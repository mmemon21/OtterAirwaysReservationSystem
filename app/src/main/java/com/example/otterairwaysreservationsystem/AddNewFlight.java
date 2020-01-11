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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AddNewFlight extends AppCompatActivity {
    DBHandler db;
    String  flightCapacity,flightNumber,arrival,departureTime,price,departure;
    Spinner departureSpinner,arrivalSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_flight);
        db = new DBHandler(this, "University", null, 6);
        departureSpinner= findViewById(R.id.spinnerDeparture);
        arrivalSpinner= findViewById(R.id.spinnerArrival);
       // db.deleteFlights();
        ArrayAdapter<CharSequence> adapter2 =  ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrivalSpinner.setAdapter(adapter2);



        ArrayAdapter<CharSequence> adapter3 =  ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departureSpinner.setAdapter(adapter3);

    }
    public void addNewFlight_Click(View view){

         flightCapacity=((EditText)findViewById(R.id.flightCapacity)).getText().toString();
         flightNumber =((EditText)findViewById(R.id.flightNo)).getText().toString();
         arrival =arrivalSpinner.getSelectedItem().toString();
         departureTime=((EditText)findViewById(R.id.departureTime)).getText().toString();
         price =((EditText)findViewById(R.id.price)).getText().toString();
         departure =departureSpinner.getSelectedItem().toString();


         AlertDialog.Builder builder=new AlertDialog.Builder(AddNewFlight.this);
        builder.setMessage("please make sure Are the information you entered is correct" +
                "\n" +
                "Flight Number: "+flightNumber+
                "\n" +
                "Departure Address: "+departure+
                "\n" +
                "Arrival Location: "+arrival+
                "\n" +
                "Deparutre Time: "+departureTime+
                "\n" +
                "Flight  Capacity: "+flightCapacity+
                "\n" +
                "price: "+price).setPositiveButton("ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(isFightAlreadyExist() || flightCapacity.equals("") || flightNumber.equals("") || arrival.equals("") || departureTime.equals("") || price.equals("") || departure.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(AddNewFlight.this);
                    builder.setMessage("Flight Information is Invalid  OR \n This Flight Aready Exist, Try a different one" ).setPositiveButton("ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ((EditText)findViewById(R.id.flightCapacity)).setText("");
                           ((EditText)findViewById(R.id.flightNo)).setText("");
                          //((EditText)findViewById(R.id.arrival)).setText("");
                            ((EditText)findViewById(R.id.departureTime)).setText("");
                         ((EditText)findViewById(R.id.price)).setText("");
                          // ((EditText)findViewById(R.id.departure)).setText("");

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {


                    final SQLiteDatabase sdb=db.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("flightCapacity",Integer.valueOf(flightCapacity));
                    values.put("flightNumber",flightNumber);
                    values.put("arrival",arrival);
                    values.put("price",price);
                    values.put("departure",departure);
                    values.put("departureTime",departureTime);


                    try
                    {
                        sdb.insertOrThrow("flights",null, values);
                        AlertDialog.Builder builder=new AlertDialog.Builder(AddNewFlight.this);
                        builder.setMessage("New Flight Inserted Successfully" ).setPositiveButton("ok", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sdb.close();
                                Intent intent=new Intent(AddNewFlight.this,MainActivity.class);
                                AddNewFlight.this.startActivity(intent);

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    catch (SQLException ex)
                    {
                        AlertDialog.Builder builder=new AlertDialog.Builder(AddNewFlight.this);
                        builder.setMessage(ex.getMessage().toString()).setPositiveButton("ok", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }



                }




            }
        }).setNegativeButton("require some changes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddNewFlight.this,"ok made changes whatever you want",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }
    public boolean isFightAlreadyExist(){


        String query = "select * from flights";

        SQLiteDatabase sdb = db.getReadableDatabase();

        Cursor cursor = sdb.rawQuery(query, null);

        String flightNumber="";

        boolean isaccountexist=false;
        if(cursor.moveToFirst())
        {
            do
            {
                flightNumber = cursor.getString(1);
                if(flightNumber.equalsIgnoreCase(((EditText)findViewById(R.id.flightNo)).getText().toString())){
                    isaccountexist =true;
                    break;
                }
            }while (cursor.moveToNext());
        }

        return  isaccountexist;
    }
}
