package app.Clients_Management.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by egypt2 on 18-Dec-18.
 */

public class ClientsDetails extends Activity {


    TextView  new_paid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);

        new_paid = (TextView)findViewById(R.id.new_paid);


        new_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ClientsPaid.class);

                startActivity(intent);
            }
        });
    }


}
