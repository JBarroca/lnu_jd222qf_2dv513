package Model;

import java.time.LocalDate;
import java.time.Period;

public abstract class Person {

    private String personnummer, firstName, lastName, fullName, address, gender, phoneNumber;
    private int age;
    private LocalDate birthday;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (!firstName.equals("")) {
            this.firstName = firstName;
        } else {
            throw new IllegalArgumentException("First name must not be empty");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (!lastName.equals("")) {
            this.lastName= lastName;
        } else {
            throw new IllegalArgumentException("Last name must not be empty");
        }    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String firstName, String lastName) {
        this.fullName = firstName + " " + lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!address.equals("")) {
            this.address= address;
        } else {
            throw new IllegalArgumentException("Address must not be empty");
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender != null) {
            this.gender = gender;
        } else {
            throw new NullPointerException("Please select a gender");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (!phoneNumber.equals("") && phoneNumber.matches("^[0-9]*$")) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Phone number must be a valid number");
        }
    }

    public String getPersonnummer() {
        return personnummer;
    }

    public void setPersonnummer(String personnummer) {
        //check for valid swedish personal number
        if (!personnummer.equals("")
                && personnummer.matches("(19[0-9]{2}|20[0-9]{2})(0[0-9]|1[0-2])([0-2]\\d|3[0-1])[-]\\d{4}")
                && !isFuture(personnummer)) {
            this.personnummer = personnummer;
        } else {
            throw new IllegalArgumentException("Personnummer must be in the pattern " +
                    "YYYYMMDD-XXXX");
        }
    }

    private boolean isFuture(String personnummer) {
        int year = Integer.parseInt(personnummer.substring(0, 4));
        int month = Integer.parseInt(personnummer.substring(4, 6));
        int day = Integer.parseInt(personnummer.substring(6, 8));
        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate today = LocalDate.now();
        return birthDate.isAfter(today);
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        if (birthday == null) {
            throw new NullPointerException("Date of birth cannot be empty");
        } else if (birthday.isAfter(LocalDate.now()) || birthday.getYear() < 1900) {
            throw new IllegalArgumentException("Invalid date of birth");
        } else {
            this.birthday = birthday;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(LocalDate birthday) {
        this.age = Period.between(birthday, LocalDate.now()).getYears();
    }

}
