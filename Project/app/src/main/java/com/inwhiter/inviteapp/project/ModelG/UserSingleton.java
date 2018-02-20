package com.inwhiter.inviteapp.project.ModelG;

/**
 * Created by gamze on 04/09/2017.
 */

public class UserSingleton {
    private static UserSingleton user = new UserSingleton();
    private String token;
    private int count; //hesabındaki davetiye sayısını verir.


    public static UserSingleton getInstance() {

        if(user == null)
        {

            user = new UserSingleton();
        }

        return user;

    }

    private UserSingleton() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
