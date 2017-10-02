package com.inwhiter.inviteapp.project.ModelG;

/**
 * Created by gamze on 04/09/2017.
 */

public class UserSingleton {
    private static UserSingleton user = new UserSingleton();
    private String token;


    public static UserSingleton getInstance() {

        if(user == null)
        {

            user = new UserSingleton();
        }

        return user;

    }

    private UserSingleton() {
    }
}
