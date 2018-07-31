package patrykd.finances.patrykd.finances.models;

import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;

import lombok.Data;
import patrykd.finances.patrykd.finances.controllers.AccountController;
import patrykd.finances.patrykd.finances.controllers.CategoryController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;

public @Data
class Expense {
    private double amount;
    private int categoryId;
    private int userId;
    private int moneyRepositoryId;
    private Date date;
    private String categoryName;
    private String accountName;

    public Expense(double amount, int categoryId, int userId, int moneyRepositoryId, Date date){
        this.amount = amount;
        this.categoryId = categoryId;
        this.userId = userId;
        this.moneyRepositoryId = moneyRepositoryId;
        this.date = date;
    }

    public Expense(){}

    public String toString(){
        return amount + " zł - " + categoryName.toUpperCase() + " - "+
                accountName.toUpperCase() + " - " + date;
    }
}
