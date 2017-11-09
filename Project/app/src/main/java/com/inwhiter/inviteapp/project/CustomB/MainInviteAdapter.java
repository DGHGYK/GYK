package com.inwhiter.inviteapp.project.CustomB;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inwhiter.inviteapp.project.ModelG.Invitation;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;


public class MainInviteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Invitation> modelList;

    private OnItemClickListener mItemClickListener;


    public MainInviteAdapter(Context context, ArrayList<Invitation> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<Invitation> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_invite_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final Invitation model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.tv_mainiviterow_title.setText(model.getImageText());
            genericViewHolder.tv_mainiviterow_details.setText(model.getDetails());

            Glide
                    .with(mContext)
                    .load(model.getImageURI())
                    .asBitmap()
                    .fitCenter()
                    .placeholder(R.drawable.giris)
                    .into(genericViewHolder.iv_maininviterow_invitationimage);
        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private Invitation getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, Invitation model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_maininviterow_invitationimage;
        private TextView tv_mainiviterow_title;
        private TextView tv_mainiviterow_details;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.iv_maininviterow_invitationimage = (ImageView) itemView.findViewById(R.id.iv_maininviterow_invitationimage);
            this.tv_mainiviterow_title = (TextView) itemView.findViewById(R.id.tv_mainiviterow_title);
            this.tv_mainiviterow_details = (TextView) itemView.findViewById(R.id.tv_mainiviterow_details);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });

        }
    }

}

