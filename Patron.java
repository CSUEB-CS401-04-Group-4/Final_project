// Patron class representing a library patron
public class Patron {
    private int ID;
    private String name;
    private String contactInfo;

    // Constructor
    public Patron(int ID, String name, String contactInfo) {
        this.ID = ID;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    // Getters and setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // Other methods as needed
}