package org.weibeld.example.tabs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.view.LayoutInflater;

import org.weibeld.example.R;

import java.util.ArrayList;

/**
 * Created by moham on 2017-11-20.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private ArrayList<Notification> arrayList;
    private Context context;

    public NotificationAdapter(ArrayList<Notification> arrayList, Context context){
        this.context = context;
        this.arrayList = new ArrayList<>(arrayList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.Title.setText(arrayList.get(position).getTitle());
        holder.Alert.setText(arrayList.get(position).getAlert());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView Title;
        private TextView Alert;
        private Button Edit;

        public MyViewHolder(View itemView){
            super(itemView);
            Title = (TextView)itemView.findViewById(R.id.notificationTitle);
            Alert = (TextView)itemView.findViewById(R.id.alert);
            Edit = (Button)itemView.findViewById(R.id.edit);

            Edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if (v.getId() == Title.getId()) {
                Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
