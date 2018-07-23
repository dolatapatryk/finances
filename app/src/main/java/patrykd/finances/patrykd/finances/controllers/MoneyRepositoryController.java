package patrykd.finances.patrykd.finances.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import patrykd.finances.patrykd.finances.models.MoneyRepository;

public class MoneyRepositoryController {

    public static void addMoneyRepository(MoneyRepository mr, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("id", mr.getId());
        values.put("name", mr.getName());
        values.put("amount", mr.getAmount());

        db.insert("money_repositories", null, values);
        db.close();
    }

    public static List<MoneyRepository> getAllMoneyRepositories(SQLiteDatabase db){
        List<MoneyRepository> repositories = new ArrayList<>();

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
                MoneyRepository repo = new MoneyRepository();
                repo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                repo.setName(cursor.getString(cursor.getColumnIndex("name")));
                repo.setAmount(Double.parseDouble(cursor.getString(cursor.getColumnIndex("amount"))));
                repositories.add(repo);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return repositories;
    }
}
