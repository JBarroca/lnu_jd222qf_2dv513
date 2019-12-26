package model;

public class Patient extends Person{

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PN: ").append(this.getPersonnummer()).append("\n");
        sb.append("Date of Birth: ").append(this.getBirthday().toString()).append("\n");
        sb.append("Name: ").append(this.getFirstName()).append(" ").append(this.getLastName()).
                append(", ").append(this.getGender()).append("\n");
        sb.append("Address: ").append(this.getStreetAddress()).append("\n").
                append(this.getPostalCode()).append(" - ").append(this.getCity()).append("\n");
        sb.append("Phone number: ").append(this.getPhoneNumber()).append("\n");

        return sb.toString();
    }
}
