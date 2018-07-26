package patrykd.finances.patrykd.finances.models;

import lombok.Data;
import lombok.Getter;

public @Data
class Category {

     private int id;
     private String name;
     private int user_id;

    public Category() {
    }
}
