package app.Clients_Management.com;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by aya on 11/1/2016.
 */
public class ListViewAdapterClientTracks extends BaseAdapter {

    // Declare Variables

    Activity context;
    String          ls_username ;
    List<DataPaid>     list_clientTracks ;
    Integer    list_position = 0 ;

    public ListViewAdapterClientTracks(Activity context,
                                       List<DataPaid>  list_clientTracks, String username ) {

        this.context = context;
        ls_username = username;
        this.list_clientTracks = list_clientTracks;
       // resultp = list_clients.get(0);

    }

    @Override
    public int getCount() {
        return list_clientTracks.size();
    }

    @Override
    public Object getItem(int position) {
        return list_clientTracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup viewGroup) {
        // Declare Variables
        TextView    last_paid , last_buy , last_date ;
        ImageButton img_details ;
        //---- customer inside
        LayoutInflater  inflater = context.getLayoutInflater();
        View    listViewClient = inflater.inflate(R.layout.customer_detail_inside, null, true);
        // Locate the TextViews
        last_paid = (TextView) listViewClient.findViewById(R.id.last_paid);
        last_buy = (TextView) listViewClient.findViewById(R.id.last_buy);
        last_date = (TextView) listViewClient.findViewById(R.id.last_date);
        img_details = (ImageButton) listViewClient.findViewById(R.id.img_details);
        //------- position
        final DataPaid dataPaid = list_clientTracks.get(position);
        //----- set Text
        last_paid.setText(dataPaid.getUser_cash());
        last_buy.setText(dataPaid.getUser_buy());
        last_date.setText(dataPaid.getUser_date());

        // Locate the ImageView in listview_item.xml
//        imglink = (ImageView) itemView.findViewById(R.id.image);

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
       // imageLoader.DisplayImage("http://52.41.120.12:8080/Restaurant/uploadedFiles/"+resultp.get(MenuParent.IMG), imglink);

        //
        //convertview.setOnClickListener(new CustomOnClickListener(callback, position));
        //
        img_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             /*  Intent intent = new Intent( context ,ClientsPaid.class);
                //intent.putExtra("username",resultp.get(key));

                intent.putExtra("ID",dataPaid.getUser_id());
                intent.putExtra("username",ls_username);
                intent.putExtra("clientname",dataPaid.getClient_name());
                intent.putExtra("phone",dataPaid.getUser_phone());
                intent.putExtra("card",dataPaid.getUser_card());
                context.startActivity(intent);

               */
            }
        });

        return listViewClient;

    }
}


