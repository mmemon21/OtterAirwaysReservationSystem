
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CancelReservation extends AppCompatActivity {

    DBHandler db;
    HashMap<String, String> record;
    private ArrayList<HashMap<String, String>> list;
    String userName;
    String ReservationNumber, FlightNumber, Departure, Arrival,DepartureTime,NoofTickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation);
        db = new DBHandler(this, "University", null, 6);

    }
    public void ok_Click(View view){

        if( isaccountexist()){
            list = new ArrayList< HashMap<String, String>  >();
            record = new HashMap<String, String>();

            ArrayList wherepart=new ArrayList();

            wherepart.add("userName like '%"+ userName +"%'");
            wherepart.add("TransactionType like '%"+ "Reserve Seat" +"%'");
            String whereString=" where ";
            for(Object s:wherepart){
                whereString=whereString+s+" and ";
            }
            whereString= whereString+" userName NOT NULL";
            String query="select * from ReservationHistory "+whereString;
            final SQLiteDatabase sdb = db.getReadableDatabase();
            Cursor cursor = sdb.rawQuery(query, null);
            int counts=cursor.getCount();
            if(counts==0){
                AlertDialog.Builder builder=new AlertDialog.Builder(CancelReservation.this);
                builder.setMessage("You do not have any reserved Flights" ).setPositiveButton("ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(CancelReservation.this,MainActivity.class);
                        CancelReservation.this.startActivity(intent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }else {


                record = new HashMap<String, String>();
                record.put("ReservationNumber", "ReservationNumber");
                record.put("FlightNumber", "FlightNumber");
                record.put("Departure", "Departure");
                record.put("Arrival", "Arrival");
                record.put("DepartureTime", "DepartureTime");
                //record.put("noOfTickets", "noOfTickets");
                list.add(record);


                if (cursor.moveToFirst()) {
                    do {
                        record = new HashMap<String, String>();
                        record.put("ReservationNumber", cursor.getString(5).toString());
                        record.put("FlightNumber", cursor.getString(1).toString());
                        record.put("Departure", cursor.getString(9).toString());
                        record.put("Arrival", cursor.getString(2).toString());
                        record.put("DepartureTime", cursor.getString(3).toString());
                        //  record.put("noOfTickets", cursor.getString(4).toString());
//Toast.makeText(CancelReservation.this,cursor.getString(8).toString(),Toast.LENGTH_SHORT).show();
                        list.add(record);

                    } while (cursor.moveToNext());
                }

                ListViewAdapter2 adapter = new ListViewAdapter2(this, list);
                ListView listView = (ListView) findViewById(R.id.listView2);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ReservationNumber = ((TextView) view.findViewById(R.id.textViewReservationNumber)).getText().toString();
                        FlightNumber = ((TextView) view.findViewById(R.id.textViewFlightNumber)).getText().toString();
                        Departure = ((TextView) view.findViewById(R.id.textViewDeparture)).getText().toString();
                        Arrival = ((TextView) view.findViewById(R.id.textViewArrival)).getText().toString();
                        DepartureTime = ((TextView) view.findViewById(R.id.textViewDepartureTime)).getText().toString();

                        AlertDialog.Builder builder = new AlertDialog.Builder(CancelReservation.this);
                        builder.setMessage("Are you sure you want to cancel this reservation ??").setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                               // values.put("TransactionType","cancelation");

                                try {
                                    //Toast.makeText(CancelReservation.this,"working",Toast.LENGTH_SHORT).show();
                                   /* SQLiteDatabase sdb1=db.getWritableDatabase();
                                    ContentValues values=new ContentValues();
                                    values.put("totalAmount","0");
                                    values.put("arrival","someewhere");
                                    sdb1.update("ReservationHistory",values,"userName="+userName+" and reservationNumber="+ReservationNumber+" and FlightNumber=" + FlightNumber,null);

                                    */
                                   String query="update ReservationHistory set TransactionType='cancellation' where reservationNumber="+ReservationNumber;
                                   sdb.execSQL(query);
                                   AlertDialog.Builder builder = new AlertDialog.Builder(CancelReservation.this);
                                    builder.setMessage("Flight Reservation Canceled Successfully").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(CancelReservation.this, MainActivity.class);
                                            CancelReservation.this.startActivity(intent);
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                } catch (SQLException ex) {


                                    AlertDialog.Builder builder=new AlertDialog.Builder(CancelReservation.this);
                                    builder.setMessage(ex.getMessage() ).setPositiveButton("ok", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            }
                        }).setNegativeButton("disregred", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder builder=new AlertDialog.Builder(CancelReservation.this);
                                builder.setMessage("the cancellation has failed" ).setPositiveButton("ok", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(CancelReservation.this,MainActivity.class);
                                        CancelReservation.this.startActivity(intent);

                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
            }

        }
        else{
            Toast.makeText(this,"Your account does not exist OR Login Credentials are Invalid",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isaccountexist(){

        String query = "select * from logins";

        SQLiteDatabase sdb = db.getReadableDatabase();

        Cursor cursor = sdb.rawQuery(query, null);
        String userNameTextField=((EditText)findViewById(R.id.userName)).getText().toString();
        String passwordTextField=((EditText)findViewById(R.id.password)).getText().toString();
        //Toast.makeText(this,userNameTextField+passwordTextField,Toast.LENGTH_SHORT).show();

        String password="";
        boolean isaccountexist=false;
        if(cursor.moveToFirst())
        {
            do
            {
                userName = cursor.getString(0);
                password=cursor.getString(2);
                //Toast.makeText(this,userName,Toast.LENGTH_SHORT).show();
                if(userName.equalsIgnoreCase(userNameTextField)  &&  password.equalsIgnoreCase(passwordTextField)) {
                    isaccountexist =true;
                    break;
                }
            }while (cursor.moveToNext());
        }

        return  isaccountexist;
    }
}
