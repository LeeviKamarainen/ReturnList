package com.example.harkka8t2;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
public class BottleDispenser {


        private int bottles=5;

        private double money;
        public static ArrayList<Bottle> Array = new ArrayList<Bottle>();	{

            Array.add(new Bottle("Pepsi Max", 1.8, 0.5));
            Array.add(new Bottle("Pepsi Max", 2.2, 1.5));
            Array.add(new Bottle("Coca-Cola Zero", 2.0, 0.5));
            Array.add(new Bottle("Coca-Cola Zero", 2.5, 1.5));
            Array.add(new Bottle("Fanta Zero", 1.95, 0.5));
        }

        public void Replenish() {
            Array.add(new Bottle("Pepsi Max", 1.8, 0.5));
            Array.add(new Bottle("Pepsi Max", 2.2, 1.5));
            Array.add(new Bottle("Coca-Cola Zero", 2.0, 0.5));
            Array.add(new Bottle("Coca-Cola Zero", 2.5, 1.5));
            Array.add(new Bottle("Fanta Zero", 1.95, 0.5));
            bottles+=5;
        }

        private static BottleDispenser BD = new BottleDispenser();

        private BottleDispenser() {

            bottles = 5;

            money = 0;
        }

        public static BottleDispenser getInstance()	{
            return BD;
        }


        public void addMoney(TextView v, double coin) {

            money += coin;

        }



        public void buyBottle(TextView v, int choice, Context c, String name, Double price) {
            if (bottles<=0)	{
                v.setText("Pullot loppu");
            }
            if (money<=Array.get(choice).getPrice()) {
                Toast.makeText(c, "Add more money!", Toast.LENGTH_SHORT).show();

            }
            else	{
                try {
                    OutputStreamWriter ows = new OutputStreamWriter(c.openFileOutput("BottleReceipt.txt",Context.MODE_PRIVATE));
                    String s = "Your receipt:   Name: "+name+"  Price: "+price;

                    ows.write(s);
                    ows.close();

                } catch (IOException e) {
                    Log.e("IOException", "Error in name,");
                }
                bottles -= 1;
                money -= Array.get(choice).getPrice();

                Array.remove(choice);
            }
        }
    public void readFile(Context c,TextView v) {
        try {
            InputStream ins = c.openFileInput("BottleReceipt.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String s = "";
            s = br.readLine();
            v.setText(s);
            ins.close();
        } catch (IOException e) {
            Log.e("IOException", "Virhe syötteessä.");
        }
    }

        public void returnMoney(TextView v) {
            String money2 = String.format("%.2f", money);
            v.setText("Klink klink. Money came out! You got "+money2+"€ back");
            money = 0;
        }

        public void listBottles(TextView s, TextView m) {
            s.setText("");
            m.setText("Current money: "+(String.format("%.2f",money)));
            for(int i = 0; i<bottles; i++)	{
                s.append((i+1)+". Name: "+Array.get(i).getName()+"\n");
                s.append("	Size: " + Array.get(i).getSize()+"  Price: "+ Array.get(i).getPrice()+"\n");
            }
        }
    }




