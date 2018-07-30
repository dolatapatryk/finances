package patrykd.finances.patrykd.finances.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import patrykd.finances.patrykd.finances.models.Expense;

public class ExpenseController {

    public static void addExpense(Expense ex, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", ex.getAmount());
        contentValues.put("category_id", ex.getCategoryId());
        contentValues.put("user_id", ex.getUserId());
        contentValues.put("money_repository_id", ex.getMoneyRepositoryId());
        contentValues.put("date", ex.getDate().getTime());
        db.insert("expenses", null, contentValues);
        db.close();
    }

    public static List<Expense> getAllExpenses(String login, SQLiteDatabase db){
        int userId = UserController.getUserId(login, db);
        List<Expense> expenses = new ArrayList<>();

        String[] columns = {
               "amount", "category_id", "money_repository_id", "date"
        };

        String selection = "user_id = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("expenses",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            do{
                Expense ex = new Expense();
                ex.setAmount(Double.parseDouble(cursor.getString(cursor.getColumnIndex("amount"))));
                ex.setCategoryId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("category_id"))));
                ex.setUserId(userId);
                ex.setMoneyRepositoryId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("money_repository_id"))));
                ex.setDate(new Date(cursor.getLong(cursor.getColumnIndex("date"))));
                String categoryName = CategoryController.getCategoryName(ex.getCategoryId(), db);
                String accountName = AccountController.getAccountName(ex.getMoneyRepositoryId(), db);
                ex.setCategoryName(categoryName);
                ex.setAccountName(accountName);
                expenses.add(ex);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return expenses;
    }
}
