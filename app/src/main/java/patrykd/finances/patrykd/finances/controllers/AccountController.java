package patrykd.finances.patrykd.finances.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import patrykd.finances.patrykd.finances.models.Account;

public class AccountController {

    public static void addAccount(Account acc, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("id", acc.getId());
        values.put("name", acc.getName());
        values.put("amount", acc.getAmount());

        db.insert("money_repositories", null, values);
        db.close();
    }

    public static List<Account> getAllAcounts(SQLiteDatabase db){
        List<Account> accounts = new ArrayList<>();

        String[] columns = {
                "id", "name", "amount"
        };

        Cursor cursor = db.query("money_repositories",
                columns,
                null,
                null,
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
