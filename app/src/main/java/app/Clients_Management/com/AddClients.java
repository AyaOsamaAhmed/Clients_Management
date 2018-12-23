package app.Clients_Management.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by egypt2 on 11-Dec-18.
 */

public class AddClients extends Activity {

    EditText        name ,phone , card ,cash ,buy  ,date ;
    TextView        total ;
    Button          button_save ;
    String          ls_id ,ls_name , ls_phone , ls_card , ls_cash ="0.0" , ls_buy="0.0" , ls_total="0.0" , ls_date ;
    String          databasename;
    DataClients     dataClients;

    DatabaseReference   databaseclients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclients);
        //-------Database Firebase
        databasename = "Test";                                                      // name clients
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

        //--------- calc Total
        setData();
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
        setData();
        dataClients  = new DataClients(ls_id ,ls_name,ls_phone,ls_card,ls_cash,ls_buy,ls_date);
        //--------- Button Save

        //---------

    }

    private void setData (){
        ls_name = name.getText().toString();
        ls_phone = phone.getText().toString();
        ls_card  = card.getText().toString();
        ls_cash = cash.getText().toString();
        ls_buy  = buy.getText().toString();
        ls_date = date.getText().toString();

    }

    private void  CalcTotal (){
        Integer total ;
        //--------
        total = Integer.parseInt(ls_buy) - Integer.parseInt(ls_cash) ;
        ls_total = total.toString() ;
        //-----
        this.total.setText(ls_total);
    }


}
