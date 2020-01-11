package   com.example.otterairwaysreservationsystem;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManageSystem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);
    }
    public void btn_Click(View view){
        Intent intent=new Intent(this,AddNewFlight.class);
        this.startActivity(intent);

    }
}
