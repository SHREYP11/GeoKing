public class country {
    private final String name;
    private final String continent;

    // Constructor with parameters to set name and continent
    public country(String name, String continent) {
        this.name = name;
        this.continent = continent;
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Getter method for continent
    public String getContinent() {
        return continent;
    }
}
