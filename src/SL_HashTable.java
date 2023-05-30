public class SL_HashTable {
    private int size;
    private Account[] accounts;

    public SL_HashTable() {
        this.size = 13;
        this.accounts = new Account[size];
    }

    public SL_HashTable(int size) {
        this.size = size;
        this.accounts = new Account[size];
    }

    public int getSize() {
        return size;
    }

    public Account[] getAccounts() {
        return accounts;
    }
}
