package patrykd.finances.patrykd.finances.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import patrykd.finances.R;
import patrykd.finances.patrykd.finances.controllers.CategoryController;
import patrykd.finances.patrykd.finances.database.DatabaseHelper;
import patrykd.finances.patrykd.finances.models.Category;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = CategoryActivity.this;

    private AppCompatButton appCompatButtonAdd;
    private AppCompatButton appCompatButtonDelete;
    private ListView listViewCategory;
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
    }

    private void initViews(){
        appCompatButtonAdd = findViewById(R.id.appCompatButtonAdd);
        appCompatButtonDelete = findViewById(R.id.appCompatButtonDelete);
        listViewCategory = findViewById(R.id.listViewCategory);
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
        List<Category> accounts = CategoryController.getAllCategories(userLogin, db.getReadableDatabase());
        adapter = new ArrayAdapter<>(this, R.layout.row ,
                accounts);
        listViewCategory.setAdapter(adapter);
    }

    private void deleteCategory(int position){
        Category cat = (Category) listViewCategory.getItemAtPosition(position);
        CategoryController.deleteCategory(cat, db.getWritableDatabase());
        displayCategories();
    }
}
