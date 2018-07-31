package patrykd.finances.patrykd.finances.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import patrykd.finances.R;
import patrykd.finances.patrykd.finances.controllers.CategoryController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;
import patrykd.finances.patrykd.finances.models.Category;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = CategoryActivity.this;

    private AppCompatButton appCompatButtonAdd;
    private AppCompatButton appCompatButtonDelete;
    private ListView listViewCategory;
    private TextView textViewMonth2;
    private TextView textViewAmount2;
    private ArrayAdapter<Category> adapter;
    private DatabaseHelper db;

    private String userLogin;

    private int positionOnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent myIntent = getIntent();
        userLogin = myIntent.getStringExtra("login");
        System.out.println(userLogin);
        initViews();
        initListeners();
        initObjects();
        displayCategories();
        displayMonth();
    }

    private void initViews(){
        appCompatButtonAdd = findViewById(R.id.appCompatButtonAdd);
        appCompatButtonDelete = findViewById(R.id.appCompatButtonDelete);
        listViewCategory = findViewById(R.id.listViewCategory);
        textViewMonth2 = findViewById(R.id.textViewMonth2);
        textViewAmount2 = findViewById(R.id.textViewAmount2);
    }

    private void initListeners(){
        appCompatButtonAdd.setOnClickListener(this);
        appCompatButtonDelete.setOnClickListener(this);
        listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                positionOnList = position;
            }
        });
    }

    private void initObjects(){
        db = new DatabaseHelper(activity);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.appCompatButtonAdd:
                Intent intentAdd = new Intent(getApplicationContext(), AddCategoryActivity.class);
                intentAdd.putExtra("login", userLogin);
                startActivity(intentAdd);
                break;
            case R.id.appCompatButtonDelete:
                deleteCategory(positionOnList);
                break;
        }
    }

    private void displayCategories(){
        List<Category> categories = CategoryController.getAllCategories(userLogin, db.getReadableDatabase());
        CategoryController.setMonthlyAmountToCategory(categories, db.getReadableDatabase());
        String amount = String.format("%.2f zł", CategoryController.calculateTotal(categories));
        textViewAmount2.setText(amount);
        adapter = new ArrayAdapter<>(this, R.layout.row ,
                categories);
        listViewCategory.setAdapter(adapter);
    }

    private void deleteCategory(int position){
        Category cat = (Category) listViewCategory.getItemAtPosition(position);
        CategoryController.deleteCategory(cat, db.getWritableDatabase());
        displayCategories();
    }


    private void displayMonth(){
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String monthString = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
        int year = cal.get(Calendar.YEAR);
        String dateString = monthString + " - " + year;
        textViewMonth2.setText(dateString);
    }
}
