package patrykd.finances.patrykd.finances.activities;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import patrykd.finances.R;
import patrykd.finances.patrykd.finances.controllers.InputValidation;
import patrykd.finances.patrykd.finances.controllers.UserController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;
import patrykd.finances.patrykd.finances.models.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutLogin;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextLogin;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        nestedScrollView = findViewById(R.id.nestedScrollView);

        textInputLayoutLogin = findViewById(R.id.textInputLayoutLogin);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextLogin = findViewById(R.id.textInputEditTextLogin);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = findViewById(R.id.appCompatTextViewLoginLink);
    }

    private void initListeners(){
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }

    private void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    private void postDataToSQLite(){
        String errorLogin = "Enter valid login";
        String errorPassword = "Enter password";
        String errorConfirm = "Password does not match";
        String succesfulRegistration = "Registration succesful";
        String loginTaken = "Error, login is already taken";
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
        if(!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, errorConfirm)){
            return;
        }

        if(!UserController.isLoginTaken(login, databaseHelper.getReadableDatabase())){
            user.setLogin(login);
            user.setPassword(password);

            UserController.addUser(user, databaseHelper.getWritableDatabase());

            Snackbar.make(nestedScrollView, succesfulRegistration, Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        }else{
            Snackbar.make(nestedScrollView, loginTaken, Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText(){
        textInputEditTextLogin.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}
