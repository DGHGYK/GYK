package com.inwhiter.inviteapp.project.BusinessG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhiter.inviteapp.project.ActivityG.InviteeEditActivity;
import com.inwhiter.inviteapp.project.ModelG.ContactListSingleton;
import com.inwhiter.inviteapp.project.ModelG.Invitee;
import com.inwhiter.inviteapp.project.ModelG.InviteeListSingleton;
import com.inwhiter.inviteapp.project.ModelG.InviteeStatus;
import com.inwhiter.inviteapp.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gncal on 8/2/2017.
 * Seçilen kişilerin artık davetli listesine atılması için adapter.
 * Kişi bilgilerine ek olarak gönderilme ve cevaplanma tarihleri, davetlilerin
 * cevapları gibi değişkenleri de tutar.
 * Kişi adları listede görünürken adın üstüne basınca açılan alanda detay
 * bilgileri görüntülenir.
 */

public class InviteeListAdapter extends BaseExpandableListAdapter {
    //public List<Invitee> InviteeListSingleton.getInst().getInviteeList();
    public Context context;
    public Activity activity;

    public InviteeListAdapter(Context context, Activity activity) {
        // this.InviteeListSingleton.getInst().getInviteeList() = InviteeListSingleton.getInst().getInviteeList();
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getGroupCount() {
        return InviteeListSingleton.getInst().getInviteeList().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Invitee getGroup(int groupPosition) {
        return InviteeListSingleton.getInst().getInviteeList().get(groupPosition);
    }

    @Override
    public InviteeStatus getChild(int groupPosition, int childPosition) {
        return InviteeListSingleton.getInst().getInviteeList().get(groupPosition).getStatus();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Invitee listTitle = (Invitee) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.invitee_item, null);
        }
        TextView inviteeName = (TextView) convertView
                .findViewById(R.id.tv_item_inviteeName);
        inviteeName.setTypeface(null, Typeface.BOLD);
        inviteeName.setText(listTitle.getName());


        CheckBox check = (CheckBox) convertView.findViewById(R.id.cb_item_check);
        check.setChecked(false);

        ImageView inviteeColor = (ImageView) convertView.findViewById(R.id.iv_item_inviteeStatus);
        if(listTitle.getStatus()!=null && listTitle.getStatus().getAnswer()!=null) {
            if (listTitle.getStatus().getAnswer().equals("Geliyor")) {
                inviteeColor.setBackgroundColor(Color.rgb(31, 120, 180));
            } else if (listTitle.getStatus().getAnswer().equals("Gelmiyor")) {
                inviteeColor.setBackgroundColor(Color.rgb(186, 22, 63));
            } else {
                inviteeColor.setBackgroundColor(Color.rgb(153, 148, 194));
            }
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Invitee invitee =(Invitee) getGroup(groupPosition);
        final InviteeStatus invitee_status = (InviteeStatus) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.invitee_child_item, null);
        }

        TextView sendDate = (TextView) convertView
                .findViewById(R.id.tv_inviteeChild_sendDate);
        if(invitee_status.getSendDate()!=null) {
            sendDate.setText(invitee_status.getSendDate().toString());
        }else{
            sendDate.setText("--");
        }

        TextView answerDate = (TextView) convertView
                .findViewById(R.id.tv_inviteeChild_answerDate);
        if(invitee_status.getAnswerDate()!=null) {
            answerDate.setText(invitee_status.getAnswerDate().toString());
        }else{
            answerDate.setText("--");
        }

        TextView phoneNumber = (TextView) convertView
                .findViewById(R.id.tv_inviteeChild_phoneNumber);
        phoneNumber.setText(invitee.getPhoneNumber());

        ImageView delete = (ImageView) convertView.findViewById(R.id.iv_inviteeChild_delete);
        delete.setImageResource(R.mipmap.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Invitee in= InviteeListSingleton.getInst().getInviteeList().get(groupPosition);
                ContactListSingleton.getInst().getSelectedContactsList().removeContactByPhoneNumber(in.getPhoneNumber());
                InviteeListSingleton.getInst().removeInvitee(in);
                notifyDataSetChanged();
            }
        });

        ImageView resend =(ImageView) convertView.findViewById(R.id.iv_inviteeChild_resend);
        resend.setImageResource(R.mipmap.send);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Invitee in= InviteeListSingleton.getInst().getInviteeList().get(groupPosition);
                List<Invitee> list = new ArrayList<>();
                list.add(in);
                new SendSMS().execute(list);
            }
        });

        ImageView edit = (ImageView) convertView.findViewById(R.id.iv_inviteeChild_edit);
        edit.setImageResource(R.mipmap.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InviteeEditActivity.class);
                intent.putExtra("position", groupPosition);
                activity.startActivity(intent);
            }
        });




        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
