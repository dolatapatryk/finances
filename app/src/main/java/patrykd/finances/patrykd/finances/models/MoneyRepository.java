package patrykd.finances.patrykd.finances.models;

public abstract class MoneyRepository {

    private int id;
    private String name;
    private double amount;

    public MoneyRepository(int id, String name){
        this.id = id;
        this.name = name;
        this.amount = 0;
    }

    public MoneyRepository(String name){
        this.name = name;
        this.amount = 0;
    }
}
