package Model.Database;

import java.io.File;
import java.util.ArrayList;

public class Randomizer {

    private ArrayList<String> firstNamesMale = new ArrayList<>();
    private File maleFirstNamesFile = new File("resources/swedish_male_names.txt");

    private ArrayList<String> firstNamesFemale = new ArrayList<>();
    private File femaleFirstNamesFile = new File("resources/swedish_female_names.txt");

    private ArrayList<String> lastNames = new ArrayList<>();
    private File lastNamesFile = new File("resources/swedish_surnames.txt");

    private ArrayList<String> streetNames = new ArrayList<>();
    private File streetNamesFile = new File("resources/swedish_street_names.txt");

    private ArrayList<String> cities = new ArrayList<>();
    private ArrayList<String> postalCodes = new ArrayList<>();
    private File postalCodesFile = new File("resources/swedish_postal_codes.txt");

    public String getRandomNameMale() {
        
    }

}
