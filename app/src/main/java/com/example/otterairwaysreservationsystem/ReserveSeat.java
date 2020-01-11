package   com.example.otterairwaysreservationsystem;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReserveSeat extends AppCompatActivity {
    DBHandler db;
    Spinner departure,arrival,tickets;
    HashMap<String, String> record;
    private ArrayList<HashMap<String, String>> list;
    String s1,s2,s3,s4,s5,s6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_seat);
       // Toast.makeText(this,"reserve seat",Toast.LENGTH_SHORT).show();
        db=new DBHandler(this, "University", null, 6);

        departure= findViewById(R.id.selectDeparture);
         arrival= findViewById(R.id.selectArrival);
       // tickets= findViewById(R.id.selectTickets);


/*
        String query = "select * from flights";
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(query, null);

        String departuredb="",arrivaldb="";
        List<String> departurelist=new ArrayList<>();
        List<String> arrivallist=new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do
            {
                departuredb=cursor.getString(5);
                arrivaldb=cursor.getString(2);
                departurelist.add(departuredb);
                arrivallist.add(arrivaldb);

            }while (cursor.moveToNext());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, departurelist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departure.setAdapter(adapter);


         adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrivallist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrival.setAdapter(adapter);




 */
/*
        ArrayAdapter<CharSequence> adapter1 =  ArrayAdapter.createFromResource(this,
                R.array.no_of_tickets, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tickets.setAdapter(adapter1);



 */

        ArrayAdapter<CharSequence> adapter2 =  ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departure.setAdapter(adapter2);



        ArrayAdapter<CharSequence> adapter3 =  ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrival.setAdapter(adapter3);




    }
    public  void searchFlight_Click(View view) {

        if(((EditText) findViewById(R.id.seats)).getText().toString().equals("")){
            Toast.makeText(ReserveSeat.this,"Please Enter No Of Tickets",Toast.LENGTH_SHORT).show();
        }
        else {


            int seats = Integer.valueOf(((EditText) findViewById(R.id.seats)).getText().toString());

            if (seats > 7) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReserveSeat.this);
                builder.setMessage(" reservation can not be made due to the system restriction").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {


                list = new ArrayList<HashMap<String, String>>();
                String departureLocation, arrivalLocation;
                String noOfSeats;
                departureLocation = departure.getSelectedItem().toString();
                arrivalLocation = arrival.getSelectedItem().toString();
                noOfSeats = ((EditText) findViewById(R.id.seats)).getText().toString();


                ArrayList wherepart = new ArrayList();

                wherepart.add("flightCapacity>=" + noOfSeats);
                wherepart.add("arrival like '%" + arrivalLocation + "%'");
                wherepart.add("departure like '%" + departureLocation + "%'");
                //wherepart.add("TransactionType  like '%"+ "Reserve Seat" +"%'");

                String whereString = " where ";
                for (Object s : wherepart) {
                    whereString = whereString + s + " and ";
                }

                whereString = whereString + " flightCapacity NOT NULL";

                String query = "select * from flights " + whereString;

                SQLiteDatabase sdb = db.getReadableDatabase();
                Cursor cursor = sdb.rawQuery(query, null);
                int counts = cursor.getCount();

                if (counts == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReserveSeat.this);
                    builder.setMessage("There is no Flight Maching your Search").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {



                record = new HashMap<String, String>();
                record.put("FlightNo", "FlightNo");
                record.put("Departure", "Departure");
                record.put("Arrival", "Arrival");
                record.put("DepartureTime", "DepartureTime");
                record.put("FlightCapacity", "FlightCapacity");
                record.put("Price", "Price");
                list.add(record);


                if (cursor.moveToFirst()) {
                    do {
                        record = new HashMap<String, String>();
                        record.put("FlightNo", cursor.getString(1).toString());
                        record.put("Departure", cursor.getString(5).toString());
                        record.put("Arrival", cursor.getString(2).toString());
                        record.put("DepartureTime", cursor.getString(3).toString());
                        record.put("FlightCapacity", cursor.getString(0).toString());
                        record.put("Price", cursor.getString(4).toString());
                        list.add(record);

                    } while (cursor.moveToNext());
                }


                ListViewAdapter adapter = new ListViewAdapter(this, list);
                ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        s1 = ((TextView) view.findViewById(R.id.textViewFlightNo)).getText().toString();
                        s2 = ((TextView) view.findViewById(R.id.textViewDeparture)).getText().toString();
                        s3 = ((TextView) view.findViewById(R.id.textViewArrival)).getText().toString();
                        s4 = ((TextView) view.findViewById(R.id.textViewDepartureTime)).getText().toString();
                        s5 = ((TextView) view.findViewById(R.id.textViewFlightCapacity)).getText().toString();
                        s6 = ((TextView) view.findViewById(R.id.textViewPrice)).getText().toString();

                        Intent intent = new Intent(ReserveSeat.this, UserConfirmation.class);
                        intent.putExtra("Flight No", s1);
                        intent.putExtra("Departure", s2);
                        intent.putExtra("Arrival", s3);
                        intent.putExtra("Departure Time", s4);
                        intent.putExtra("Flight Capacity", s5);
                        intent.putExtra("Price", s6);
                        startActivity(intent);


                    }
                });
            }
            }
        }
    }
}
