import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PANMain {
    public static void main(String[] args) {
        Hash panHash = new Hash();
        panHash.initializeHash();

        // Read input file and add entries to the hash table
        try {
            File inputFile = new File("C:/Users/I527370/codes/DSAASSG/panmg/src/input.txt");
            Scanner fileScanner = new Scanner(inputFile);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] columns = line.split(" ");

                if (columns.length == 3) {
                    String pan = columns[0];
                    String name = columns[1];
                    String place = columns[2];

                    Account account = new Account(pan, name, place);
                    panHash.addEntry(account);
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.");
            return;
        }

        // Read search file and search for PAN entries
        try {
            File searchFile = new File("C:/Users/I527370/codes/DSAASSG/panmg/src/search.txt");
            Scanner fileScanner = new Scanner(searchFile);

            while (fileScanner.hasNextLine()) {
                String panToSearch = fileScanner.nextLine();
                panHash.searchDetails(panToSearch);
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Search file not found.");
            return;
        }
    }
}
