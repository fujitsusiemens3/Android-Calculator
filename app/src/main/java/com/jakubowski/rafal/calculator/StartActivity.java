package com.jakubowski.rafal.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button simple_btn;
    private Button advanced_btn;
    private Button about_btn;
    private Button exit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_start);
        initializeButtons();

        simple_btn.setOnClickListener(this);
        advanced_btn.setOnClickListener(this);
        about_btn.setOnClickListener(this);
        exit_btn.setOnClickListener(this);
    }

    private void initializeButtons() {
        simple_btn = findViewById(R.id.simple_calc_btn);
        advanced_btn = findViewById(R.id.advanced_calc_btn);
        about_btn = findViewById(R.id.about_calc_btn);
        exit_btn = findViewById(R.id.exit_calc_btn);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.simple_calc_btn:
                Intent intent_simple = new Intent(getApplicationContext(), SimpleActivity.class);
                StartActivity.this.startActivity(intent_simple);
                break;

            case R.id.advanced_calc_btn:
                Intent intent_advance = new Intent(getApplicationContext(), AdvancedActivity.class);
                StartActivity.this.startActivity(intent_advance);
                break;

            case R.id.about_calc_btn:
                Intent intent_about = new Intent(getApplicationContext(), AboutActivity.class);
                StartActivity.this.startActivity(intent_about);
                break;

            case R.id.exit_calc_btn:
                finish();
                System.exit(0);
        }
    }
}
