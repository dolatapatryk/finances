package patrykd.finances.patrykd.finances.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import patrykd.finances.MainActivity;
import patrykd.finances.R;
import patrykd.finances.patrykd.finances.controllers.CategoryController;
import patrykd.finances.patrykd.finances.controllers.InputValidation;
import patrykd.finances.patrykd.finances.controllers.UserController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;
import patrykd.finances.patrykd.finances.models.Category;

public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = AddCategoryActivity.this;


    private TextInputLayout textInputLayoutName;
    private TextInputEditText textInputEditTextName;
    private AppCompatButton appCompatButtonAdd;

    private InputValidation inputValidation;
    private DatabaseHelper db;

    private String userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Intent myIntent = getIntent();
        userLogin = myIntent.getStringExtra("login");

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        textInputLayoutName = findViewById(R.id.textInputLayoutName);
        textInputEditTextName = findViewById(R.id.textInputEditTextName);
        appCompatButtonAdd = findViewById(R.id.appCompatButtonAdd);
    }

    private void initListeners(){
        appCompatButtonAdd.setOnClickListener(this);
    }

    private void initObjects(){
        inputValidation = new InputValidation(activity);
        db = new DatabaseHelper(activity);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.appCompatButtonAdd:
                addCategory();
                break;
        }
    }

    private void addCategory(){
        String error = "Enter category name";
        String name = textInputEditTextName.getText().toString().trim();

        if(!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, error)){
            return;
        }
        int userId = UserController.getUserId(userLogin, db.getReadableDatabase());
        Category cat = new Category();
        cat.setName(name);
        cat.setId(userId);
        CategoryController.addCategory(cat, userLogin, db.getWritableDatabase());
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        mainIntent.putExtra("login", userLogin);
        startActivity(mainIntent);

    }
}
