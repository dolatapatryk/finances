package patrykd.finances.patrykd.finances.models;

import lombok.Data;

public @Data
class Wallet extends MoneyRepository {

    public Wallet(int id, String name){
        super(id, name);
    }
}
