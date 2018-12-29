package app.Clients_Management.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egypt2 on 18-Dec-18.
 */

public class ClientsDetails extends Activity {


    TextView    new_paid  , last_date;
    String      ls_id ,ls_username ,ls_clientname ;
    private String ls_phone;
    private String ls_card;

    DatabaseReference databaseReference;
    List<DataPaid> list_dataclients ;
    private String    databasename;
    private String    ls_id_client ,ls_last_date;
    ListView          list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);

        last_date = (TextView)findViewById(R.id.last_date);
        new_paid = (TextView)findViewById(R.id.new_paid);
        list_view = (ListView)findViewById(R.id.trackslist);
        list_dataclients = new ArrayList<>();
        //--------
        ls_id_client=getIntent().getStringExtra("ID");
        ls_clientname=getIntent().getStringExtra("clientname");
        ls_username=getIntent().getStringExtra("username");
        ls_phone=getIntent().getStringExtra("phone");
        ls_card = getIntent().getStringExtra("card");
        ls_last_date = getIntent().getStringExtra("Date");
        //----- Set last_date
        last_date.setText(ls_last_date);
        //-------Database name
        databasename = "Tracks_" + ls_username;
        Toast.makeText(this, databasename, Toast.LENGTH_SHORT).show();
        //-------Database Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference(databasename).child(ls_id_client);

        new_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ClientsPaid.class);
                intent.putExtra("username", ls_username);
                intent.putExtra("clientname", ls_clientname);
                intent.putExtra("phone", ls_phone);
                intent.putExtra("card", ls_card);
                intent.putExtra("clientid", ls_id_client);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_dataclients.clear();
                for(DataSnapshot dataclients : dataSnapshot.getChildren()){
                    DataPaid client  = dataclients.getValue(DataPaid.class);
                    //    Toast.makeText(ClientsList.this, client.getUser_Name(), Toast.LENGTH_SHORT).show();
                    list_dataclients.add(client);
                }
                ListViewAdapterClientTracks adapter = new ListViewAdapterClientTracks(ClientsDetails.this, list_dataclients ,ls_username );
                list_view.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        super.onStart();
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ClientsDetails.this ,ClientsList.class);
        intent.putExtra("username", ls_username);
        intent.putExtra("ID", ls_id_client);
        intent.putExtra("clientname", ls_clientname);
        intent.putExtra("ID", ls_id_client);
        intent.putExtra("phone", ls_phone);
        intent.putExtra("card", ls_card);
        startActivity(intent);


    }



}
