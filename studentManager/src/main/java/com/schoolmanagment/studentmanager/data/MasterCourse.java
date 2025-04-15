package com.schoolmanagment.studentmanager.data;

public enum MasterCourse implements Course{
    HIGHER_TECHNOLOGY_BUSINESS("Higher technology business"),
    DATA_SCIENCE("Data science"),
    FINANCE_AND_INSURANCE_MATHEMATICS("Finance and insurance mathematics"),
    INFORMATICS("Informatics"),
    COMPUTER_MODELING("Computer modeling"),
    MATHEMATICS("Mathematics"),
    SOFTWARE_SYSTEMS("Software systems"),
    INTERNATIONAL_CYBERSECURITY_AND_CYBER_INTELLIGENCE("International cybersecurity and cyber intelligence");

    private final String name;

    MasterCourse(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
