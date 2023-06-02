package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PANMain {

    public static void main(String[] args) {
        Hash panHash = new Hash();
        panHash.initializeHash();

        System.out.println("PAN Details Menu");
        System.out.println("1. Add PAN Details");
        System.out.println("2. Search the PAN Number");
        System.out.println("3. Quit");

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.print("Enter your choice (1-3): ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    try {
                        File inputFile = new File("src/data/input.txt");
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
                    }
                    break;
                case 2:
                    try {
                        File searchFile = new File("src/data/search.txt");
                        Scanner fileScanner = new Scanner(searchFile);

                        while (fileScanner.hasNextLine()) {
                            String panToSearch = fileScanner.nextLine();
                            panHash.searchDetails(panToSearch);
                        }

                        fileScanner.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Search file not found.");
                    }
                    break;
                case 3:
                    System.out.println("Quitting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 3);
    }
}
