/**
 * @author Titas Stongvila 5 grupė
 **/

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
 * Pagrindinė žaidimo klasė, kuri paveldi PApplet ir apdoroja žaidimo logiką, animacijas,
 * žemėlapio generavimą bei sąveiką su žaidėjo įvestimi.
 */
public class Game extends PApplet {
    int gameTime = 10;
    // Animacijos dažnis (milisekundėmis)
    final int ANIM_FREQUENCY = 150; // 150ms
    // Kadrų per sekundę rodymo dažnis
    final int FPS = 60;
    PFont f;

    // Nustatymai gridui redaktoriuje
    final int GRID_OFFSET_X = 100;
    final int GRID_OFFSET_Y = 100;
    final int TILE_PALLET_OFFSET_X = 0;
    final int TILE_PALLET_OFFSET_Y = 32;

    // Taisyklių tipo reikšmės
    final int AIR = 0;
    final int BORDER = 1;
    final int SPIKE_BOTTOM = 2;
    final int SPIKE_TOP = 3;
    final int SIGN = 4;

    // Vaizdo objektai ir paveikslėlių masyvas
    PImage tileImg;
    PImage temp;
    PImage[] images;

    // Ekrano matmenys ir plytelių dydžiai
    final int screenWidth = 768;
    final int screenHeight = 512;
    final int tileW = 32;
    final int tileH = 32;
    final int editorTileW = 10;
    final int editorTileH = 10;
    int cols = screenHeight / tileH;
    int rows = screenWidth / tileW;

    // Charakterio būsena: 0 - ramus, 1 - vaikščiojimas į kairę, 2 - vaikščiojimas į dešinę, 3 - šuolis
    int characterStatus = 0;
    Animation characterWalkLeft;
    Animation characterWalkRight;
    Animation characterIdle;
    Animation characterJump;
    int characterX;
    int characterY;
    int characterW = 32;
    int characterH = 32;
    // Judėjimo greičiai ir šuolio parametrų reikšmės
    int stepSize = 5;
    int jumpStepSize = 4;
    int velocityX = 0;
    int velocityY = 0;
    int startingY;
    int startX = 64;
    int startY = 960;
    boolean respawn = false;

    // Laikmatis animacijai
    long animTimer = millis();
    long gameTimer = millis();

    // Žemėlapio kintamieji: skaičiai žymi skirtingus elementus (pvz., 9 - blokas, 71 - platforma, 207 - spygliuotas grindys, 216 - spygliuota lubos)
    int[][] map = new int[cols * 2][rows * 2];
    int level = 1;
    // Redaktoriaus režimo flagas
    boolean editor = false;
    // Pasirinktos plytelės indeksas
    int selectedTile = 0;

    /**
     * Nustato pradinius ekrano matmenis.
     */
    public void settings() {
        size(screenWidth, screenHeight);
    }

    /**
     * Inicijuoja žaidimo pradžios parametrus: kadrų dažnį, įkelia plytelių paveikslėlius,
     * generuoja žemėlapį bei sukuria charakterio animacijas.
     */
    public void setup() {
        frameRate(FPS);
        tileImg = loadImage("tiles2.png");
        f = createFont("Arial",16,true);
        fill(0);
        textFont(f, 16);

        // Įkeliame visus plytelių paveikslėlius į masyvą
        images = new PImage[(tileImg.width / tileW) * (tileImg.height / tileH)];
        for (int i = 0; i < (tileImg.width / tileW); i++) {
            for (int j = 0; j < (tileImg.height / tileH); j++) {
                images[i + j * (tileImg.width / tileW)] = tileImg.get(i * tileW, j * tileH, tileW, tileH);
            }
        }

        generateMap();

        // Inicializuojame charakterio animacijas
        characterWalkLeft = new Animation("./characterAnims/walkLeft", 3, this);
        characterWalkRight = new Animation("./characterAnims/walkRight", 3, this);
        characterJump = new Animation("./characterAnims/idle", 1, this);
        characterIdle = new Animation("./characterAnims/idle", 3, this);

        // Nustatome pradinę charakterio poziciją
        characterX = startX;
        characterY = startY;
        startingY = characterY;
    }

    /**
     * Sugeneruoja žemėlapį pagal dabartinį lygį. Naudoja JSON failus, kuriuose aprašytos plytelių pozicijos.
     */
    private void generateMap() {
        JSONObject mapJson;

        switch(level){
            case 1:
                mapJson = loadJSONObject("map1.json");
                break;
            case 2:
                mapJson = loadJSONObject("map2.json");
                break;
            case 3:
                mapJson = loadJSONObject("map3.json");
                break;
            default:
                mapJson = loadJSONObject("map1.json");
                break;
        }

        JSONArray layers = mapJson.getJSONArray("layers");
        JSONObject firstLayer = layers.getJSONObject(0);
        JSONArray tiles = firstLayer.getJSONArray("tiles");

        // Užpildo žemėlapio masyvą pagal JSON duomenis
        for(int i = 0; i < tiles.size(); i++){
            JSONObject tile = tiles.getJSONObject(i);
            int tileId = tile.getInt("id");
            int x = tile.getInt("x");
            int y = tile.getInt("y");
            map[y][x] = tileId;
        }
    }

