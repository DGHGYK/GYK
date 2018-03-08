package com.inwhiter.inviteapp.project.BusinessG;

import android.os.AsyncTask;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inwhiter.inviteapp.project.ModelG.Guest;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gncal on 8/4/2017.
 */

public class SendSMSService extends AsyncTask<List<Guest>, Void, String> {

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
    protected String doInBackground(List<Guest>... params) {
        String response = null;
        for (Guest guest : params[0]) {
            try {
                //gönderme değerleri değiştiriliyor
                guest.getStatus().setSended(true);
                guest.getStatus().setSendDate(Calendar.getInstance().getTime());
                guest.setLastProcess(1);

                //SMS gönderimi
                String inviteUrl="inwhiter.com/invite.html?id="+guest.getGuestId();
                String getUrl="https://api.iletimerkezi.com/v1/send-sms/get/?username=5333665570&password=azrazra&text="+guest.getName()+", bir davetiniz var. Görmek için tıklayın: "+inviteUrl+"&receipents="+formatPhoneNumber(guest.getPhoneNumber())+"&sender=inwhiter";
                response += run(getUrl);

                //veritabanındaki davetli verilerini değiştirme
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference guestRef = database.getReference("guest");
                guestRef.child(guest.getGuestId()).setValue(guest);


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