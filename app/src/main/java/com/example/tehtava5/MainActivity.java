package com.example.tehtava5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tOutput, tName, balance, moneyList;
    Button buyBottle, addMoney, returnMoney, printReceipt;
    SeekBar moneyCount;
    Spinner list;

    String[] bottle = {"Pepsi Max 0,5l: 1,80€", "Pepsi Max 1,5l: 2,20€", "Coca-Cola Zero  0,5l: 2,00€",
            "Coca-Cola Zero 1,5l: 2,50€", "Fanta Zero 0,5l: 1,95€"};
    double money = 0.00;
    double moreMoney = 1.00;

    Context context = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        moneyList = (TextView) findViewById(R.id.moneyList);
        moneyList.setText("  0,05€    0,10€    0,20€    0,50€    1,00€    2,00€");

        list = (Spinner) findViewById(R.id.list);

        ArrayAdapter AA = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bottle);
        list.setAdapter(AA);

        BottleDispenser dispenser = BottleDispenser.getInstance();

        tName = (TextView) findViewById(R.id.BottleDispenser);
        tName.setText("Bottle Dispenser");

        tOutput = (TextView) findViewById(R.id.Output);
        tOutput.setText((""));

        Locale fi = new Locale("fi", "FI");
        String sMoney = String.format(fi,  "%4.2f", money);

        balance = (TextView) findViewById(R.id.balance);
        balance.setText(sMoney + "€");

        buyBottle = (Button) findViewById(R.id.buyBottle);
        buyBottle.setOnClickListener(v -> tOutput = dispenser.buyBottle(tOutput, balance, list));

        printReceipt = (Button) findViewById(R.id.getReceipt);
        printReceipt.setOnClickListener(v -> dispenser.makeReceipt(context, tOutput));

        addMoney = (Button) findViewById(R.id.addMoney);
        addMoney.setOnClickListener(v -> tOutput = dispenser.addMoney(tOutput, balance, moreMoney));

        returnMoney = (Button) findViewById(R.id.returnMoney);
        returnMoney.setOnClickListener(v -> {
            money = dispenser.changeMoney();
            tOutput = dispenser.returnMoney(money, tOutput, balance);
        });

        moneyCount = (SeekBar) findViewById(R.id.moneyCount);
        moneyCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int arvo = seekBar.getProgress();

                switch (arvo) {
                    case (0):
                        moreMoney = 0.05;
                        break;
                    case (1):
                        moreMoney = 0.10;
                        break;
                    case (2):
                        moreMoney = 0.20;
                        break;
                    case (3):
                        moreMoney = 0.50;
                        break;
                    case (4):
                        moreMoney = 1.00;
                        break;
                    case (5):
                        moreMoney = 2.00;
                        break;

                }
            }
        });
    }
}