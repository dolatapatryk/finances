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

    public static Account getAccount(int accountId, SQLiteDatabase db){
        Account acc = null;

        String[] columns = {
                "id", "name", "amount"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(accountId)};

        Cursor cursor = db.query("money_repositories",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            acc = new Account();
            acc.setId(accountId);
            acc.setName(cursor.getString(cursor.getColumnIndex("name")));
            acc.setAmount(Double.parseDouble(cursor.getString(cursor.getColumnIndex("amount"))));
        }
        cursor.close();
        db.close();

        return acc;
    }

    public static void deleteAccount(Account acc, SQLiteDatabase db){
        db.delete("money_repositories", "id = ?", new String[]{String.valueOf(acc.getId())});
        db.close();
    }

    public static void addMoneyToAccount(Account acc, double money, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", money);
        db.update("money_repositories", contentValues, "id = ?", new String[]{String.valueOf(acc.getId())});
        db.close();
    }

    public static String getAccountName(int accountId, SQLiteDatabase db){
        String query = "SELECT name from money_repositories where id = ? ";

        String[] selectionArgs = {String.valueOf(accountId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if(cursor.moveToFirst()){
            return cursor.getString(0);
        }else{
            return "";
        }
    }

    public static void subtractMoneyFromAccount(int accountId, double money, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", money);
        db.update("money_repositories", contentValues, "id = ?", new String[]{String.valueOf(accountId)});
        db.close();
    }
}
