package app.Clients_Management.com;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by user on 1/1/2019.
 */

public class Clients_Management extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
