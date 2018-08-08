package patrykd.finances.patrykd.finances.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import patrykd.finances.MainActivity;
import patrykd.finances.R;
import patrykd.finances.patrykd.finances.controllers.CategoryController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;
import patrykd.finances.patrykd.finances.models.Category;

public class ChartActivity extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = ChartActivity.this;

    private AppCompatTextView appCompatTextViewBack;
    private PieChart pieChart;

    private String userLogin;
    private int month;
    private int year;

    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Intent myIntent = getIntent();
        userLogin = myIntent.getStringExtra("login");
        month = myIntent.getIntExtra("month", 8);
        year = myIntent.getIntExtra("year", 2018);

        initViews();
        initListeners();
        initObjects();
        setChartOptions(initDescription());
        initChart();
    }

    private void initViews(){
        appCompatTextViewBack = findViewById(R.id.appCompatTextViewBack);
        pieChart = findViewById(R.id.pieChart);
    }

    private void initListeners(){
        appCompatTextViewBack.setOnClickListener(this);
    }

    private void initObjects(){
        db = new DatabaseHelper(activity);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.appCompatTextViewBack:
                Intent mainIntent = new Intent(activity, MainActivity.class);
                mainIntent.putExtra("login", userLogin);
                startActivity(mainIntent);
                break;

        }
    }

    private void setChartOptions(Description description){
        pieChart.setUsePercentValues(true);
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setRotationEnabled(true);
        pieChart.setCenterTextSize(16f);
        initLegend(pieChart.getLegend());
        pieChart.setEntryLabelColor(Color.BLACK);
    }

    private void initChart(){
        List<Category> cats = CategoryController.getAllCategories(userLogin, db.getReadableDatabase());
        PieDataSet dataSet = initValues(cats);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(14f);
        pieChart.setData(data);
        pieChart.invalidate();
    }

    private PieDataSet initValues(List<Category> cats){
        CategoryController.setMonthlyAmountToCategory(month, year, cats, db.getReadableDatabase());
        ArrayList<PieEntry> yValues = new ArrayList<>();
//        for(int i=0;i<cats.size();i++){
//            yValues.add(new PieEntry((float)cats.get(i).getMonthlyAmount(), cats.get(i).getName().toUpperCase()));
//        }

        for(Category cat:cats){
            if(cat.getMonthlyAmount() > 0){
                yValues.add(new PieEntry((float)cat.getMonthlyAmount(), cat.getName().toUpperCase()));
            }
        }

        PieDataSet dataSet = new PieDataSet(yValues, "");
        return dataSet;
    }

    private Description initDescription(){
        Description description = new Description();
        description.setText("Monthly statement pie chart (%)");
        description.setTextSize(16f);
        description.setTextColor(Color.WHITE);

        return description;
    }

    private void initLegend(Legend legend){
        legend.setEnabled(false);
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(16f);
        legend.setWordWrapEnabled(false);
    }

}
