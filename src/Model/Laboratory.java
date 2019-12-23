package Model;

public class Laboratory {

    private String labName;
    private String streetAddress;
    private String postalCode;
    private String city;

    public Laboratory(String labName, String postalCode, String city) {
        this.labName = labName;
        this.postalCode = postalCode;
        this.city = city;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        if (!postalCode.equals("")) {
            this.postalCode = postalCode;
        } else {
            throw new IllegalArgumentException("Postal code must not be empty");
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
        }    }
}
