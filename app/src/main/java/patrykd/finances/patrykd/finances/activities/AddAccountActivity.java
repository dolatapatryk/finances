package patrykd.finances.patrykd.finances.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import patrykd.finances.R;

public class AddAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton appCompatButtonAdd;

    private AppCompatTextView textViewLinkBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        initViews();
        initListeners();
    }

    private void initViews(){
        appCompatButtonAdd = findViewById(R.id.appCompatButtonAdd);
        textViewLinkBack = findViewById(R.id.textViewLinkBack);
    }

    private void initListeners(){
        appCompatButtonAdd.setOnClickListener(this);
        textViewLinkBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.appCompatButtonAdd:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkBack:
                Intent intentAccount = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intentAccount);
                break;
        }
    }

    private void verifyFromSQLite(){};
}
