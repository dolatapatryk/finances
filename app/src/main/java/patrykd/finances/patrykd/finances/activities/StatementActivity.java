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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import patrykd.finances.R;
import patrykd.finances.patrykd.finances.controllers.CategoryController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;
import patrykd.finances.patrykd.finances.models.Category;
import patrykd.finances.patrykd.finances.models.Month;

public class StatementActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = StatementActivity.this;

    private TextInputLayout textInputLayoutYear;
    private TextInputEditText textInputEditTextYear;
    private Spinner spinner;
    private ListView listViewCategory;
    private AppCompatButton appCompatButtonShow;
    private AppCompatButton appCompatButtonChart;
    private TextView textViewAmount;

    private int month;
    private int year;
    private String userLogin;
    private ArrayAdapter<Month> adapterMonth;
    private ArrayAdapter<Category> adapterCategory;
    private DatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        Intent myIntent = getIntent();
        userLogin = myIntent.getStringExtra("login");

        initViews();
        initListeners();
        initObjects();
        displayMonths();
    }

    private void initViews(){
        textInputLayoutYear = findViewById(R.id.textInputLayoutYear);
        textInputEditTextYear = findViewById(R.id.textInputEditTextYear);
        spinner = findViewById(R.id.spinner);
        appCompatButtonShow = findViewById(R.id.appCompatButtonShow);
        appCompatButtonChart = findViewById(R.id.appCompatButtonChart);
        listViewCategory = findViewById(R.id.listViewCategory);
        textViewAmount = findViewById(R.id.textViewAmount);
    }

    private void initListeners(){
        appCompatButtonShow.setOnClickListener(this);
        appCompatButtonChart.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initObjects(){
        db = new DatabaseHelper(activity);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.appCompatButtonShow:
                year = Integer.parseInt(textInputEditTextYear.getText().toString().trim());
                showStatement(month, year);
                appCompatButtonChart.setVisibility(View.VISIBLE);
                break;
            case R.id.appCompatButtonChart:
                Intent chartIntent = new Intent(activity, ChartActivity.class);
                chartIntent.putExtra("login", userLogin);
                chartIntent.putExtra("month", month);
                chartIntent.putExtra("year", year);
                startActivity(chartIntent);
                break;
        }
    }

    private void displayMonths(){
        adapterMonth = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Month.values());
        adapterMonth.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterMonth);
    }

    private void showStatement(int month, int year){
        List<Category> categories = CategoryController.getAllCategories(userLogin, db.getReadableDatabase());
        CategoryController.setMonthlyAmountToCategory(month, year, categories, db.getReadableDatabase());
        String amount = String.format("%.2f zł", CategoryController.calculateTotal(categories));
        textViewAmount.setText(amount);
        adapterCategory = new ArrayAdapter<>(this, R.layout.row ,
                categories);
        listViewCategory.setAdapter(adapterCategory);
        listViewCategory.setEnabled(true);
    }
}
