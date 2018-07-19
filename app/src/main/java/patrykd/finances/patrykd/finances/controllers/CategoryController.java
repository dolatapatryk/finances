package patrykd.finances.patrykd.finances.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import patrykd.finances.patrykd.finances.models.Category;

public class CategoryController {

    public static void addCategory(Category cat, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", cat.getName());

        db.insert("categories", null, contentValues);
        db.close();
    }

    public List<Category> getAllCategories(SQLiteDatabase db){
        List<Category> categories = new ArrayList<>();

        String[] columns = {"id", "name"};

        Cursor cursor = db.query("categories",
                columns,
                null,
                null,
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            do{
                Category cat = new Category();
                cat.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                cat.setName(cursor.getString(cursor.getColumnIndex("name")));
                cat.add(user);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return users;
    }
}
