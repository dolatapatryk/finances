package patrykd.finances;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import patrykd.finances.patrykd.finances.activities.AddAccountActivity;
import patrykd.finances.patrykd.finances.controllers.AccountController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;
import patrykd.finances.patrykd.finances.models.Account;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private final AppCompatActivity activity = MainActivity.this;

    private AppCompatButton appCompatButtonAdd;
    private AppCompatButton appCompatButtonDelete;

    private TextView textViewAmount;

    private ListView listViewAccount;
    private ArrayAdapter<Account> adapter;

    private DatabaseHelper db;

    public static String userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initViews();
        initListeners();
        initObjects();

        Intent myIntent = getIntent();
        userLogin = myIntent.getStringExtra("login");
        System.out.println(userLogin);

        displayAccounts();
    }

    private void initViews(){
        appCompatButtonAdd = findViewById(R.id.appCompatButtonAdd);
        appCompatButtonDelete = findViewById(R.id.appCompatButtonDelete);
        listViewAccount = findViewById(R.id.listViewAccount);
        textViewAmount = findViewById(R.id.textViewAmount);
    }

    private void initListeners(){
        appCompatButtonAdd.setOnClickListener(this);
        appCompatButtonDelete.setOnClickListener(this);
    }

    private void initObjects(){
        db = new DatabaseHelper(activity);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.accounts) {
            displayAccounts();

        } else if (id == R.id.expenses) {

        } else if (id == R.id.categories) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.appCompatButtonAdd:
                Intent intentAdd = new Intent(getApplicationContext(), AddAccountActivity.class);
                intentAdd.putExtra("login", userLogin);
                startActivity(intentAdd);
                break;
            case R.id.appCompatButtonDelete:

                break;
        }
    }

    private void displayAccounts(){
        List<Account> accounts = AccountController.getAllAcounts(userLogin, db.getReadableDatabase());
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,
                accounts);
        listViewAccount.setAdapter(adapter);
        double amount = 0;
        for(Account acc:accounts){
            amount += acc.getAmount();
        }
        textViewAmount.setText(String.valueOf(amount));
    }
}
