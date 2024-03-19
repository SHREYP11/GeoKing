import java.io.*;
import java.util.ArrayList; // import the ArrayList class
import java.util.Objects;

public class userDatabase {
    private ArrayList<user> users = new ArrayList<user>(); // Create an ArrayList object
    public userDatabase(){
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
                user tempUser = new user(name,classicLevel,frenzyLevel,admin);
                users.add(tempUser);

            }
        }
        catch (IOException e) {
            e.printStackTrace();

        }

    }
    public void createUser(String name){
        user tempUser = new user(name,1,1,false);
        users.add(tempUser);
    }

    public user findUser(String name){
        for (user temp: users){
            if (Objects.equals(temp.getName(), name)){
                return temp;
            }
        }
        return null;
    }

    public void exportDatabase(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userfile.csv"))) {
            // Writing header
            writer.write("Name,Classic Level,Frenzy Level,Admin");

            // Writing data
            for (user temp: users) {
                writer.write("\n"+ temp.getName() + "," + temp.getClassicLevel()+ "," + temp.getFrenzyLevel()+ "," + temp.getAdmin());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        userDatabase user = new userDatabase();
        //group39.user.createUser("John");
        System.out.println("Test");
        System.out.println(user.findUser("Colin"));
        user.exportDatabase();
    }
}