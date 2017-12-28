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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhiter.inviteapp.project.ActivityG.GuestEditActivity;
import com.inwhiter.inviteapp.project.ModelG.Guest;
import com.inwhiter.inviteapp.project.ModelG.GuestListSingleton;
import com.inwhiter.inviteapp.project.ModelG.GuestStatus;
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

public class GuestListAdapter extends BaseExpandableListAdapter {
    //public List<Guest> GuestListSingleton.getInst().getGuestList();
    public Activity activity;
    String inviteId;


    public GuestListAdapter(Activity activity, String inviteId) {
        this.activity = activity;
        this.inviteId=inviteId;
    }

    @Override
    public int getGroupCount() {
        return GuestListSingleton.getInst().getGuestList().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Guest getGroup(int groupPosition) {
        return GuestListSingleton.getInst().getGuestList().get(groupPosition);
    }

    @Override
    public GuestStatus getChild(int groupPosition, int childPosition) {
        return GuestListSingleton.getInst().getGuestList().get(groupPosition).getStatus();
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
        Guest listTitle = (Guest) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.activity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.guest_item, null);
        }
        TextView guestName = (TextView) convertView
                .findViewById(R.id.tv_item_guestName);
        guestName.setTypeface(null, Typeface.BOLD);
        guestName.setText(listTitle.getName());


        CheckBox check = (CheckBox) convertView.findViewById(R.id.cb_item_check);
        check.setChecked(true);

        ImageView guestColor = (ImageView) convertView.findViewById(R.id.iv_item_guestStatus);
        if(listTitle.getStatus()!=null && listTitle.getStatus().getAnswer()!=null) {
            if (listTitle.getStatus().getAnswer().equals("Geliyor")) {
                guestColor.setBackgroundColor(Color.rgb(31, 120, 180));
            } else if (listTitle.getStatus().getAnswer().equals("Gelmiyor")) {
                guestColor.setBackgroundColor(Color.rgb(186, 22, 63));
            } else if (listTitle.getStatus().getAnswer().equals("Belki")){
                guestColor.setBackgroundColor(Color.rgb(153, 148, 194));
            }
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Guest guest =(Guest) getGroup(groupPosition);
        final GuestStatus guest_status = (GuestStatus) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.guest_child_item, null);
        }

        TextView sendDate = (TextView) convertView
                .findViewById(R.id.tv_guestChild_sendDate);
        if(guest_status.getSendDate()!=null) {
            sendDate.setText(guest_status.getSendDate().toString());
        }else{
            sendDate.setText("--");
        }

        TextView answerDate = (TextView) convertView
                .findViewById(R.id.tv_guestChild_answerDate);
        if(guest_status.getAnswerDate()!=null) {
            answerDate.setText(guest_status.getAnswerDate().toString());
        }else{
            answerDate.setText("--");
        }

        TextView phoneNumber = (TextView) convertView
                .findViewById(R.id.tv_guestChild_phoneNumber);
        phoneNumber.setText(guest.getPhoneNumber());

        Button delete = (Button) convertView.findViewById(R.id.bt_guestChild_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guest in= GuestListSingleton.getInst().getGuestList().get(groupPosition);
                GuestListSingleton.getInst().deleteGuest(in, inviteId);
                notifyDataSetChanged();
            }
        });

        Button resend =(Button) convertView.findViewById(R.id.bt_guestChild_resend);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guest in= GuestListSingleton.getInst().getGuestList().get(groupPosition);
                List<Guest> list = new ArrayList<>();
                list.add(in);
                new SendSMS().execute(list);
            }
        });

        Button edit = (Button) convertView.findViewById(R.id.bt_guestChild_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, GuestEditActivity.class);
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
