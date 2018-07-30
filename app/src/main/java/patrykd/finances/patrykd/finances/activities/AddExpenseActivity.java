package patrykd.finances.patrykd.finances.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Date;
import java.util.List;

import patrykd.finances.MainActivity;
import patrykd.finances.R;
import patrykd.finances.patrykd.finances.controllers.AccountController;
import patrykd.finances.patrykd.finances.controllers.CategoryController;
import patrykd.finances.patrykd.finances.controllers.ExpenseController;
import patrykd.finances.patrykd.finances.controllers.InputValidation;
import patrykd.finances.patrykd.finances.controllers.UserController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;
import patrykd.finances.patrykd.finances.models.Account;
import patrykd.finances.patrykd.finances.models.Category;
import patrykd.finances.patrykd.finances.models.Expense;

public class AddExpenseActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = AddExpenseActivity.this;

    private TextInputLayout textInputLayoutAmount;
    private TextInputEditText textInputEditTextAmount;
    private Spinner spinner;
    private AppCompatButton appCompatButtonAdd;

    private InputValidation inputValidation;
    private DatabaseHelper db;

    private String userLogin;
    private int accountId;
    private int categoryId;
    private ArrayAdapter<Category> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Intent myIntent = getIntent();
        userLogin = myIntent.getStringExtra("login");
        accountId = myIntent.getIntExtra("accountId", 1);

        initViews();
        initListeners();
        initObjects();
        displayCategories();
    }

    private void initViews(){
        textInputLayoutAmount = findViewById(R.id.textInputLayoutAmount);
        textInputEditTextAmount = findViewById(R.id.textInputEditTextAmount);
        spinner = findViewById(R.id.spinner);
        appCompatButtonAdd = findViewById(R.id.appCompatButtonAdd);
    }

    private void initListeners(){
        appCompatButtonAdd.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category cat = (Category) spinner.getItemAtPosition(position);
                categoryId = cat.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initObjects(){
        inputValidation = new InputValidation(activity);
        db = new DatabaseHelper(activity);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.appCompatButtonAdd:
                addExpense();
                break;
        }
    }

    private void displayCategories(){
        List<Category> cats = CategoryController.getAllCategories(userLogin, db.getReadableDatabase());
        adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, cats);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void addExpense(){
        String errorAmount = "Enter the amount";
        Double amount = Double.parseDouble(textInputEditTextAmount.getText().toString().trim());
        if(!inputValidation.isInputEditTextFilled(textInputEditTextAmount, textInputLayoutAmount, errorAmount)){
            return;
        }
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date.toString());
        int userId = UserController.getUserId(userLogin, db.getReadableDatabase());
        Expense ex = new Expense(amount, categoryId, userId, accountId, date);
        ExpenseController.addExpense(ex, db.getWritableDatabase());
        Account acc = AccountController.getAccount(accountId, db.getReadableDatabase());
        double money = acc.getAmount() - amount;
        AccountController.subtractMoneyFromAccount(accountId, money, db.getWritableDatabase());
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        mainIntent.putExtra("login", userLogin);
        startActivity(mainIntent);
    }
}
