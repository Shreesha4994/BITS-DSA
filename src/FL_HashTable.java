public class FL_HashTable {
    private SL_HashTable[] hashTables;

    public FL_HashTable() {
        hashTables = new SL_HashTable[10];
    }

    public void initialize() {
        for (int i = 0; i < 10; i++) {
            hashTables[i] = new SL_HashTable();
        }
    }

    public SL_HashTable getHashTable(int index) {
        if (index >= 0 && index < 10) {
            return hashTables[index];
        } else {
            throw new IndexOutOfBoundsException("Invalid index for FL_HashTable");
        }
    }


    public void setHashTable(int index, SL_HashTable hashTable) {
        hashTables[index] = hashTable;
    }
}
