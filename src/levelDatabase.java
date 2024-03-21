import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class levelDatabase {
    private ArrayList<ArrayList<country>> levels;

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

    // Method to select a level
    public country selectLevel(int userLevel) {
        Random random = new Random();
        ArrayList<country> leveloptions = levels.get(userLevel-1);
        int randomInt = random.nextInt(4);
        return leveloptions.get(randomInt);
    }

    public country adminSelect(int level, int choice){
        return (levels.get(level-1)).get(choice-1);
    }

    public static void main(String[] args) {
        levelDatabase test = new levelDatabase();
        country tester = test.selectLevel(1);
        country tester2 = test.selectLevel(2);
        System.out.println("These are working");
        country test3 = test.adminSelect(1,2);
        System.out.println(test3);
    }
}
