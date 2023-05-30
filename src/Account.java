public class Account {
    private String panNumber;
    private String name;
    private String place;

    public Account(String panNumber, String name, String place) {
        this.panNumber = panNumber;
        this.name = name;
        this.place = place;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }
}

