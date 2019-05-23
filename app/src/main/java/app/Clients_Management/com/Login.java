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
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableRow;
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
 * Created by egypt2 on 10-Dec-18.
 */

public class Login extends Activity {

    EditText username, password;
    String ls_username, ls_password;
    Button login ;
    private boolean check ;
    TextView start, tab_start , tab_Privacy ,Privacy , add_client , tab_add_client;
    String st_phone;

    DatabaseReference databaseReference;
    List<DataUsers> list_datausers;
    private String databasename;
    private int height_start , height_Privacy ,height_add_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.button_login);

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
                } else if (ls_password.isEmpty()) {
                    Toast.makeText(Login.this, "من فضلك قم بإدخال كلمه المرور الخاص بك", Toast.LENGTH_SHORT).show();
                } else if (ls_username.equals("SecretLogin")) {
                    Intent intent = new Intent(Login.this, AddUser.class);
                    startActivity(intent);

                } else if (checkUsers(ls_username, ls_password)) {
                    //        Toast.makeText(Login.this,ls_username, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ClientsList.class);
                    intent.putExtra("username", ls_username);
                    startActivity(intent);
                }
            }
        });
        //-------------------

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

        View listViewClient = getLayoutInflater().inflate(R.layout.layout_phone, null);
        start = (TextView) listViewClient.findViewById(R.id.start);
        tab_start = (TextView) listViewClient.findViewById(R.id.tab_start);

        Privacy = (TextView) listViewClient.findViewById(R.id.Privacy);
        tab_Privacy = (TextView) listViewClient.findViewById(R.id.tab_Privacy);

        add_client = (TextView) listViewClient.findViewById(R.id.add_client);
        tab_add_client = (TextView) listViewClient.findViewById(R.id.tab_add_client);

        builder.setNegativeButton("O.K", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }

        });
        builder.setView(listViewClient);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // https://www.youtube.com/watch?v=5ETJWUuH1Ag
                /*Intent intent = new Intent(getApplicationContext(), ClientsList.class);
                intent.putExtra("username", "test");

                startActivity(intent);*/
        //---------------
        height_start = tab_start.getHeight();
        Toast.makeText(this, "" + height_start, Toast.LENGTH_SHORT).show();

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
            check = true;
        } else check = false;
        return check;
    }

    private void setData() {
        ls_username = username.getText().toString();
        ls_password = password.getText().toString();
    }

    @Override
    protected void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_datausers.clear();
                for (DataSnapshot dataclients : dataSnapshot.getChildren()) {
                    DataUsers userData = dataclients.getValue(DataUsers.class);
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

    private Boolean checkUsers(String user, String pass) {

        for (int i = 0; i < list_datausers.size(); i++) {
            DataUsers datausers = list_datausers.get(i);
            if (user.equals(datausers.getUser_Name())) {
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
        builder.setMessage("هل انت متاكد انك تريد الخروج").setCancelable(false).setPositiveButton("نعم", new DialogInterface.OnClickListener() {
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


    private void getHeight(String s, int height) {
        if (height != 0) {
            switch (s) {
                case "S":
                    height_start = height;
                    break;
                case "P":
                    height_Privacy = height;
                    break;
                case "C":
                    height_add_client = height;
                    break;

            }

        }
    }
    public void tabStart(View view) {

        getHeight("S", tab_start.getHeight());

        if (tab_start.getHeight() != 0) {
            tab_start.setHeight(0);
        } else {
            tab_start.setHeight(height_start);
            // width , height
        }
    }
    public void tabPrivacy(View view) {
        getHeight("P", tab_Privacy.getHeight());

        if (tab_Privacy.getHeight() != 0) {
            tab_Privacy.setHeight(0);
        } else {
            tab_Privacy.setHeight(height_Privacy);
            // width , height
        }
    }

    public void tabAdd_client(View view) {
        getHeight("C", tab_add_client.getHeight());

        if (tab_add_client.getHeight() != 0) {
            tab_add_client.setHeight(0);
        } else {
            tab_add_client.setHeight(height_add_client);
            // width , height
        }
    }
}
