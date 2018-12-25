package app.Clients_Management.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by egypt2 on 18-Dec-18.
 */

public class ClientsDetails extends Activity {


    TextView    new_paid;
    String      ls_id ,ls_username ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);

        new_paid = (TextView)findViewById(R.id.new_paid);
        //--------
        ls_id = getIntent().getStringExtra("ID");
        ls_username=getIntent().getStringExtra("username");
        //--------
      //  Toast.makeText(this, ls_id, Toast.LENGTH_SHORT).show();

        new_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ClientsPaid.class);
                intent.putExtra("username", ls_username);
                startActivity(intent);
            }
        });
    }


}
