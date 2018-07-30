package patrykd.finances.patrykd.finances.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import patrykd.finances.R;
import patrykd.finances.patrykd.finances.controllers.ExpenseController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;
import patrykd.finances.patrykd.finances.models.Expense;

public class ExpenseActivity extends AppCompatActivity {

    private final AppCompatActivity activity = ExpenseActivity.this;

    private ListView listViewExpenses;

    private ArrayAdapter<Expense> adapter;
    private DatabaseHelper db;

    private String userLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        Intent myIntent = getIntent();
        userLogin = myIntent.getStringExtra("login");
        System.out.println(userLogin);
        initViews();
        initObjects();
        displayExpenses();
    }

    private void initViews(){
        listViewExpenses = findViewById(R.id.listViewExpenses);
    }

    private void initObjects(){
        db = new DatabaseHelper(activity);
    }

    private void displayExpenses(){
        List<Expense> accounts = ExpenseController.getAllExpenses(userLogin, db.getReadableDatabase());
        adapter = new ArrayAdapter<>(this, R.layout.row,
                accounts);
        listViewExpenses.setAdapter(adapter);
    }
}
