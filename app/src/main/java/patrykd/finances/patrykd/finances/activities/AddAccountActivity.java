package patrykd.finances.patrykd.finances.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import org.w3c.dom.Text;

import patrykd.finances.MainActivity;
import patrykd.finances.R;
import patrykd.finances.patrykd.finances.controllers.AccountController;
import patrykd.finances.patrykd.finances.controllers.InputValidation;
import patrykd.finances.patrykd.finances.controllers.UserController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;
import patrykd.finances.patrykd.finances.models.Account;

public class AddAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AddAccountActivity.this;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutAmount;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextAmount;

    private AppCompatButton appCompatButtonAdd;

    private AppCompatTextView textViewLinkBack;

    private DatabaseHelper db;
    private InputValidation inputValidation;

    private String userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        Intent myIntent = getIntent();
        userLogin = myIntent.getStringExtra("login");

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        textInputLayoutName = findViewById(R.id.textInputLayoutName);
        textInputLayoutAmount = findViewById(R.id.textInputLayoutAmount);
        textInputEditTextName = findViewById(R.id.textInputEditTextName);
        textInputEditTextAmount = findViewById(R.id.textInputEditTextAmount);
        appCompatButtonAdd = findViewById(R.id.appCompatButtonAdd);
        textViewLinkBack = findViewById(R.id.textViewLinkBack);
    }

    private void initListeners(){
        appCompatButtonAdd.setOnClickListener(this);
        textViewLinkBack.setOnClickListener(this);
    }

    private void initObjects(){
        db = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.appCompatButtonAdd:
                addAccount();
                break;
            case R.id.textViewLinkBack:
                Intent intentAccount = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentAccount);
                break;
        }
    }

    private void addAccount(){
        String errorName = "Enter the account's name";
        String errorAmount = "Enter the amount";
        String name = textInputEditTextName.getText().toString().trim();
        Double amount = Double.parseDouble(textInputEditTextAmount.getText().toString().trim());
        if(!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, errorName)){
            return;
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextAmount, textInputLayoutAmount, errorAmount)){
            return;
        }
        Account acc = new Account();
        acc.setName(name);
        acc.setAmount(amount);
        int userId = UserController.getUserId(userLogin, db.getReadableDatabase());
        AccountController.addAccount(acc, userId, db.getWritableDatabase());
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        mainIntent.putExtra("login", userLogin);
        startActivity(mainIntent);
    };
}