    /**
     * Pagrindinė piešimo funkcija, kuri nuolat atnaujina žaidimo ekraną.
     */
    public void draw() {
        background(125, 175, 225);

        if(editor){
            drawEditor();

            // Jei pele paspausta, atnaujiname žemėlapį arba plytelių paletę
            if(mousePressed){
                int x = mouseX;
                int y = mouseY;

                if(x >= GRID_OFFSET_X && x <= map[0].length * editorTileW + GRID_OFFSET_X && y > GRID_OFFSET_Y && y < map.length * editorTileH + GRID_OFFSET_Y){
                    map[(y - GRID_OFFSET_Y) / editorTileW][(x - GRID_OFFSET_X) / editorTileH] = selectedTile;
                }

                if(x >= TILE_PALLET_OFFSET_X && x <= images.length * tileW && y > TILE_PALLET_OFFSET_Y && y < TILE_PALLET_OFFSET_Y + tileH){
                    selectedTile = (x - TILE_PALLET_OFFSET_X) / tileW;
                }
            }
        }
        else {
            // Kameros pozicijos skaičiavimas pagal charakterio poziciją
            int camX = characterX - screenWidth / 2;
            int camY = characterY - screenHeight / 2;

            camX = constrain(camX, 0, screenWidth);
            camY = constrain(camY, 0, screenHeight);

            text("Laikas:" + gameTime, 20, 20);

            pushMatrix();
            translate(-camX, -camY);

            drawMap();
            drawCharacter();
            respawn = false;

            if(millis() - gameTimer > 1000){
                gameTimer = millis();
                gameTime--;
            }

            if(gameTime < 0){
                resetCharacter();
            }

            popMatrix();
        }
    }

