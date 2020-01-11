package com.example.otterairwaysreservationsystem;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter2 extends BaseAdapter
{
    Activity activity;
    ArrayList<HashMap<String, String>> list;
    TextView ReservationNumber, FlightNumber, Departure, Arrival,DepartureTime,NoofTickets;

    public ListViewAdapter2(Activity activity, ArrayList<HashMap<String, String>> list)
    {
        super();

        this.activity = activity;
        this.list = list;
    }
    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int i)
    {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = activity.getLayoutInflater();

        if(view == null)
        {
            view = inflater.inflate(R.layout.column_row1, null);

            ReservationNumber= ((TextView)view.findViewById(R.id.textViewReservationNumber));
            FlightNumber = ((TextView)view.findViewById(R.id.textViewFlightNumber));
            Departure= ((TextView)view.findViewById(R.id.textViewDeparture));
            Arrival = ((TextView)view.findViewById(R.id.textViewArrival));
            DepartureTime = ((TextView)view.findViewById(R.id.textViewDepartureTime));
           // NoofTickets = ((TextView)view.findViewById(R.id.textViewNoofTickets));

        }
        HashMap<String, String> map = list.get(i);

        ReservationNumber.setText( map.get("ReservationNumber"));
        FlightNumber.setText(map.get("FlightNumber"));
        Departure.setText(map.get("Departure"));
        Arrival.setText(map.get("Arrival"));
        DepartureTime.setText(map.get("DepartureTime"));
       // NoofTickets.setText(map.get("noOfTickets"));

        return view;
    }
}