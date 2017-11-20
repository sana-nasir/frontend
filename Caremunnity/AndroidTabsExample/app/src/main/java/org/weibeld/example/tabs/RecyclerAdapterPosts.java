package org.weibeld.example.tabs;

/**
 * Created by sananasir on 2017-11-20.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.weibeld.example.R;

import java.util.ArrayList;
public class RecyclerAdapterPosts extends RecyclerView.Adapter<RecyclerAdapterPosts.MyViewHolder> {
    private ArrayList<posts> arrayList;
    private Context context;

    public RecyclerAdapterPosts(ArrayList<posts> arrayList, Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>(arrayList);
    }

    @Override
    public RecyclerAdapterPosts.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_posts, parent, false);
        RecyclerAdapterPosts.MyViewHolder myViewHolder = new RecyclerAdapterPosts.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.content.setText(arrayList.get(position).getContent());
        //holder.photo.setImageBitmap(arrayList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private TextView content;
        private ImageView photo;
        private Button like;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.namePost);
            content = (TextView)itemView.findViewById(R.id.contentPost);
            like = (Button)itemView.findViewById(R.id.likePost);
            //photo = (ImageView)itemView.findViewById(R.id.photoPost);

            like.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == name.getId()) {
                Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
