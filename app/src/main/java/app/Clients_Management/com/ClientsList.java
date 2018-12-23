package app.Clients_Management.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by egypt2 on 10-Dec-18.
 */

public class ClientsList extends Activity {

    EditText        search_text ;
    Button          search_button,add_button, button_details;
    String          ls_search_text ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientslist);

        search_text=(EditText)findViewById(R.id.search_text);
        search_button=(Button)findViewById(R.id.search_button);
        button_details = (Button) findViewById(R.id.button_details);

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

        add_button = (Button)findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddClients.class);
                startActivity(intent);
            }
        });
        button_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ClientsDetails.class);

                startActivity(intent);
            }
        });

    }

}
