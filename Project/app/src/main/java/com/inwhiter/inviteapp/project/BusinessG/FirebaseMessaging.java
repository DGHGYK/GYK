package com.inwhiter.inviteapp.project.BusinessG;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessaging extends FirebaseMessagingService {
    public FirebaseMessaging() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
       //howNotificaiton(remoteMessage.getData().get("message"));
    }


}
