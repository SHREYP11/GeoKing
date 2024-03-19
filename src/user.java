public class user {
    private String name;
    private int classicLevel;
    private int frenzyLevel;
    private boolean admin;


    public user(String inputName, int classicLevel, int frenzyLevel, boolean admin){
        this.name = inputName;
        this.classicLevel = classicLevel;
        this.frenzyLevel = frenzyLevel;
        this.admin = admin;
    }

    public void incrementClassicLevel(){
        classicLevel++;
    }
    public void incrementFrenzyLevel(){
        frenzyLevel++;
    }
    public String getName(){
        return name;
    }
    public int getClassicLevel(){
        return classicLevel;
    }
    public int getFrenzyLevel(){
        return frenzyLevel;
    }
    public boolean getAdmin(){
        return admin;
    }
}
