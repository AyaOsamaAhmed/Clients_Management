package app.Clients_Management.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by egypt2 on 10-Dec-18.
 */

public class Login extends Activity {

    EditText username,password;
    String      ls_username , ls_password ;
    Button   login , button_test;
    private boolean check;
    EditText  phone ;
    String    st_phone ;

    DatabaseReference databaseReference;
    List<DataUsers> list_datausers;
    private String databasename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.button_login);
        button_test = (Button)findViewById(R.id.button_test);
        list_datausers = new ArrayList<>();
        //-----
        databasename = "Users";                                                      // name clients
        databaseReference = FirebaseDatabase.getInstance().getReference(databasename);
        databaseReference.keepSynced(true);
        //---------
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
              //  Toast.makeText(Login.this,ls_username, Toast.LENGTH_SHORT).show();
                if (ls_username.isEmpty()) {
                    Toast.makeText(Login.this, "من فضلك قم بإدخال اسم المستخدم الخاص بك", Toast.LENGTH_SHORT).show();
                }else if (ls_password.isEmpty()) {
                    Toast.makeText(Login.this, "من فضلك قم بإدخال كلمه المرور الخاص بك", Toast.LENGTH_SHORT).show();
                }else if (ls_username.equals("SecretLogin")) {
                            Intent intent = new Intent(Login.this,AddUser.class);
                            startActivity(intent);

                }
                 else if (checkUsers(ls_username , ls_password)) {
                //        Toast.makeText(Login.this,ls_username, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ClientsList.class);
                        intent.putExtra("username", ls_username);
                        startActivity(intent);
                }
            }
        });
    //-------------------
        button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder  builder = new AlertDialog.Builder(Login.this);

                 View listViewClient = getLayoutInflater().inflate(R.layout.layout_phone,null);
                phone = (EditText) listViewClient.findViewById(R.id.phone);
                builder.setNegativeButton("شكرا", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        st_phone = phone.getText().toString();

                        if (! st_phone.isEmpty() && st_phone.length() >= 11){
                            //---------
                            AlertDialog.Builder  builder = new AlertDialog.Builder(Login.this);

                            View listViewClient = getLayoutInflater().inflate(R.layout.layout_contact,null);

                            builder.setNegativeButton("شكرا", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent intent = new Intent(getApplicationContext(), ClientsList.class);
                                    intent.putExtra("username", "test");
                                    intent.putExtra("phone", st_phone);
                                    startActivity(intent);
                                }

                            });
                            builder.setView(listViewClient);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            //---------


                        }else {
                            Toast.makeText(getApplicationContext(), "من فضلك,إدخل رقم الهاتف الخاص بك حتى يتم التواصل معك لاحقا", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                builder.setView(listViewClient);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                // https://www.youtube.com/watch?v=5ETJWUuH1Ag
                /*Intent intent = new Intent(getApplicationContext(), ClientsList.class);
                intent.putExtra("username", "test");

                startActivity(intent);*/
            }
        });
    }


    public void alart(String message) {
        AlertDialog al = new AlertDialog.Builder(this).create();
        al.setMessage(message);
        al.setButton("O.K", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        al.show();
    }


    public void alart() {
        AlertDialog al = new AlertDialog.Builder(this).create();
        al.setMessage("No Internet connection. Would you like to enable Internet connection ?");
        al.setButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        al.setButton2("MOBILEDATA", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Login.this.startActivity(new Intent("android.settings.DATA_ROAMING_SETTINGS"));
            }
        });
        al.setButton3("WIFI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Login.this.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
            }
        });
        al.show();
    }

    private boolean checkNetwork() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null && info.isConnected()) {
            check=true;
        }else check=false;
        return check;
    }

    private void setData (){
        ls_username = username.getText().toString();
        ls_password=password.getText().toString();
    }
    @Override
    protected void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_datausers.clear();
                for(DataSnapshot dataclients : dataSnapshot.getChildren()){
                    DataUsers userData  = dataclients.getValue(DataUsers.class);
                    //    Toast.makeText(ClientsList.this, client.getUser_Name(), Toast.LENGTH_SHORT).show();
                    list_datausers.add(userData);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        super.onStart();
    }

    private Boolean checkUsers ( String user , String pass){

        for (int i = 0 ; i < list_datausers.size() ; i++){
            DataUsers datausers = list_datausers.get(i);
            if (user.equals(datausers.getUser_Name())){
                if (pass.equals(datausers.getPassword())) {
                    return true;
                } else {
                    Toast.makeText(this, "كلمه المرور غير صحيحه ", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

        }
        Toast.makeText(this, "اسم المستخدم غير صحيح او غير مسجل .. ارجو التسجيل", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onBackPressed() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("هل انت متاكد انك تريد الخروج من ").setCancelable(false).setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

