package com.example.tehtava5;

import android.content.Context;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

public class BottleDispenser {
    private int bottles;
    private double money;
    private static BottleDispenser BD = null;

    private String name = "";
    private String price = "";
    private String size = "";

    ArrayList<Bottle> pepsiMax05List = new ArrayList<Bottle>();
    ArrayList<Bottle> pepsiMax15List = new ArrayList<Bottle>();
    ArrayList<Bottle> cokeZ05List = new ArrayList<Bottle>();
    ArrayList<Bottle> cokeZ15List = new ArrayList<Bottle>();
    ArrayList<Bottle> fantaZ05List = new ArrayList<Bottle>();
    ArrayList<Bottle> bottleList;

    private BottleDispenser() {
        bottles = 0;
        money = 0;

        Bottle pepsiMax05 = new Bottle("Pepsi Max", 0.5, 1.8);
        pepsiMax05List.add(pepsiMax05);
        bottles += 1;
        pepsiMax05List.add(pepsiMax05);
        bottles += 1;
        pepsiMax05List.add(pepsiMax05);
        bottles += 1;
        Bottle pepsiMax15 = new Bottle("Pepsi Max", 1.5, 2.2);
        pepsiMax15List.add(pepsiMax15);
        bottles += 1;
        pepsiMax15List.add(pepsiMax15);
        bottles += 1;
        pepsiMax15List.add(pepsiMax15);
        bottles += 1;
        Bottle cokeZ05 = new Bottle("Coca-Cola Zero", 0.5, 2.0);
        cokeZ05List.add(cokeZ05);
        bottles += 1;
        cokeZ05List.add(cokeZ05);
        bottles += 1;
        cokeZ05List.add(cokeZ05);
        bottles += 1;
        Bottle cokeZ15 = new Bottle("Coca-Cola Zero", 1.5, 2.5);
        cokeZ15List.add(cokeZ15);
        bottles += 1;
        cokeZ15List.add(cokeZ15);
        bottles += 1;
        cokeZ15List.add(cokeZ15);
        bottles += 1;
        Bottle fantaZ05 = new Bottle("Fanta Zero", 0.5, 1.95);
        fantaZ05List.add(fantaZ05);
        bottles += 1;
        fantaZ05List.add(fantaZ05);
        bottles += 1;
        fantaZ05List.add(fantaZ05);
        bottles += 1;
    }

    public static BottleDispenser getInstance() {
        if (BD == null) {
            BD = new BottleDispenser();
        }
        return BD;
    }

    public TextView addMoney(TextView output, TextView balance, double moreMoney) {
        money += moreMoney;

        Locale fi = new Locale("fi", "FI");
        String string = String.format(fi,  "%4.2f", money);

        output.setText("Klink! Added " + String.format(fi, "%.2f", moreMoney) + "€!\n" + output.getText());
        balance.setText(string + "€");
        return output;
    }

    public TextView buyBottle(TextView output, TextView balance, Spinner list) {
        String selected = (String) list.getSelectedItem();

        switch (selected) {
            case ("Pepsi Max 0,5l: 1,80€"):
                bottleList = pepsiMax05List;
                break;
            case ("Pepsi Max 1,5l: 2,20€"):
                bottleList = pepsiMax15List;
                break;
            case ("Coca-Cola Zero  0,5l: 2,00€"):
                bottleList = cokeZ05List;
                break;
            case ("Coca-Cola Zero 1,5l: 2,50€"):
                bottleList = cokeZ15List;
                break;
            case ("Fanta Zero 0,5l: 1,95€"):
                bottleList = fantaZ05List;
                break;
        }

        if (bottleList.size() != 0) {

            if ((bottles > 0) && ((money - (bottleList.get(0).getPrice())) >= 0)) {

                bottles -= 1;
                money -= bottleList.get(0).getPrice();

                Locale fi = new Locale("fi", "FI");
                String s = String.format(fi,  "%4.2f", money);
                balance.setText(s + "€");

                name = bottleList.get(0).getName();
                output.setText("KACHUNK! " + name
                        + " came out of the dispenser!\n" + output.getText());
                removeBottle(0);

                price = String.format(fi, "%4.2f", bottleList.get(0).getPrice());
                size = String.format(fi, "%4.2f", bottleList.get(0).getSize());
            }
            else if ((money - (bottleList.get(0).getPrice())) <= 0) {
                output.setText("Add money first!\n" + output.getText());
            }
        }
        else {
            output.setText("Out of bottles!\n" + output.getText());
        }
        return output;
    }

    public TextView returnMoney(double change, TextView output, TextView balance) {
        money = 0;
        String text = "";

        Locale fi = new Locale("fi", "FI");
        String string = String.format(fi,  "%4.2f", change);
        output.setText("Klink klink. Money came out! You got " + string + "€ back\n" + output.getText());
        balance.setText("0,00€");
        return output;
    }

    public void removeBottle(int choice) {
        bottleList.remove(choice);
    }
    public double changeMoney() {
        double change = money;
        return change;
    }

    public void makeReceipt(Context context, TextView output) {
        output.setText("Printing receipt...\n" + output.getText());
        try {
            OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput("receipt.txt", Context.MODE_PRIVATE));

            String s = "*** RECEIPT ***\n\n" +

                    "ITEM: " + name + "\n" +
                    "SIZE: " + size + "l\n" +
                    "PRICE: " + price + "€\n" +
                    "*** RECEIPT ***\n";
            out.write(s);
            out.close();
        } catch (IOException e) {
            Log.e("IOException", "Virhe syötteessä");
        } finally {
            System.out.println("KANSION SIJAINTI: " + context.getFilesDir());
            System.out.println("FILE WRITTEN");
        }
    }
}