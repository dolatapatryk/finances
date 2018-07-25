package patrykd.finances.patrykd.finances.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import patrykd.finances.patrykd.finances.models.Account;

public class AccountController {

    public static void addAccount(Account acc, int userId, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("name", acc.getName());
        values.put("amount", acc.getAmount());
        values.put("user_id", userId);
        db.insert("money_repositories", null, values);
        db.close();
    }

    public static List<Account> getAllAcounts(String login, SQLiteDatabase db){
        int userId = UserController.getUserId(login, db);
        List<Account> accounts = new ArrayList<>();

        String[] columns = {
                "id", "name", "amount"
        };

        String selection = "user_id = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("money_repositories",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            do{
                Account account = new Account();
                account.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                account.setName(cursor.getString(cursor.getColumnIndex("name")));
                account.setAmount(Double.parseDouble(cursor.getString(cursor.getColumnIndex("amount"))));
                accounts.add(account);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return accounts;
    }
}
