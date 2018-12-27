package app.Clients_Management.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by egypt2 on 18-Dec-18.
 */

public class ClientsDetails extends Activity {


    TextView    new_paid;
    String      ls_id ,ls_username ;
    private String ls_phone;
    private String ls_card;

    DatabaseReference databaseReference;
    List<DataClients> list_dataclients ;
    private String    databasename;
    private String    ls_id_client;
    ListView          list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);

        new_paid = (TextView)findViewById(R.id.new_paid);
        list_view = (ListView)findViewById(R.id.trachslist);
        //--------
        ls_id = getIntent().getStringExtra("ID");
        ls_username=getIntent().getStringExtra("username");
        ls_phone=getIntent().getStringExtra("phone");
        ls_card = getIntent().getStringExtra("card");
        //-------Database name
        ls_username=getIntent().getStringExtra("username");
        ls_id_client=getIntent().getStringExtra("ID");
        databasename = "Tracks_" + ls_username;
        Toast.makeText(this, databasename, Toast.LENGTH_SHORT).show();
        //-------Database Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference(databasename).child(ls_id_client);

        new_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ClientsPaid.class);
                intent.putExtra("username", ls_username);
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
                    DataClients client  = dataclients.getValue(DataClients.class);
                    //    Toast.makeText(ClientsList.this, client.getUser_Name(), Toast.LENGTH_SHORT).show();
                    list_dataclients.add(client);
                }
                ListViewAdapterClients adapter = new ListViewAdapterClients(ClientsDetails.this, list_dataclients ,ls_username );
                list_view.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        super.onStart();
    }

}
