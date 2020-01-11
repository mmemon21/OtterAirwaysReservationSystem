package com.example.otterairwaysreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    public void createAccount_Click(View view){
        Intent intent =new Intent(MainActivity.this,CreateAccount.class);
        this.startActivity(intent);
    }


    public void reserveSeat_Click(View view){
        Intent intent =new Intent(MainActivity.this,ReserveSeat.class);
        this.startActivity(intent);
    }
    public void cancelReservation_Click(View view){
        Intent intent =new Intent(MainActivity.this, CancelReservation.class);
        this.startActivity(intent);
    }
    public void manageSystem_Click(View view){
        Intent intent =new Intent(MainActivity.this,SystemLogin.class);
        this.startActivity(intent);
    }
}
