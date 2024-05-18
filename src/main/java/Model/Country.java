package Model;

/**
 * The Model Class for Country
 *
 * @author Adam Wright
 *
 */


public class Country {
    private int countryId;
    private String countryName;
    private int countryMonthTotal;
    private String countryMonth;

    /**
     * The overloaded constructor for Country used in CountryDAO thar will retrieve associated appointment count per country.
     *
     * @param countryMonth  The country month
     * @param countryMonthTotal The country month total
     */

    public Country(String countryMonth, int countryMonthTotal) {
        this.countryMonth = countryMonth;
        this.countryMonthTotal = countryMonthTotal;
    }

    /**
     * The Overloaded constructor for Country that will place all the countries within an observable list.
     *
     * @param countryId   The country Id
     * @param countryName The country name.
     */
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * The getter for the total count of appointments for a specific country.
     *
     * @return The total count of appointments for the country and the month.
     */
    public int getCountryMonthTotal() {
        return countryMonthTotal;
    }

    /**
     * The getter for the country month.
     *
     * @return countryMonth - The country month is returned.
     */
    public String getCountryMonth() {
        return countryMonth;
    }


    /**
     * The getter for country ID
     *
     * @return the country Id.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * The setter for country ID
     *
     * @param countryId the ID of the country.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * The getter for the country name
     *
     * @return The name of the country.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * The setter for the country name.
     *
     * @param countryName The new country name.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Returns the name of the country as a string.
     *This method is used to display country names in combo boxes instead of memory addresses.
     * @return The name of the country as a string.
     */

    @Override
    public String toString() {
        return (countryName);
    }
}

