package com.inwhiter.inviteapp.project.BusinessG;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inwhiter.inviteapp.project.ModelG.Invitation;
import com.inwhiter.inviteapp.project.R;

import java.util.List;

/**
 * Created by gamze on 07/09/2017.
 */

    public class AllInvitesRecyclerAdapter extends RecyclerView.Adapter<AllInvitesRecyclerAdapter.MyViewHolder> {

    private List<Invitation> horizontalList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView inviteId;
        public ImageView inviteImage;
        public TextView details;

        public MyViewHolder(View view) {
            super(view);
            inviteId = (TextView) view.findViewById(R.id.tv_recycler_item_id);
            inviteImage = (ImageView) view.findViewById(R.id.iv_recycler_item_image);
            details = (TextView) view.findViewById(R.id.tv_recycler_item_details);

        }
    }


    public AllInvitesRecyclerAdapter(List<Invitation> horizontalList, Context context) {
        this.horizontalList=horizontalList;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.details.setText(horizontalList.get(position).getDetails());
        holder.inviteId.setText(horizontalList.get(position).getImageText());
        Glide
                .with(context)
                .load(horizontalList.get(position).getImageURI())
                .fitCenter()
                .placeholder(R.drawable.giris)
                .into(holder.inviteImage);

        holder.inviteId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Recyler", "bisey");
            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

}

