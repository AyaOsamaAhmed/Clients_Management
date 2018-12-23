package app.Clients_Management.com;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by egypt2 on 11-Dec-18.
 */

public class AddClients extends Activity {

    EditText        name ,phone , card ,cash ,buy  ,date ;
    TextView        total ;
    Button          button_save ;
    String          ls_id ,ls_name , ls_phone , ls_card , ls_cash ="0.0" , ls_buy="0.0" , ls_total="0.0" , ls_date ;
    Double          ld_buy=0.0 , ld_cash=0.0 , ld_total=0.0 ;
    String          databasename;
    DataClients     dataClients;

    DatabaseReference   databaseclients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclients);
        //-------Database Firebase
        databasename = "Users";                                                      // name clients
        databaseclients = FirebaseDatabase.getInstance().getReference(databasename);
        //------------------- Declear
        name = (EditText)findViewById(R.id.name);
        phone = (EditText)findViewById(R.id.phone);
        card = (EditText)findViewById(R.id.card);
        cash = (EditText)findViewById(R.id.cash);
        buy = (EditText)findViewById(R.id.buy);
        total = (TextView)findViewById(R.id.total);
        date = (EditText)findViewById(R.id.date);
        button_save =(Button)findViewById(R.id.button_save);

        //--------- Set Data
        setData();
        //------ Calc Total
      buy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                ls_buy = editable.toString();
                CalcTotal();
            }
        });
        cash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                ls_cash = editable.toString();
                CalcTotal();
            }
        });


        //-------- Set Data
        CalcTotal();

        //--------- Button Save
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                String  id = databaseclients.push().getKey();
                ls_id = id ;
                dataClients  = new DataClients(ls_id ,ls_name,ls_phone,ls_card,ls_cash,ls_buy,ls_date);

                databaseclients.child(id).setValue(dataClients);
                Toast.makeText(AddClients.this, "Saved Data Sucsses", Toast.LENGTH_SHORT).show();
            }
        });
        //---------

    }

    private void setData (){
        ls_name = name.getText().toString();
        ls_phone = phone.getText().toString();
        ls_card  = card.getText().toString();
        ls_cash = cash.getText().toString();
        ls_buy  = buy.getText().toString();
        ls_date =date.getText().toString();
        //------
      /*  Date calendar = Calendar.getInstance().getTime();
        Integer day = calendar.getDate();
        Integer month = calendar.getMonth();
        Integer year = calendar.getYear();

        //united destriputer - -115-
        date.setText(year+"/"+month+"/"+day);
        date.setText(year.toString());*/

    }

    private void  CalcTotal (){
        //-------
        if (ls_buy.isEmpty()) ls_buy="0.0";
        if(ls_cash.isEmpty()) ls_cash="0.0";
        //--------
        ld_buy = Double.parseDouble(ls_buy) + 0.0 ;
        ld_cash = Double.parseDouble(ls_cash)+0.0 ;
        ld_total = ld_buy - ld_cash ;
        ls_total = ld_total.toString() ;
        //-----
        this.total.setText(ls_total);
    }


}
