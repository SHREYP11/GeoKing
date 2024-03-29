import java.io.*;
import java.util.ArrayList; // import the ArrayList class
import java.util.Objects;

/**
 * Class that represents the user data base.
 * Stores all users in the game.
 *
 * @author Colin Lynch, Vladyslav Oleksandrovych Koval
 * @version 1.0
 */
public class userDatabase {
    /**
     * The ArrayList that holds all users in the game. New ArrayList object that
     * holds user objects.
     */
    private ArrayList<user> users = new ArrayList<user>();

    /**
     * userDatabase Constructor. Creates a new userDatabase object.
     */
    public userDatabase() {
        try (BufferedReader br = new BufferedReader(new FileReader("userfile.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Split the CSV line into an array of values
                String[] values = line.split(",");
                String name = values[0].trim();
                int classicLevel = Integer.parseInt(values[1].trim());
                int frenzyLevel = Integer.parseInt(values[2].trim());
                boolean admin = Boolean.parseBoolean(values[3].trim());
                user tempUser = new user(name, classicLevel, frenzyLevel, admin);
                users.add(tempUser);

            }
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    /**
     * Method that creates user and adds it to userDatabase.
     *
     * @param name the name of the user
     */
    public void createUser(String name) {
        user tempUser = new user(name, 1, 1, false);
        users.add(tempUser);
    }

    /**
     * Method that finds user object in userDatabase.
     *
     * @param name the name of the user
     * @return the user object if the user object was found in the database, returns
     * null if not found
     */
    public user findUser(String name) {
        for (user temp : users) {
            if (Objects.equals(temp.getName().toLowerCase(), name.toLowerCase())) {
                return temp;
            }
        }
        return null;
    }

    /**
     * Method that exports the userDatabase to csv file.
     */
    public void exportDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userfile.csv"))) {
            // Writing header
            writer.write("Name,Classic Level,Frenzy Level,Admin");

            // Writing data
            for (user temp : users) {
                writer.write("\n" + temp.getName() + "," + temp.getClassicLevel() + "," + temp.getFrenzyLevel() + ","
                        + temp.getAdmin());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
