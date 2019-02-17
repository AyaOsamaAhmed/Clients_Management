package app.Clients_Management.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.CharacterPickerDialog;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by egypt2 on 10-Dec-18.
 */

public class ClientsList extends Activity {

    EditText        search_text ;
    ListView        list_view;
    Button          search_button,add_button;
    String          ls_search_text ,ls_username ,databasename ,ls_phone ;

    HashMap<String,Integer> hashMap_position ;
    DatabaseReference   databaseReference;
    List<DataClients> list_dataclients  ;
    ArrayList  <String>   arrayList_data   ;
    int                     client_test ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientslist);

        search_text=(EditText)findViewById(R.id.search_text);
        search_button=(Button)findViewById(R.id.search_button);
        list_view = (ListView)findViewById(R.id.clientslist);
        list_dataclients = new ArrayList<>();
        add_button = (Button)findViewById(R.id.add_button);
        arrayList_data = new  ArrayList<String>();
        hashMap_position = new HashMap<String, Integer>();
        //-------Database name
        ls_username=getIntent().getStringExtra("username");
        ls_phone = getIntent().getStringExtra("phone");
        databasename = "Clients_" + ls_username;
       // Toast.makeText(this, databasename, Toast.LENGTH_SHORT).show();
        //-------Database Firebase intent.putExtra("username", ls_username);
        databaseReference = FirebaseDatabase.getInstance().getReference(databasename);
        databaseReference.keepSynced(true);
        //------------
        list_view.setTextFilterEnabled(true);
        list_view.setAdapter(new ListViewAdapterClients(ClientsList.this,list_dataclients ,ls_username ));

        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // When user changed the Text

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
              /*  ArrayList<String> search = new ArrayList<String>();
                int lenghtSearch = search_text.getText().length();
                search.clear();
                for (int i = 0 ; i<arrayList_data.size() ; i++){
                    if (lenghtSearch <= arrayList_data.get(i).length()){
                        if (search_text.getText().toString().equalsIgnoreCase((String) arrayList_data.get(i).subSequence(0,lenghtSearch) ) ){
                             search.add(arrayList_data.get(i));
                        }
                    }
                }
                list_view.setAdapter(new CustomArrayAdapter(ClientsList.this,search ,ls_username , list_dataclients));
         */   //    ClientsList.this.filter_dataclients.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                ArrayList<String> search = new ArrayList<String>();
                int lenghtSearch = search_text.getText().length();
                search.clear();
                for (int i = 0 ; i<arrayList_data.size() ; i++){
                    if (lenghtSearch <= arrayList_data.get(i).length()){
                        if (search_text.getText().toString().equalsIgnoreCase((String) arrayList_data.get(i).subSequence(0,lenghtSearch) ) ){
                            search.add(arrayList_data.get(i));
                        }
                    }
                }
                list_view.setAdapter(new CustomArrayAdapter(ClientsList.this,search ,ls_username , list_dataclients , hashMap_position));

            }
        });
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ls_search_text = search_text.getText().toString();

                if (ls_search_text.isEmpty()){
                    Toast.makeText(ClientsList.this, " يجب كتابه اسم العميل قبل الضغط على بحث", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(ClientsList.this, "جارى البحث ......", Toast.LENGTH_SHORT).show();

                }
            }
        });


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (client_test>= 1 ) {
                    alartTest();

                }else {
                    Intent intent = new Intent(getApplicationContext(), AddClients.class);
                    intent.putExtra("username", ls_username);
                    intent.putExtra("phone", ls_phone);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0 ;
                    list_dataclients.clear();
                for(DataSnapshot dataclients : dataSnapshot.getChildren()){
                    DataClients client  = dataclients.getValue(DataClients.class);
                //    Toast.makeText(ClientsList.this, client.getUser_Name(), Toast.LENGTH_SHORT).show();
                    list_dataclients.add(client);
                    arrayList_data.add(client.getClient_name());
                    hashMap_position.put(client.getClient_name(),i);
                    i++;
                    if ( ls_username.equals("test")) {
                        if (ls_phone.equals(client.getUser_phone())) {
                            client_test++;
                        }
                    }
                }


             //   ListViewAdapterClients adapter = new ListViewAdapterClients(ClientsList.this, list_dataclients ,ls_username );
               // list_view.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        super.onStart();
    }
    private void alartTest( ) {
        final Button      button_call , button_whats , button_cancle;
        final AlertDialog.Builder  builder = new AlertDialog.Builder(ClientsList.this);

        final View listViewClient = getLayoutInflater().inflate(R.layout.layout_call,null);
        //-----------
        button_call = (Button) listViewClient.findViewById(R.id.number);
        button_whats = (Button) listViewClient.findViewById(R.id.whats);
        button_cancle= (Button) listViewClient.findViewById(R.id.cancle);

        //-------------------
        builder.setView(listViewClient);
        final AlertDialog alertDialog = builder.create();

        //------------------
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01001059357"));
                startActivity(intent);
            }
        });

        button_whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+2001001059357"));
                startActivity(intent);

            }
        });

        button_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
       //--------------
        alertDialog.show();
        /*
        AlertDialog.Builder al = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Material_Wallpaper));
        al.setMessage(message);
        al.setCancelable(false).setPositiveButton("الاتصال على رقمى", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01001059357"));
                startActivity(intent);

            }
        }).setCancelable(false)
                .setPositiveButton("محادثه عن طريق الواتس أب", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+2001001059357"));
                        startActivity(intent);


                    }
                });
        al.setNegativeButton("إالغاء", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });
        al.show();
*/
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Black));

        // builder.
        builder.setMessage("هل انت متاكد من انك تريد الخروج من الحساب ؟").setCancelable(false).setPositiveButton("الخروج من الحساب", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ClientsList.this ,Login.class);
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
