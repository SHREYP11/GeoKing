import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a database of levels, each containing a list of countries.
 * Levels are initialized from a CSV file, "leveldata.csv", where each level consists of four countries.
 * Provides functionality to select a random country from a specified level for gameplay,
 * or a specific country based on admin selection.
 * <p>
 * Usage Example:
 * <pre>
 * {@code
 * levelDatabase db = new levelDatabase();
 * country randomCountry = db.selectLevel(1); // Random country from level 1
 * country chosenCountry = db.adminSelect(1, 2); // Specific country from level 1
 * }
 * </pre>
 *
 * @version 1.0
 * @author Ritik Parmar
 */
public class levelDatabase {
    private ArrayList<ArrayList<country>> levels;

    /**
     * Initializes the database by loading levels and their countries from "leveldata.csv".
     * Each level consists of four countries. The file should have the structure: level,country name,continent.
     * Skips the first line assuming it's a header.
     */
    // Constructor to initialize the levels from a file
    public levelDatabase() {
        levels = new ArrayList<ArrayList<country>>();
        try (BufferedReader br = new BufferedReader(new FileReader("leveldata.csv"))) {
            String line;
            br.readLine();
            int count = 0;
            ArrayList<country> temp = new ArrayList<country>();
                while ((line = br.readLine()) != null) {
                    // Split the CSV line into an array of values
                    String[] values = line.split(",");
                    String name = values[1].trim();
                    String continent = values[2].trim();
                    temp.add(new country(name,continent));
                    count++;
                    if (count % 4 == 0){
                        levels.add(temp);
                        temp = new ArrayList<>();
                    }
                }

        }
        catch (IOException e) {
            System.out.println("File not found");

        }
    }
    /**
     * Selects a random country from the specified level.
     *
     * @param userLevel the level from which to select the country (1-based index)
     * @return A randomly selected country from the specified level.
     */
    // Method to select a level
    public country selectLevel(int userLevel) {
        Random random = new Random();
        ArrayList<country> leveloptions = levels.get(userLevel-1);
        int randomInt = random.nextInt(4);
        return leveloptions.get(randomInt);
    }
    /**
     * Selects a specific country based on admin choice from the specified level.
     *
     * @param level  the level from which to select the country (1-based index)
     * @param choice the specific country to select (1-based index)
     * @return The country chosen by the admin from the specified level.
     */
    public country adminSelect(int level, int choice){
        return (levels.get(level-1)).get(choice-1);
    }
    /**
     * Main method for demonstration purposes. Shows how to use the levelDatabase class to select countries.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        levelDatabase test = new levelDatabase();
        country tester = test.selectLevel(1);
        country tester2 = test.selectLevel(2);
        System.out.println("These are working");
        country test3 = test.adminSelect(1,2);
        System.out.println(test3);
    }
}
