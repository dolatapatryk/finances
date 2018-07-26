package patrykd.finances.patrykd.finances.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import patrykd.finances.MainActivity;
import patrykd.finances.patrykd.finances.models.User;

public class UserController {

    public static void addUser(User user, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("login", user.getLogin());
        values.put("password", user.getPassword());

        db.insert("users", null, values);
        db.close();
    }

    public static List<User> getAllUsers(SQLiteDatabase db){
        List<User> users = new ArrayList<>();

        String[] columns = {
          "id", "login", "password"
        };

        Cursor cursor = db.query("users",
                columns,
                null,
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                user.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                users.add(user);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return users;
    }

    public static void deleteUser(User user, SQLiteDatabase db){
        db.delete("users", "id = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public static boolean isLoginTaken(String login, SQLiteDatabase db){
        String[] columns = {"id"};
        String selection = "login = ?";
        String selectionArgs[] = {login};

        Cursor cursor = db.query("users",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount > 0){
            return true;
        }

        return false;
    }

    public static boolean checkIfUserExist(String login, String password, SQLiteDatabase db){
        String[] columns={
                "id"
        };

        String selection = "login = ? and password = ?";

        String[] selectionArgs = {login, password};

        Cursor cursor = db.query("users",
        columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if(cursorCount > 0){
            return true;
        }

        return false;
    }

    public static int getUserId(String login, SQLiteDatabase db){
        String query = "SELECT id from users where login = ? ";

        String[] selectionArgs = {login};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if(cursor.moveToFirst()){
            return cursor.getInt(0);
        }else{
            return -1;
        }
    }
}
