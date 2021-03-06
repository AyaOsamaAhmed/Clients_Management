package app.Clients_Management.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by Aya_Osama on 26-Aug-18.
 */

public class Splash extends Activity {


    private ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

          progressBar = (ProgressBar) findViewById(R.id.progress);

         Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    for (int i =20 ; i<= 100 ;) {
                        progressBar.setProgress(i);
                        sleep(1000);
                        i = i + 10 ;
                    }

                    Intent intent = new Intent(getApplicationContext(),Login.class);
                   // intent.putExtra("username", "عبد الرحمن");
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}
        };
        thread.start();

        /*
         <ProgressBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/progress"
        style="?android:attr/progressBarStyleHorizontal"
        android:progressDrawable="@drawable/progress_bar"
         android:layout_marginBottom="40dp"
        android:layout_gravity="center"
        android:progress="20"

        android:layout_weight="0.10"
        android:minWidth="25dp"
        />

        */
    }


}

