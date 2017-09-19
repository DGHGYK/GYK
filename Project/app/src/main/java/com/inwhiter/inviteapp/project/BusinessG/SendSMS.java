package com.inwhiter.inviteapp.project.BusinessG;

import android.os.AsyncTask;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inwhiter.inviteapp.project.ModelG.Invitee;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gncal on 8/4/2017.
 */

public class SendSMS extends AsyncTask<List<Invitee>, Void, String> {

    //sms apiye bağlanmak için 3. parti okhttpclient kullanıyoruz

    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    protected String doInBackground(List<Invitee>... params) {
        String response = null;
        for (Invitee invitee : params[0]) {
            try {
                //gönderme değerleri değiştiriliyor
                invitee.getStatus().setSended(true);
                invitee.getStatus().setSendDate(Calendar.getInstance().getTime());
                invitee.setLastProcess(1);

                //veritabanındaki davetli verilerini değiştirme
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference inviteeRef = database.getReference("invitee");
                inviteeRef.child(invitee.getInviteeId()).setValue(invitee);

                //SMS gönderimi
                String inviteUrl="www.inwhiter.com/invite.html?id="+invitee.getInviteeId();
                String getUrl="https://api.iletimerkezi.com/v1/send-sms/get/?username=5333665570&password=azrazra&text="+invitee.getName()+", bir davetiniz var. Görmek için tıklayın: "+inviteUrl+"&receipents="+formatPhoneNumber(invitee.getPhoneNumber())+"&sender=inwhiter";
                response += run(getUrl);


            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(response);
        }

        return response;

    }


    private String formatPhoneNumber(String phoneNumber){
        return phoneNumber.replaceAll("[^\\d]", "");

    }

}