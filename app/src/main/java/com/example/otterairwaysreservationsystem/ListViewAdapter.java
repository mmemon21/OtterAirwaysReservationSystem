package   com.example.otterairwaysreservationsystem;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter
{
    Activity activity;
    ArrayList<HashMap<String, String>> list;
    TextView no,departure, arrival, departureTime, flightCapacity,price;

    public ListViewAdapter(Activity activity, ArrayList<HashMap<String, String>> list)
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
            view = inflater.inflate(R.layout.column_row, null);

             no= ((TextView)view.findViewById(R.id.textViewFlightNo));
            departure = ((TextView)view.findViewById(R.id.textViewDeparture));
            arrival = ((TextView)view.findViewById(R.id.textViewArrival));
            departureTime = ((TextView)view.findViewById(R.id.textViewDepartureTime));
            flightCapacity= ((TextView)view.findViewById(R.id.textViewFlightCapacity));
            price = ((TextView)view.findViewById(R.id.textViewPrice));

        }
        HashMap<String, String> map = list.get(i);

        no.setText( map.get("FlightNo"));
        departure.setText(map.get("Departure"));
        arrival.setText(map.get("Arrival"));
        departureTime.setText(map.get("DepartureTime"));
        flightCapacity.setText(map.get("FlightCapacity"));
        price.setText(map.get("Price"));

        return view;
    }
}
