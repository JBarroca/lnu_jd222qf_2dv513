package model;

public class Laboratory {

    private String labName;
    private String streetAddress;
    private String city;
    private String phoneNumber;

    public Laboratory() {}

    public Laboratory(String labName, String streetAddress, String city, String phoneNumber) {
        this.labName = labName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        if (!labName.equals("")) {
            this.labName = labName;
        } else {
            throw new IllegalArgumentException("Laboratory's name must not be empty");
        }
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        if (!streetAddress.equals("")) {
            this.streetAddress = streetAddress;
        } else {
            throw new IllegalArgumentException("Street address must not be empty");
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (!city.equals("")) {
            this.city = city;
        } else {
            throw new IllegalArgumentException("City name must not be empty");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Phone number must not be empty");
        }
    }

    @Override
    public String toString() {
        return "Laboratory{" +
                "labName='" + labName + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
