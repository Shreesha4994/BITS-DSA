package main;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Hash {
    private FL_HashTable flHashTable;
    private int[] hashSize;

    public Hash() {
        flHashTable = new FL_HashTable();
        hashSize = new int[10];
        Arrays.fill(hashSize, 13);
    }

    public void initializeHash() {
        flHashTable.initialize();
    }

    public int hash(String pan) {
        String regex = "[A-Z]{5}\\d{4}[A-Z]";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pan);

        if (matcher.matches()) {
            char category = pan.charAt(3);
            switch (category) {
                case 'C':
                    return 0;
                case 'P':
                    return 1;
                case 'H':
                    return 2;
                case 'F':
                    return 3;
                case 'A':
                    return 4;
                case 'T':
                    return 5;
                case 'B':
                    return 6;
                case 'L':
                    return 7;
                case 'J':
                    return 8;
                case 'G':
                    return 9;
                default:
                    return -1;
            }
        } else {
            return -1;
        }
    }

    public int secondHash(String pan) {
        int h2 = 0;
        for(int i=0 ; i < pan.length() ; i++){
            h2 = h2 + (int) pan.charAt(i);
        }
        return h2;
    }

    public void addEntry(Account account) {
        try {
            int categoryIndex = hash(account.getPanNumber());
            SL_HashTable slHashTable = flHashTable.getHashTable(categoryIndex);

            int hashTableSize = hashSize[categoryIndex];
            int hashValue = secondHash(account.getPanNumber())% hashTableSize;
            int position = hashValue;

            int collisionCount = 0;
            while (slHashTable.getAccounts()[position] != null) {
                collisionCount++;
                position = (hashValue + (collisionCount * collisionCount)) % hashTableSize;
            }

            slHashTable.getAccounts()[position] = account;

            if (collisionCount > 0) {
                System.out.println("Collision occurred for PAN " + account.getPanNumber() + ". Resolved with quadratic probing.");
            }

            // Check if rehashing is needed
            if (collisionCount > 0 && collisionCount >= (hashTableSize / 2)) {
                extend_rehash(categoryIndex);
            }
        } catch (Exception e) {
            System.out.println("The PAN number: "+ account.getPanNumber()+" couldn't be inserted because it is invalid");
        }
    }

    public void extend_rehash(int categoryIndex) {
        int oldSize = hashSize[categoryIndex];
        int newSize = oldSize * 2;
        hashSize[categoryIndex] = newSize;

        SL_HashTable oldHashTable = flHashTable.getHashTable(categoryIndex);
        Account[] oldAccounts = oldHashTable.getAccounts();

        SL_HashTable newHashTable = new SL_HashTable(newSize);
        flHashTable.setHashTable(categoryIndex, newHashTable);

        for (Account account : oldAccounts) {
            if (account != null) {
                addEntry(account);
            }
        }

        System.out.println("Extended java.SL_HashTable for category " + categoryIndex + ". New size: " + newSize);
    }

    public void searchDetails(String pan) {
        try {
            int categoryIndex = hash(pan);
            SL_HashTable slHashTable = flHashTable.getHashTable(categoryIndex);

            boolean found = false;
            int hashCode = secondHash(pan) % slHashTable.getSize();
            if (hashCode < 0) {
                hashCode += slHashTable.getSize();
            }
            int index = hashCode;
            int probe = 1;

            while (slHashTable.getAccounts()[index] != null && probe < slHashTable.getSize()) {
                if (slHashTable.getAccounts()[index].getPanNumber().equals(pan)) {
                    String output = "The entry " + pan + " does exist - " + slHashTable.getAccounts()[index].getName();
                    System.out.println(output);
                    found = true;
                    break;
                }

                probe++;
                index = (hashCode + (probe * probe)) % slHashTable.getSize();
                if (index < 0) {
                    index += slHashTable.getSize();
                }
            }

            if (!found) {
                String output = "The entry " + pan + " doesn't exist";
                System.out.println(output);
            }
        } catch (Exception e) {
            // Handle the exception here
            System.out.println("The PAN number("+pan+") you are trying to search is invalid");
        }
    }

}
