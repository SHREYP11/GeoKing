/**
 * Class that represents User Objects
 *
 * @author Colin Lynch
 * @version 1.0
 */

public class user {
    /** The User name */
    private String name;
    /** The level number for classic gamemode */
    private int classicLevel;
    /** The level number for frenzy gamemode */
    private int frenzyLevel;
    /** Boolean to see if User has admin priviledges */
    private boolean admin;

    /**
     * User Constructor. Creates a new User Object
     *
     * @param inputName    the name of the user
     * @param classicLevel the level number for classic gamemode
     * @param frenzyLevel  the level number for frenzy gamemode
     * @param admin        boolean to see if user has admin priviledges
     */
    public user(String inputName, int classicLevel, int frenzyLevel, boolean admin) {
        this.name = inputName;
        this.classicLevel = classicLevel;
        this.frenzyLevel = frenzyLevel;
        this.admin = admin;
    }

    /**
     * Classiclevel incrementer, increment level number by 1
     */
    public void incrementClassicLevel() {
        classicLevel++;
    }

    /**
     * Frenzylevel incrementer, increment frenzy level number by 1
     */
    public void incrementFrenzyLevel() {
        frenzyLevel++;
    }

    /**
     * Getter method for name. Gets name for User Object.
     *
     * @return the user name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter Method for classicLevel. Gets classicLevel for User Object.
     *
     * @return the level number for classicLevel
     */
    public int getClassicLevel() {
        return classicLevel;
    }

    /**
     * Getter Method for frenzyLevel. Gets frenzyLevel for User Object.
     *
     * @return the level number for frenzyLevel
     */
    public int getFrenzyLevel() {
        return frenzyLevel;
    }

    /**
     * Getter Method for admin. Gets admin for User Object
     *
     * @return the boolean that verifies if user has admin priviledges
     */
    public boolean getAdmin() {
        return admin;
    }
}
