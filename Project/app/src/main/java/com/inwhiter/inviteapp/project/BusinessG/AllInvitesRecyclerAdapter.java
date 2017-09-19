package com.inwhiter.inviteapp.project.BusinessG;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhiter.inviteapp.project.R;

import java.util.List;

/**
 * Created by gamze on 07/09/2017.
 */

    public class AllInvitesRecyclerAdapter extends RecyclerView.Adapter<AllInvitesRecyclerAdapter.MyViewHolder> {

        private List<String> horizontalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView inviteId;
            public ImageView inviteImage;

            public MyViewHolder(View view) {
                super(view);
                inviteId= (TextView) view.findViewById(R.id.tv_recycler_item_id);
                inviteImage = (ImageView) view.findViewById(R.id.iv_recycler_item_image);

            }
        }


        public AllInvitesRecyclerAdapter(List<String> horizontalList) {
            this.horizontalList = horizontalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.inviteId.setText(horizontalList.get(position));
            holder.inviteImage.setImageResource(R.drawable.giris);

            holder.inviteId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("bisey", "bisey");
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }

