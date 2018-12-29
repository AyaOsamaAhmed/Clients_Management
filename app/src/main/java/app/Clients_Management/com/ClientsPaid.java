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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by egypt2 on 18-Dec-18.
 */

public class ClientsPaid extends Activity {

    Button      button_save ;
    TextView    date ;
    EditText    buy , paid, buy_details ;
    String      ls_id_client ,ls_username , databasename ,ls_clientname;
    String      ls_date ,ls_buy , ls_paid , ls_buy_details , ls_total ,ls_phone ,ls_card;
    DatabaseReference   databaseclientspaid;
    DatePickerDialog datePickerDialog ;
    DataPaid        datapaid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paid_details);

        date = (TextView)findViewById(R.id.date);
        buy  = (EditText)findViewById(R.id.buy);
        paid = (EditText)findViewById(R.id.paid);
        buy_details = (EditText)findViewById(R.id.buy_details);
        button_save =(Button)findViewById(R.id.button_save);
        //------ receive data
        ls_id_client=getIntent().getStringExtra("clientid");
        ls_username=getIntent().getStringExtra("username");
        ls_clientname = getIntent().getStringExtra("clientname");
        ls_phone = getIntent().getStringExtra("phone");
        ls_card = getIntent().getStringExtra("card");
        //--------Database Name
        databasename = "Tracks_" + ls_username;
        Toast.makeText(this, databasename, Toast.LENGTH_SHORT).show();
        //-------Database Firebase
        databaseclientspaid = FirebaseDatabase.getInstance().getReference(databasename).child(ls_id_client);

        //----- button clicking
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(ClientsPaid.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month+1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //  DialogFragment newFragment = new DataPickerFragment();
                // newFragment.show(getFragmentManager(),"datepicker");
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                if( validation_data()){
                    String  id = databaseclientspaid.push().getKey();
                    datapaid  = new DataPaid(id ,ls_clientname,ls_paid,ls_buy,ls_buy_details,ls_date);
                    databaseclientspaid.child(id).setValue(datapaid);

                    Toast.makeText(ClientsPaid.this, "Saved Data Sucsses", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ClientsPaid.this,ClientsDetails.class);
                    intent.putExtra("username", ls_username);
                    intent.putExtra("ID", ls_id_client);
                    intent.putExtra("clientname", ls_clientname);
                    intent.putExtra("ID", ls_id_client);
                    intent.putExtra("phone", ls_phone);
                    intent.putExtra("card", ls_card);
                    startActivity(intent);
                }
            }


        });

    }

    private Boolean validation_data() {
        if (ls_buy_details.isEmpty()) {Toast.makeText(this, "من فضلك... قم بإدخال تفاصيل المشتريات للعميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_paid.isEmpty()) {Toast.makeText(this, "من فضلك... قم بإدخال المبلغ المدفوع الخاص بالعميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_buy.isEmpty()) {Toast.makeText(this, "من فضلك... قم بإدخال مبلغ المشتريات الخاص بالعميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_date.isEmpty()) {Toast.makeText(this, "من فضلك... قم بإدخال تاريخ دفع او شراء للعميل", Toast.LENGTH_SHORT).show(); return false ;}

        return true ;
    }

    private void setData (){
        ls_date = date.getText().toString();
        ls_buy = buy.getText().toString();
        ls_buy_details  = buy_details.getText().toString();
        ls_paid = paid.getText().toString();

    }

    private void  CalcTotal (){
        Double  ld_buy ,ld_paid ,ld_total ;
        //-------
        if (ls_buy.isEmpty()) ls_buy="0.0";
        if(ls_paid.isEmpty()) ls_paid="0.0";
        //--------
        ld_buy = Double.parseDouble(ls_buy) + 0.0 ;
        ld_paid = Double.parseDouble(ls_paid)+0.0 ;
        ld_total = ld_buy - ld_paid ;
        ls_total = ld_total.toString() ;
        //-----
       // this.total.setText(ls_total);
    }


    @Override
public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));

        // builder.
        builder.setMessage("هل انت متاكد من إلغاء انشاء دفع جديد للعميل ؟").setCancelable(false).setPositiveButton("نعم", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int id) {
        Intent intent = new Intent(ClientsPaid.this ,ClientsDetails.class);
        intent.putExtra("username", ls_username);
        intent.putExtra("ID", ls_id_client);
        intent.putExtra("clientname", ls_clientname);
        intent.putExtra("ID", ls_id_client);
        intent.putExtra("phone", ls_phone);
        intent.putExtra("card", ls_card);
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
