/**
 * Class that represents Country Objects
 *
 * @author Shreykumar J Patel
 * @version 1.0
 */

public class country {
    /** The Country name */
    private final String name;
    /** The Continent of the Country */
    private final String continent;

    /**
     * Country Constructor. Creates a new Country Object.
     *
     * @param name      the name of the country
     * @param continent the continent of the country
     */
    public country(String name, String continent) {
        this.name = name;
        this.continent = continent;
    }

    /**
     * Getter Method for name. Gets name for Country Object.
     *
     * @return the country name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter Method for continent. Gets Continent for Country Object.
     *
     * @return the country continent
     */
    public String getContinent() {
        return continent;
    }
}
