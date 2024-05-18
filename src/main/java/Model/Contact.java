package Model;

/**
 * The Model Class for Contact
 *
 * @author Adam Wright
 */

public class Contact {

    private int contactId;
    private String contactName;
    private String contactEmail;
    private String contactPhone;

    /**
     * Constructor for Contact
     * @param contactId The contact ID
     * @param contactName  The contact name
     * @param contactEmail  The contact email
     */

    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;

    }

    /**
     * The getter for contact ID
     *
     * @return contactId - The contact ID
     */
    public int getContactId() {
        return contactId;
    }


    /**
     * The setter  for contact ID
     *
     * @param contactId - The contact ID
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * The getter for contact name
     *
     * @return contactName - The contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * The setter for contact name
     *
     * @param contactName - The name of the contact
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * The getter for contact email
     *
     * @return contactEmail - The contact email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * The setter for contact email
     *
     * @param contactEmail The new contact email.
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * The method overrides the toString() method from the object class.
     *It returns a string representation of the Contact object.
     * @return contactName - The name of the contact
     */

    @Override
    public String toString() {
        return (contactName);
    }


}
