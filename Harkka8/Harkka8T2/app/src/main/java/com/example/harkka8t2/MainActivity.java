package com.example.harkka8t2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context context = null;
    TextView BottleList;
    TextView MoneyAmount;
    EditText Choice;
    TextView Receipt;
    SeekBar Money;
    TextView Moneybar;
    Spinner spinner;
    BottleDispenser BottleDispenser1 = BottleDispenser.getInstance();
    String stringchoice = "";
    double Moneysend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        BottleList = findViewById(R.id.textView2);
        MoneyAmount = findViewById(R.id.textView4);
        Money = findViewById(R.id.seekBar);
        Moneybar = findViewById(R.id.textView7);
        BottleDispenser1.listBottles(BottleList,MoneyAmount);
        spinner = findViewById(R.id.spinner5);
        Receipt = findViewById(R.id.textView3);
        Money.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Moneybar.setText("Add "+(double) progress/10+ " €.");
            Moneysend =  (double) progress/10;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final ArrayAdapter<Bottle> adapter = new ArrayAdapter<Bottle>(this, android.R.layout.simple_spinner_item, BottleDispenser.Array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItem().toString().equals("Machine is empty"))  {
                    BottleList.setText("Machine is empty.");
                }
                else if (position==0)  {
                    adapter.notifyDataSetChanged();
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }



    public void BuyBottle(View v) {
        if (spinner.getSelectedItem().toString().equals("Machine is empty")) {
            BottleList.setText("Machine is empty. Add more bottles.");
        } else {
            Bottle bottle = (Bottle) spinner.getSelectedItem();
            int choice = spinner.getSelectedItemPosition();
            BottleDispenser1.buyBottle(BottleList, choice, context, bottle.getName(), bottle.getPrice());
            BottleDispenser1.readFile(context, Receipt);
            spinner.setSelection(0);
            BottleDispenser1.listBottles(BottleList, MoneyAmount);
            if (spinner.getSelectedItem() == null) {
                ArrayList<String> Empty = new ArrayList<String>();
                Empty.add("Machine is empty");
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Empty);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                BottleList.setText("Machine is empty.");
                spinner.setAdapter(adapter2);
            }
        }
    }
    public void AddMoney(View v)    {
        BottleDispenser1.addMoney(BottleList,Moneysend);
        Money.setProgress(0);
        BottleDispenser1.listBottles(BottleList, MoneyAmount);
    }
    public void ReturnMoney(View v)    {
        BottleDispenser1.returnMoney(BottleList);
    }
    public void Refresh(View v) {
        BottleDispenser1.Replenish();
        ArrayAdapter<Bottle> adapter = new ArrayAdapter<Bottle>(this, android.R.layout.simple_spinner_item, BottleDispenser.Array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        BottleDispenser1.listBottles(BottleList, MoneyAmount);
    }
}
