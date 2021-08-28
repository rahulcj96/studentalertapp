package com.studentalert.cses6;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class maincardAdapter extends RecyclerView.Adapter<maincardAdapter.ViewHolder> {

    private final Context context;
    private final List<maincontent> HomeData;

    public maincardAdapter(List<maincontent> HomeData, Context context){
        super();
        //Getting all the notification
        this.HomeData = HomeData;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        maincontent maincontent =  HomeData.get(position);
        holder.Title.setText(maincontent.getTitle());
        holder.Content.setText(maincontent.getContent());
        holder.Date.setText(maincontent.getDate());
        if(Global.Orientation.equals("Landscape")) {
            holder.Title.setTextSize(20);
            holder.Content.setTextSize(13);
            holder.Date.setTextSize(10);
        }
        holder.Content.setMaxLines(5);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO implement onClick for the card here..!
                holder.Content.setMaxLines(100);
            }
        });

    }


    @Override
    public int getItemCount() {
        return HomeData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView Title;
        public final TextView Date;
        public final TextView Content;
        public final CardView cardView= (CardView) itemView.findViewById(R.id.card_view);
        //TODO cardview is taken to use it for onClick Events
        public ViewHolder(View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.text_title);
            Content = (TextView) itemView.findViewById(R.id.text_content);
            Date = (TextView) itemView.findViewById(R.id.textView3);

        }

        @Override
        public void onClick(View view) {

        }




    }
}