package model.database;

import model.Laboratory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class LaboratoryGenerator {

    private ArrayList<String> laboratoryNames = new ArrayList<>();
    private File laboratoryNamesFile = new File("resources/laboratory_names.txt");

    private ArrayList<String> streetNames = new ArrayList<>();
    private File streetNamesFile = new File("resources/swedish_street_names.txt"); //1041 lines

    private ArrayList<String> postalCodesAndCities = new ArrayList<>();
    private File postalCodesFile = new File("resources/postalCodesAndCities.txt"); //15687 lines


    public LaboratoryGenerator() {
        try {
            fillList(laboratoryNamesFile, laboratoryNames);
            fillList(streetNamesFile, streetNames);
            fillList(postalCodesFile, postalCodesAndCities);
        } catch (FileNotFoundException e) {
            System.out.println("Error when generating random laboratory: ");
            e.printStackTrace();
        }
    }

    public String[] createRandomLaboratoryData() {
        String[] laboratoryData = new String[4];
        laboratoryData[0] = getRandomLabName();
        laboratoryData[1] = getRandomAddress();
        laboratoryData[2] = getRandomPostalCode();
        laboratoryData[3] = getRandomPhoneNumber();
        return laboratoryData;
    }

    private String getRandomLabName() {
        Random rnd = new Random();
        int rndIndex = rnd.nextInt(laboratoryNames.size());
        return laboratoryNames.get(rndIndex).concat(" Labs");
    }

    String getRandomPostalCode() {
        Random rnd = new Random();
        int rndIndex = rnd.nextInt(15687);
        String pair = postalCodesAndCities.get(rndIndex);
        String[] postCodeAndCity = pair.split("\\*");
        return postCodeAndCity[0];
    }

    String getRandomAddress() {
        Random rnd = new Random();
        int rndIndex = rnd.nextInt(1041);
        String streetName = streetNames.get(rndIndex);
        int rndDoorNumber = rnd.nextInt(80);

        return streetName + ", " + rndDoorNumber;
    }

    String getRandomPhoneNumber() {
        Random rnd = new Random();
        int rndIndex = rnd.nextInt(9999);
        String rndNumber = String.valueOf(rndIndex);
        if (rndIndex < 10) {
            rndNumber = "000" + rndNumber;
        } else if (rndIndex < 100) {
            rndNumber = "00" + rndNumber;
        } else if (rndIndex < 1000) {
            rndNumber = "0" + rndNumber;
        }
        return "555-" + rndNumber;
    }

    private void fillList(File fileToRead, ArrayList<String> listToFill) throws FileNotFoundException {
        Scanner sc = new Scanner(fileToRead);
        while (sc.hasNextLine()) {
            listToFill.add(sc.nextLine());
        }
    }

}
