package patrykd.finances.patrykd.finances.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import patrykd.finances.R;
import patrykd.finances.patrykd.finances.controllers.AccountController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;
import patrykd.finances.patrykd.finances.models.Account;


public class AccountActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AccountActivity.this;

    private AppCompatButton appCompatButtonAdd;
    private AppCompatButton appCompatButtonDelete;

    private ListViewCompat listViewAccount;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initViews();
        initListeners();
        initObjects();
        displayAccounts();
    }

    private void initViews(){
        appCompatButtonAdd = findViewById(R.id.appCompatButtonAdd);
        appCompatButtonDelete = findViewById(R.id.appCompatButtonDelete);
        listViewAccount = findViewById(R.id.listViewAccount);
    }

    private void initListeners(){
        appCompatButtonAdd.setOnClickListener(this);
        appCompatButtonDelete.setOnClickListener(this);
        listViewAccount.setOnClickListener(this);
    }

    private void initObjects(){
        db = new DatabaseHelper(activity);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.appCompatButtonAdd:
                Intent intentAdd = new Intent(getApplicationContext(), AddAccountActivity.class);
                startActivity(intentAdd);
                break;
            case R.id.appCompatButtonDelete:

                break;
        }
    }

    private void displayAccounts(){
        ArrayAdapter<Account> adapter = new ArrayAdapter<>(this, R.layout.activity_account,
                AccountController.getAllAcounts(db.getReadableDatabase()));
        listViewAccount.setAdapter(adapter);
    }
}
