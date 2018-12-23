package app.Clients_Management.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by egypt2 on 18-Dec-18.
 */

public class ClientsPaid extends Activity {

    Button      button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paid_details);

        button_save =(Button)findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }



}
