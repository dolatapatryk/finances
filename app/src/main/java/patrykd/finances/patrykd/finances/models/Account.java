package patrykd.finances.patrykd.finances.models;

import lombok.Data;

public @Data
class Account {

    private int id;
    private String name;
    private double amount;

    public Account(int id, String name){
        this.id = id;
        this.name = name;
        this.amount = 0;
    }

    public Account(String name){
        this.name = name;
        this.amount = 0;
    }

    public Account(){}

    @Override
    public String toString(){
        return this.name.toUpperCase() + " amount: " + this.amount + " zł";
    }

}
