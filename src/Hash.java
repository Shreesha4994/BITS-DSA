import java.util.Arrays;

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
    }

    public void addEntry(Account account) {
        int categoryIndex = hash(account.getPanNumber());
        SL_HashTable slHashTable = flHashTable.getHashTable(categoryIndex);

        int hashTableSize = hashSize[categoryIndex];
        int hashValue = account.getPanNumber().hashCode() % hashTableSize;
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

        System.out.println("Extended SL_HashTable for category " + categoryIndex + ". New size: " + newSize);
    }

    public void searchDetails(String pan) {
        int categoryIndex = hash(pan);
        SL_HashTable slHashTable = flHashTable.getHashTable(categoryIndex);

        boolean found = false;
        int hashCode = pan.hashCode() % slHashTable.getSize();
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
    }









}
