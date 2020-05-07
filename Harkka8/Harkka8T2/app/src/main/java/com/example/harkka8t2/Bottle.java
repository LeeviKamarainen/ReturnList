package com.example.harkka8t2;

import androidx.annotation.NonNull;

public class Bottle {

        private String name;

        private String manufacturer;

        private double total_energy;

        private double price;

        private double size;




        public Bottle(){
            name = "Pepsi Max";
            manufacturer = "Pepsi";
            total_energy = 0.3;
            price = 1.8;
            size = 0.5;
        }


        public Bottle(String n, double p, double s){
            name = n;
            price = p;
            size = s;
        }



        public String getName(){
            return name;
        }

        public String getManufacturer(){
            return manufacturer;
        }

        public double getEnergy(){
            return total_energy;
        }

        public double getPrice(){
            return price;
        }
        public double getSize(){
            return size;
        }

    @NonNull
    @Override
    public String toString() {
            String info = name+", Size: "+size+" Price: "+price;
        return info;
    }
}
