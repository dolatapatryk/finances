package patrykd.finances.patrykd.finances.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import patrykd.finances.patrykd.finances.models.Category;

public class CategoryController {

    public static void addCategory(Category cat, String login, SQLiteDatabase db){
        int userId = UserController.getUserId(login, db);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", cat.getName());
        contentValues.put("user_id", userId);

        db.insert("categories", null, contentValues);
        db.close();
    }

    public static List<Category> getAllCategories(String login, SQLiteDatabase db){
        int userId = UserController.getUserId(login, db);
        List<Category> categories = new ArrayList<>();

        String[] columns = {"id", "name", "user_id"};

        String selection = "user_id = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("categories",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            do{
                Category cat = new Category();
                cat.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                cat.setName(cursor.getString(cursor.getColumnIndex("name")));
                cat.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id"))));
                categories.add(cat);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return categories;
    }

    public static void deleteCategory(Category cat, SQLiteDatabase db){
        db.delete("categories", "id = ?", new String[]{String.valueOf(cat.getId())});
        db.close();
    }
}
