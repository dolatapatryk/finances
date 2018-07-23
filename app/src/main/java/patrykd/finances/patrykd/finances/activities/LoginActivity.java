package patrykd.finances.patrykd.finances.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import patrykd.finances.MainActivity;
import patrykd.finances.R;
import patrykd.finances.patrykd.finances.controllers.InputValidation;
import patrykd.finances.patrykd.finances.controllers.UserController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutLogin;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextLogin;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutLogin = findViewById(R.id.textInputLayoutLogin);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        textInputEditTextLogin = findViewById(R.id.textInputEditTextLogin);
        textInputEditTextPassword  = findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);
    }

    private void initListeners(){
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite(){
        String errorLogin = "Enter valid login";
        String errorPassword = "Enter password";
        String login = textInputEditTextLogin.getText().toString().trim();
        String password = textInputEditTextPassword.getText().toString().trim();
        if(!inputValidation.isInputEditTextFilled(textInputEditTextLogin, textInputLayoutLogin, errorLogin)) {
            return;
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextLogin, textInputLayoutLogin, errorLogin)){
            return;
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, errorPassword)){
            return;
        }

        if(UserController.checkIfUserExist(login, password, databaseHelper.getReadableDatabase())){
            Intent mainIntent = new Intent(activity, MainActivity.class);
            mainIntent.putExtra("LOGIN", login);
            emptyInputEditText();
            startActivity(mainIntent);
        }else{
            String error = "Wrong login or password";
            Snackbar.make(nestedScrollView, error, Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText(){
        textInputEditTextLogin.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
