package Model.Database;

import Model.Patient;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class PatientRandomizer {

    private ArrayList<String> firstNamesMale = new ArrayList<>();
    private File maleFirstNamesFile = new File("resources/swedish_male_names.txt"); //603 lines

    private ArrayList<String> firstNamesFemale = new ArrayList<>();
    private File femaleFirstNamesFile = new File("resources/swedish_female_names.txt"); //803 lines

    private ArrayList<String> lastNames = new ArrayList<>();
    private File lastNamesFile = new File("resources/swedish_surnames.txt"); //100 lines

    private ArrayList<String> streetNames = new ArrayList<>();
    private File streetNamesFile = new File("resources/swedish_street_names.txt"); //1041 lines

    private ArrayList<String> postalCodesAndCities = new ArrayList<>();
    private File postalCodesFile = new File("resources/postalCodesAndCities.txt"); //15687 lines

    public PatientRandomizer() {
        try {
            fillList(maleFirstNamesFile, firstNamesMale);
            fillList(femaleFirstNamesFile, firstNamesFemale);
            fillList(lastNamesFile, lastNames);
            fillList(streetNamesFile, streetNames);
            fillList(postalCodesFile, postalCodesAndCities);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Patient getRandomPatient() {
        Patient patient = new Patient();

        LocalDate dateOfBirth = getRandomDateOfBirth();
        patient.setBirthday(dateOfBirth);
        patient.setPersonnummer(getRandomPersonnummer(dateOfBirth));
        //randomizing gender
        Random rnd = new Random();
        int rndInt = rnd.nextInt(2);
        if (rndInt == 1) {
            patient.setFirstName(getRandomItem(firstNamesMale));
            patient.setGender("M");
        } else {
            patient.setFirstName(getRandomItem(firstNamesFemale));
            patient.setGender("F");
        }
        patient.setLastName(getRandomItem(lastNames));
        patient.setStreetAddress(getRandomAddress());

        String[] postalCodeAndCity = getRandomPostCodeAndCity();
        patient.setPostalCode(postalCodeAndCity[0]);
        patient.setCity(postalCodeAndCity[1]);
        patient.setPhoneNumber(getRandomPhoneNumber());

        return patient;
    }

    private String getRandomPersonnummer(LocalDate dateOfBirth) {
        String year = String.valueOf(dateOfBirth.getYear());
        String month = String.valueOf(dateOfBirth.getMonthValue());
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        String day = String.valueOf(dateOfBirth.getDayOfMonth());
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        Random rnd = new Random();
        int codeInt = rnd.nextInt(9999);
        String codeString = String.valueOf(codeInt);
        if (codeInt < 100) {
            codeString = "00" + codeString;
        } else if (codeInt < 1000) {
            codeString = "0" + codeString;
        }
        return year.concat(month).concat(day).concat("-").concat(codeString);
    }

    private String getRandomItem(ArrayList<String> listToUse) {
        Random rnd = new Random();
        int rndIndex = rnd.nextInt(listToUse.size());
        return listToUse.get(rndIndex);
    }

    private LocalDate getRandomDateOfBirth() {
        //adapted from https://stackoverflow.com/questions/3985392/generate-random-date-of-birth
        Random rnd = new Random();
        long milliseconds = -946771200000L + (Math.abs(rnd.nextLong()) % (60L * 365 * 24 * 60 * 60 * 1000));
        Date date = new Date(milliseconds);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public String getRandomAddress() {
        Random rnd = new Random();
        int rndIndex = rnd.nextInt(1041);
        String streetName = streetNames.get(rndIndex);
        int rndDoorNumber = rnd.nextInt(80);

        return streetName + ", " + rndDoorNumber;
    }

    public String[] getRandomPostCodeAndCity() {
        Random rnd = new Random();
        int rndIndex = rnd.nextInt(15687);
        String pair = postalCodesAndCities.get(rndIndex);
        return pair.split("\\*");
    }

    public String getRandomPhoneNumber() {
        Random rnd = new Random();
        int rndIndex = rnd.nextInt(9999);
        String rndNumber = String.valueOf(rndIndex);
        if (rndIndex < 100) {
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