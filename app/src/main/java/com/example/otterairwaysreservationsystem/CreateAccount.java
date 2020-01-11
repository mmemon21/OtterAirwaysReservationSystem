
package   com.example.otterairwaysreservationsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
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

public class CreateAccount extends AppCompatActivity {
    DBHandler db;
    int incorrect_attempts=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        db = new DBHandler(this, "University", null, 6);
    }
    public void createAccount_Click(View view){


        String userName=((EditText)findViewById(R.id.userName)).getText().toString();
        String password=((EditText)findViewById(R.id.password)).getText().toString();

        int charCountUserName=0,numCountUserName=0;
        int charCountPassword=0,numCountPassword=0;


        for (int i=0;i<userName.length();i++){
            char ch = userName.charAt(i);

            if ((ch>='a' && ch<='z') || (ch>='A' && ch<='Z')) {

                charCountUserName++;
            }

            else if (ch >= 48 && ch <= 57)
            {
                numCountUserName++;
            }

        }


        for (int i=0;i<password.length();i++){
            char ch = password.charAt(i);

            if ((ch>='a' && ch<='z') || (ch>='A' && ch<='Z')) {

                charCountPassword++;
            }

            else if (ch >= 48 && ch <= 57)
            {
                numCountPassword++;
            }

        }




        if(userName.equalsIgnoreCase("admin2")){

            AlertDialog.Builder builder=new AlertDialog.Builder(CreateAccount.this);
            builder.setMessage("you cannot use this USER NAME, plz enter different USER NAME").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((EditText)findViewById(R.id.userName)).setText("");
                    ((EditText)findViewById(R.id.password)).setText("");
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
       else if(userName.length()<4 || password.length()<4 || charCountUserName<3 || numCountUserName <1 || charCountPassword <3 || numCountPassword<1)
       {
            AlertDialog.Builder builder=new AlertDialog.Builder(CreateAccount.this);
            builder.setMessage("USER NAME  and  PASSWORD should contain atleast on digit and three Alphabets" ).setPositiveButton("ok", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            incorrect_attempts++;
            if(incorrect_attempts>=2){
               incorrect_attempts=0;
               Intent intent=new Intent(this,MainActivity.class);
               this.startActivity(intent);
           }
       }
       else{

           boolean isaccountalreadyexist=isaccountexist();

           if(isaccountalreadyexist){
               AlertDialog.Builder builder=new AlertDialog.Builder(CreateAccount.this);
               builder.setMessage("this USERNAME is already taken, try a different one" ).setPositiveButton("ok", new DialogInterface.OnClickListener()
               {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       ((EditText)findViewById(R.id.userName)).setText("");
                       ((EditText)findViewById(R.id.password)).setText("");
                   }
               });
               AlertDialog alertDialog = builder.create();
               alertDialog.show();
           }
           else {

               SQLiteDatabase sdb = db.getWritableDatabase();
               ContentValues value = new ContentValues();
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
               String currentDateandTime = sdf.format(new Date());
               value.put("userName", userName);
               value.put("createdat",currentDateandTime);
               value.put("password", password);
               //value.put("createdAt","datetime('now','localtime')");

               try
               {
                   sdb.insertOrThrow("logins",null, value);
                   AlertDialog.Builder builder=new AlertDialog.Builder(CreateAccount.this);
                   builder.setMessage("You have successfully created new account" ).setPositiveButton("ok", new DialogInterface.OnClickListener()
                   {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           Intent intent=new Intent(CreateAccount.this,MainActivity.class);
                           CreateAccount.this.startActivity(intent);

                       }
                   });
                   AlertDialog alertDialog = builder.create();
                   alertDialog.show();
               }
               catch (SQLException ex)
               {

                   AlertDialog.Builder builder=new AlertDialog.Builder(CreateAccount.this);
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



        }
    }
    public boolean isaccountexist(){

        String query = "select * from logins";

        SQLiteDatabase sdb = db.getReadableDatabase();

        Cursor cursor = sdb.rawQuery(query, null);

        String userName="";

        boolean isaccountexist=false;
        if(cursor.moveToFirst())
        {
            do
            {
                userName = cursor.getString(0);

                 if(userName.equalsIgnoreCase(((EditText)findViewById(R.id.userName)).getText().toString())){
                     isaccountexist =true;
                     break;
                 }
            }while (cursor.moveToNext());
        }

        return  isaccountexist;
    }
}
