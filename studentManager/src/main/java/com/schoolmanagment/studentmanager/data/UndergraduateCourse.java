package com.schoolmanagment.studentmanager.data;

public enum UndergraduateCourse implements Course {
    BIOINFORMATICS("Bioinformatics"),
    DATA_SCIENCE("Data science"),
    FINANCE_AND_INSURANCE_MATHEMATICS("Finance and insurance mathematics"),
    INFORMATION_TECHNOLOGIES("Information technologies"),
    INFORMATION_SYSTEMS_ENGINEERING("Information systems engineering"),
    INFORMATICS("Informatics"),
    MATHEMATICS_AND_APPLICATIONS("Mathematics and applications"),
    MATHEMATICS_EDUCATION_AND_EDUCOMETRY("Mathematics education and educometry"),
    SOFTWARE_SYSTEMS("Software systems"),
    BUSINESS_DATA_ANALYTICS("Business data analytics");
    ;

    private String name;

    UndergraduateCourse(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
