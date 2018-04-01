package com.example.dhruvkalia.newproject.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dhruvkalia.newproject.Fragment.HomeFragment;
import com.example.dhruvkalia.newproject.R;

public class MoneyReceivedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_received);

        String amountReceived = getIntent().getStringExtra(HomeFragment.CODE_MONEY_SENT_ID);

        ((TextView) findViewById(R.id.text_view_amount_received)).setText("INR " + amountReceived);
    }
}
