package patrykd.finances.patrykd.finances.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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

    public static String getCategoryName(int categoryId, SQLiteDatabase db){
        String query = "SELECT name from categories where id = ? ";

        String[] selectionArgs = {String.valueOf(categoryId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if(cursor.moveToFirst()){
            return cursor.getString(0);
        }else{
            return "";
        }
    }

    public static void setMonthlyAmountToCategory(List<Category> cats, SQLiteDatabase db){
        String query = "SELECT amount, date from expenses where category_id = ?";

        for(Category cat:cats){
            String[] selectionArgs = {String.valueOf(cat.getId())};
            double amount = 0;
            Cursor cursor = db.rawQuery(query, selectionArgs);
            if(cursor.moveToFirst()){
                do{
                    Date date = new Date(cursor.getLong(1));
                    Date today = new Date(System.currentTimeMillis());

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    Calendar calToday = Calendar.getInstance();
                    calToday.setTime(today);

                    if((cal.get(Calendar.MONTH) == calToday.get(Calendar.MONTH)) && (cal.get(Calendar.YEAR) == calToday.get(Calendar.YEAR))){
                        amount += cursor.getDouble(0);
                    }
                }while(cursor.moveToNext());
            }
            cat.setMonthlyAmount(amount);
        }

    }

    public static void setMonthlyAmountToCategory(int month, int year, List<Category> cats, SQLiteDatabase db){
        String query = "SELECT amount, date from expenses where category_id = ?";

        for(Category cat:cats){
            String[] selectionArgs = {String.valueOf(cat.getId())};
            double amount = 0;
            Cursor cursor = db.rawQuery(query, selectionArgs);
            if(cursor.moveToFirst()){
                do{
                    Date date = new Date(cursor.getLong(1));

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);


                    if((cal.get(Calendar.MONTH) == month) && (cal.get(Calendar.YEAR) == year)){
                        amount += cursor.getDouble(0);
                    }
                }while(cursor.moveToNext());
            }
            cat.setMonthlyAmount(amount);
        }
    }

    public static double calculateTotal(List<Category> cats){
        double amount = 0;
        for(Category cat:cats){
            amount += cat.getMonthlyAmount();
        }

        return amount;
    }
}
