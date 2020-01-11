package   com.example.otterairwaysreservationsystem;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UserConfirmation extends AppCompatActivity {
    String s1,s2,s3,s4,s5,s6;
    DBHandler db;

    String userName="",password="";
    int reservationNumber,incorrect_attempts=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_confirmation);
        db = new DBHandler(this, "University", null, 6);
        Intent intent=this.getIntent();
        s1=intent.getStringExtra("Flight No");
        s2=intent.getStringExtra("Departure");
        s3=intent.getStringExtra("Arrival");
        s4=intent.getStringExtra("Departure Time");
        s5=intent.getStringExtra("Flight Capacity");
        s6=intent.getStringExtra("Price");


    }
    public void btn_Click(final View view){

if(isAccountExistorNot()){

    AlertDialog.Builder builder=new AlertDialog.Builder(UserConfirmation.this);
    builder.setMessage("Flight No:"+ s1+"\n"+
            "Departure :"+s2+"\n"+
            "Arrival :"+s3+"\n"+
            "Departure Time :"+s4+"\n"+
            "Flight Capacity :"+s5+"\n"+
            "Price :"+s6+"\n"+
            "total price :"+String.valueOf(Integer.valueOf(s6)*Integer.valueOf(s5))+
            "\n"+"\n"+
            "CONFIRM FLIGHT ???").setPositiveButton("ok", new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {


            SQLiteDatabase sdb = db.getWritableDatabase();
            ContentValues value = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            String currentDateandTime = sdf.format(new Date());
            value.put("userName", userName);
            value.put("flightNumber",s1);
            value.put("departure",s2);
            value.put("arrival", s3);
            value.put("departureTime", s4);
            int capacity=Integer.valueOf(s5);
            Random random=new Random();
             reservationNumber =random.nextInt(10000);
            value.put("reservationNumber",String.valueOf(reservationNumber));
            int price=Integer.valueOf(s6);
            int totalAmouunt=price*capacity;
            value.put("totalAmount",String.valueOf(totalAmouunt));
            value.put("reservedAt",currentDateandTime);
            value.put("TransactionType","Reserve Seat");

            //value.put("createdAt","datetime('now','localtime')");

            try
            {
                sdb.insertOrThrow("ReservationHistory",null, value);
                AlertDialog.Builder builder=new AlertDialog.Builder(UserConfirmation.this);
                builder.setMessage("You have successfully reserved Flight" ).setPositiveButton("ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //reservationNumber++;
                        Intent intent=new Intent(UserConfirmation.this,MainActivity.class);
                        UserConfirmation.this.startActivity(intent);

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            catch (SQLException ex)
            {

                AlertDialog.Builder builder=new AlertDialog.Builder(UserConfirmation.this);
                builder.setMessage(ex.getMessage().toString()).setPositiveButton("ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            AlertDialog.Builder builder=new AlertDialog.Builder(UserConfirmation.this);
            builder.setMessage("Are you sure you want to cancel Flight Reservation ???").setPositiveButton("sure", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(UserConfirmation.this,"ERRORR",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(UserConfirmation.this,MainActivity.class);
                    UserConfirmation.this.startActivity(intent);
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    btn_Click(view);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();




        }
    });
    AlertDialog alertDialog = builder.create();
    alertDialog.show();

}
else{

    incorrect_attempts++;
    if(incorrect_attempts>=2){
        incorrect_attempts=0;
       // Toast.makeText(UserConfirmation.this,"Such Account Does not exist OR login Credentials are Invalid",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,MainActivity.class);
        this.startActivity(intent);
    }
    else{
        Toast.makeText(UserConfirmation.this,"Such Account Does not exist OR login Credentials are Invalid",Toast.LENGTH_SHORT).show();
    }
}



    }
    public boolean isAccountExistorNot(){
        String query = "select * from logins";
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(query, null);
        boolean isaccountexist=false;
        if(cursor.moveToFirst())
        {
            do
            {
                userName = cursor.getString(0);
                password=cursor.getString(2);
                if(userName.equalsIgnoreCase(((EditText)findViewById(R.id.userName)).getText().toString())  && password.equalsIgnoreCase(((EditText)findViewById(R.id.password)).getText().toString())){
                    isaccountexist =true;
                    break;
                }
            }while (cursor.moveToNext());
        }

        return  isaccountexist;
    }
}


