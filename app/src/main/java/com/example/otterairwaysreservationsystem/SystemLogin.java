
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

public class SystemLogin extends AppCompatActivity {
    DBHandler l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_login);
        l = new DBHandler(this, "University", null, 6);

        String query = "select * from logs";
        SQLiteDatabase sdb = l.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(query, null);
        int counts = cursor.getCount();
        if (counts == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SystemLogin.this);
            builder.setMessage(" there is no log information at the moment").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {


            // Toast.makeText(SystemLogin.this,String.valueOf(counts),Toast.LENGTH_SHORT).show();
            String logs = "Your Previous Logins \n";

            boolean isaccountexist = false;


            if (cursor.moveToFirst()) {
                do {
                    logs = logs + cursor.getString(0) + "\n";
                } while (cursor.moveToNext());
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(SystemLogin.this);
            builder.setMessage(logs).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }
    public  void login_Click (View view){

        String userName=((EditText)findViewById(R.id.userNameTextField)).getText().toString();
        String passsword=((EditText)findViewById(R.id.passwordTextField)).getText().toString();

        if(userName.equalsIgnoreCase("admin2") || passsword.equalsIgnoreCase("admin2")){
            SQLiteDatabase sdb = l.getWritableDatabase();
            ContentValues value = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            String currentDateandTime = sdf.format(new Date());
            value.put("time",currentDateandTime);

            try
            {
                sdb.insertOrThrow("logs",null, value);
                Intent intent=new Intent(SystemLogin.this,ManageSystem.class);
                SystemLogin.this.startActivity(intent);

            }
            catch (SQLException ex)
            {
                Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG).show();

            }

        }else{
            Toast.makeText(this,"Invalid Login credentials",Toast.LENGTH_LONG).show();
        }
    }
}
