package   com.example.otterairwaysreservationsystem;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class DBHandler extends SQLiteOpenHelper
{
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String loginsTable =
                "CREATE TABLE logins(" +
                        "userName TEXT," +
                        "createdAt TEXT," +
                        "password TEXT)";
        String flightsTable =
                "CREATE TABLE flights(" +
                        "flightCapacity INT," +
                        "flightNumber TEXT," +
                        "arrival TEXT," +
                        "departureTime TEXT," +
                        "price TEXT," +
                        "departure TEXT)";
        String logsTable =
                "CREATE TABLE logs(" +

                        "time TEXT)";
        String ReservationHistoryTable =
                "CREATE TABLE ReservationHistory(" +
                        "userName TEXT," +
                        "flightNumber TEXT," +
                        "arrival TEXT," +
                        "departureTime TEXT," +
                        "noOfTickets TEXT," +
                        "reservationNumber TEXT," +
                        "totalAmount TEXT," +
                        "reservedAt TEXT," +
                        "TransactionType TEXT," +
                        "departure TEXT)";


        String reservationTable =
                "CREATE TABLE reservation(" +
                        "reservationNumber TEXT," +
                        "userName TEXT," +
                        "flightCapacity TEXT," +
                        "Price TEXT," +
                        "flightNumber TEXT," +
                        "arrival TEXT," +
                        "departureTime TEXT," +
                        "reservedAt TEXT," +
                        "tickets TEXT," +
                        "totalAmount TEXT," +
                        "departure TEXT)";


        try {
            db.execSQL(loginsTable);
            db.execSQL(flightsTable);
            db.execSQL(logsTable);
            db.execSQL(ReservationHistoryTable);
            db.execSQL(reservationTable);
        } catch (SQLException ex) {
            Log.i("EXCEPTION: ", ex.getMessage());
        }
    }



        public void deleteFlights(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="delete from flights";
            db.execSQL(query);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS logins");
        db.execSQL("DROP TABLE IF EXISTS flights");
        db.execSQL("DROP TABLE IF EXISTS logs");
        db.execSQL("DROP TABLE IF EXISTS ReservationHistory");
        db.execSQL("DROP TABLE IF EXISTS reservation");
        onCreate(db);
    }

}