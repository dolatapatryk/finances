package patrykd.finances.patrykd.finances.models;

import lombok.Data;
import lombok.Getter;

public @Data
class Category {

     private int id;
     private String name;
     private int user_id;
     private double monthlyAmount;

    public Category() {
    }

    @Override
    public String toString(){
        return name.toUpperCase() + " - " + String.format("%.2f zł", monthlyAmount);
    }
}