    /**
     * Nupiešia žemėlapį, pereidamas per visus žemėlapio elementus ir išvedant atitinkamą plytelės paveikslėlį.
     */
    private void drawMap() {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                image(images[map[i][j]], j * tileW, i * tileH, tileW, tileH);
            }
        }
    }

    /**
     * Nupiešia redaktoriaus sąsają, įskaitant tinklelį, plytelių paletę bei dabartinę pasirinktos plytelės išvaizdą.
     */
    private void drawEditor() {
        grid();

        // Piešia plytelių paletę
        for(int i = 0; i < images.length; i++){
            image(images[i], i * tileW + TILE_PALLET_OFFSET_X, TILE_PALLET_OFFSET_Y, tileW, tileH);
        }

        // Piešia mažą žemėlapio peržiūrą
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++){
                image(images[map[i][j]], j * editorTileW + GRID_OFFSET_X, i * editorTileH + GRID_OFFSET_Y, editorTileW, editorTileH);
            }
        }

        // Paryškiname pasirinktos plytelės langelį
        image(images[selectedTile], 650, tileH, tileW * 2, tileH * 2);
    }

    /**
     * Nupiešia tinklelį redaktoriaus lange, skirtą plytelių paletei ir žemėlapiui.
     */
    private void grid() {
        // Tinklelio piešimas plytelių paletėje
        for(int i = 0; i < images.length; i++){
            rect(i * tileW + TILE_PALLET_OFFSET_X, TILE_PALLET_OFFSET_Y, tileW, tileH);
        }

        // Tinklelio piešimas žemėlapiui
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                rect(j * editorTileW + GRID_OFFSET_X, i * editorTileH + GRID_OFFSET_Y, editorTileW, editorTileH);
            }
        }

        // Rodyti paryškinamą plyteles dėžutę
        rect(650, tileH, tileW * 2, tileH * 2);
    }

    /**
     * Nupiešia charakterį ir atnaujina jo animaciją bei poziciją, atsižvelgiant į judėjimo greičius ir
     * sąveiką su aplinka.
     */
    private void drawCharacter() {
        if(velocityX == 0 && characterStatus != 3){
            characterStatus = 0;
        }

        // Pasirenkame animaciją pagal charakterio būseną
        switch(characterStatus){
            case 0:
                displayCharacter(characterIdle);
                break;
            case 1:
                displayCharacter(characterWalkLeft);
                break;
            case 2:
                displayCharacter(characterWalkRight);
                break;
            case 3:
                // Žingsniuoja pakilimo metu
                velocityY += 1;
                displayCharacter(characterJump);
                break;
            default:
                displayCharacter(characterIdle);
                break;
        }

        // Skaičiuojame būsimas charakterio pozicijas
        int nextX = characterX + velocityX;
        int nextY = characterY + velocityY;

        checkCollision(nextX, nextY);

        characterX += velocityX;
        characterY += velocityY;

        // Stabilizuoja charakterio poziciją kai jis nebėra ore
        if ((nextY + characterH) / tileH != map.length && (characterY >= startingY || (map[(nextY + characterH) / tileH][characterX / tileW] == BORDER)) && characterStatus == 3) {
            velocityY = 0;

            if(characterY >= startingY){
                characterY = startingY;
            }
            else {
                characterY = nextY;
            }

            if(velocityX < 0) {
                velocityX = -stepSize;
                characterStatus = 2;
            }
            else if(velocityX > 0){
                velocityX = stepSize;
                characterStatus = 1;
            }
            else {
                characterStatus = 0;
            }
        }
    }

    /**
     * Atvaizduoja charakterio animaciją pagal nurodytą Animation objektą.
     *
     * @param anim Animation objektas, kurio rėžiai bus atvaizduojami.
     */
    private void displayCharacter(Animation anim) {
        if(millis() - animTimer > ANIM_FREQUENCY) {
            anim.frame = (anim.frame + 1) % anim.imageCount;
            animTimer = millis();
        }
        image(anim.images[anim.frame], characterX, characterY);
    }

    /**
     * Tikrina, ar charakteris nesusiduria su kliūtimis, ribomis ar specialiais elementais (pvz., spygliuota plytelių).
     *
     * @param nextX Planuojama charakterio X koordinatė.
     * @param nextY Planuojama charakterio Y koordinatė.
     */
    private void checkCollision(int nextX, int nextY) {
        int leftTile = nextX / tileW;
        int rightTile = (nextX + characterW) / tileW;
        int topTile = nextY / tileH;
        int bottomTile = (nextY + characterH) / tileH;

        // Tikriname ar charakteris neperslinko į ekrano ribas
        if(characterY >= 1024){
            velocityY = 0;
            characterY = 1024;
        }
        if(characterY < 0){
            velocityY = 0;
            characterY = 0;
        }
        if(characterX >= 1536){
            characterX = 1536;
            velocityX = 0;
        }
        if(characterX < 0){
            characterX = 0;
            velocityX = 0;
        }

        // Sustabdo judėjimą jei pasiekiamos ribos ar platformos šonai
        if(map[characterY / tileH][leftTile] == BORDER || map[characterY / tileH][rightTile] == BORDER){
            velocityX = 0;
        }

        // Paleidžia iš naujo, jei pataikoma į spygliuotą plytelę
        if (map[characterY / tileH][leftTile] == SPIKE_BOTTOM || map[characterY / tileH][rightTile] == SPIKE_BOTTOM ||
                map[characterY / tileH][leftTile] == SPIKE_TOP || map[characterY / tileH][rightTile] == SPIKE_TOP){
            resetCharacter();
        }

        // Sustabdo judėjimą aukštyn, jei yra kliūtis viršuje
        if(topTile == map.length || map[topTile][characterX / tileW] == BORDER){
            velocityY = 0;
        }

        // Sustabdo judėjimą žemyn, jei apačioje yra riba, išskyrus šuolio atvejus
        if((bottomTile >= map.length || map[bottomTile][characterX / tileW] == BORDER) && characterStatus != 3){
            velocityY = 0;
        }
        // Jei po charakteriu yra tuščia vieta, pradeda kristi (nebūna šuolio režime)
        if(bottomTile < map.length && map[bottomTile][characterX / tileW] == AIR && characterStatus != 3 && !respawn){
            velocityY = 16;
        }

        // Keičia lygį, jei susiduriama su ženklu
        if(map[characterY / tileH][leftTile] == SIGN || map[characterY / tileH][rightTile] == SIGN){
            changeLevel();
        }
    }

    /**
     * Nustato charakterį į pradinę poziciją po mirties ar lygio keitimo.
     */
    private void resetCharacter(){
        respawn = true;
        characterX = startX;
        characterY = startY;
        startingY = characterY;
        characterStatus = 0;
        velocityY = 0;
        velocityX = 0;
        gameTime = 10;
    }

    /**
     * Keičia lygį ir atnaujina žemėlapį bei charakterio poziciją.
     */
    private void changeLevel(){
        level++;
        generateMap();
        resetCharacter();
    }

    /**
     * Apdoroja klaviatūros paspaudimo įvykius.
     * Skirtas judėti į šoną, šokti ir įjungti/redaguoti redaktoriaus režimą.
     */
    public void keyPressed() {
        // Šuolis su tarpu, kai nėra jau ore ir redaktoriaus režime
        if (key == ' ' && characterStatus != 3 && !editor) {
            if (characterStatus == 1) {
                velocityX += jumpStepSize;
            } else if (characterStatus == 2) {
                velocityX += -jumpStepSize;
            }
            velocityY = -32;
            characterStatus = 3;
            startingY = characterY;
        }
        // Judėjimas į kairę
        if (key == 'a' && characterStatus != 3 && !editor) {
            velocityX = -stepSize;
            characterStatus = 2;
        }
        // Judėjimas į dešinę
        if (key == 'd' && characterStatus != 3 && !editor) {
            velocityX = stepSize;
            characterStatus = 1;
        }
        // Perjungia redaktoriaus režimą
        if (key == 'e'){
            editor = !editor;
        }
    }

    /**
     * Apdoroja klaviatūros atleidimo įvykius ir stabdo judėjimą.
     */
    public void keyReleased() {
        if (key == 'a') {
            velocityX = 0;
        }
        else if (key == 'd') {
            velocityX = 0;
        }
    }
}
