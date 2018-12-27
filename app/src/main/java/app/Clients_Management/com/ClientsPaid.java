package app.Clients_Management.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by egypt2 on 18-Dec-18.
 */

public class ClientsPaid extends Activity {

    Button      button_save;
    String      ls_id ,ls_username;

    DatabaseReference   databaseclients;
    DatePickerDialog datePickerDialog ;
    DataPaid        datapaid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paid_details);

        button_save =(Button)findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                if( validation_data()){
                    String  id = databaseclients.push().getKey();
                    ls_id = id ;
                //    datapaid  = new DataClients(ls_id ,ls_name,ls_phone,ls_card,ls_cash,ls_buy,ls_date);

                    databaseclients.child(id).setValue(datapaid);

                    Toast.makeText(ClientsPaid.this, "Saved Data Sucsses", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ClientsPaid.this,ClientsList.class);
                    intent.putExtra("username", ls_username);
                    startActivity(intent);
                }
            }


        });

    }

    private boolean validation_data() {

        return false ;
    }
    private void setData() {


    }

    @Override
public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));

        // builder.
        builder.setMessage("هل انت متاكد من إلغاء انشاء عميل جديد؟").setCancelable(false).setPositiveButton("نعم", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int id) {
        Intent intent = new Intent(ClientsPaid.this ,ClientsList.class);
        startActivity(intent);

        }
        }).setNegativeButton("لا", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();
        }
        });
        builder.create().show();
        }

}
