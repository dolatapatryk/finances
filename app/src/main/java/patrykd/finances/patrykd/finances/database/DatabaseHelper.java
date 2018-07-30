package patrykd.finances.patrykd.finances.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "baza.db";

    private String createUsersTable = "CREATE TABLE users(" +
            "id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "login TEXT NOT NULL, " +
            "password TEXT NOT NULL)";
    private String createCategoriesTable = "create table categories(\n" +
            "id integer primary key autoincrement,\n" +
            "name text not null," +
            "user_id integer not null," +
            "foreign key (user_id) references users(id))";
    private String createMoneyRepositoriesTable = "create table money_repositories(\n" +
            "id integer primary key autoincrement,\n" +
            "name text not null,\n" +
            "amount number not null,\n" +
            "user_id integer not null,\n" +
            "foreign key (user_id) references users(id))";
    private String createExpensesTable = "create table expenses(\n" +
            "amount number not null,\n" +
            "category_id integer not null,\n" +
            "user_id integer not null,\n" +
            "money_repository_id integer not null,\n" +
            "date long not null,\n" +
            "foreign key (category_id) references categories(id),\n" +
            "foreign key (user_id) references users(id),\n" +
            "foreign key (money_repository_id) references money_repositories(id)\n" +
            ")";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUsersTable);
        db.execSQL(createCategoriesTable);
        db.execSQL(createMoneyRepositoriesTable);
        db.execSQL(createExpensesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
