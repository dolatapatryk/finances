package patrykd.finances.patrykd.finances.models;

import lombok.Data;

public @Data
class Account extends MoneyRepository {

    public Account(int id, String name){
        super(id, name);
    }
    public Account(String name){ super(name);}

}
