package patrykd.finances.patrykd.finances.models;

import java.sql.Date;

import lombok.Data;

public @Data
class Expense {
    private double amount;
    private int categoryId;
    private int userId;
    private int moneyRepositoryId;
    private Date date;

    public Expense(double amount, int categoryId, int userId, int moneyRepositoryId, Date date){
        this.amount = amount;
        this.categoryId = categoryId;
        this.userId = userId;
        this.moneyRepositoryId = moneyRepositoryId;
        this.date = date;
    }
}
