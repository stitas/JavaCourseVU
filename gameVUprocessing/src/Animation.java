import processing.core.PImage;

import static processing.awt.ShimAWT.loadImage;
import static processing.core.PApplet.nf;

/**
 * Ši klasė tvarko animacijų atvaizdų masyvą ir teikia metodus, skirtus gauti animacijos dydžius.
 */
class Animation {
    // Animacijos paveikslėlių masyvas
    PImage[] images;
    // Bendras paveikslėlių kiekis animacijoje
    int imageCount;
    // Dabartinio rodomo paveikslėlio indeksas
    int frame;

    /**
     * Konstruktorius, užkraunantis animacijos paveikslėlius iš diskų.
     *
     * @param imagePrefix Paveikslėlių pavadinimų pradžia (pvz., katalogo kelias ir pavadinimo pradžia)
     * @param count Bendras paveikslėlių skaičius, sudarančių animaciją
     * @param game Nuoroda į žaidimo objektą, naudojama paveikslėlių įkėlimui
     */
    Animation(String imagePrefix, int count, Game game) {
        imageCount = count;
        images = new PImage[imageCount];

        for (int i = 0; i < imageCount; i++) {
            // Naudojama nf() funkcija, kad 'i' būtų suformatuotas kaip keturženklis skaičius
            String filename = imagePrefix + nf(i, 4) + ".png";
            images[i] = loadImage(game, filename);
        }
    }

    /**
     * Gražina pirmo paveikslėlio plotį.
     *
     * @return Animacijos paveikslėlio plotis
     */
    int getWidth() {
        return images[0].width;
    }

    /**
     * Gražina pirmo paveikslėlio aukštį.
     *
     * @return Animacijos paveikslėlio aukštis
     */
    int getHeight() {
        return images[0].height;
    }
}
