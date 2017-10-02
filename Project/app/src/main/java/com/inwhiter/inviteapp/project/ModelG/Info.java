package com.inwhiter.inviteapp.project.ModelG;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gamze on 05/09/2017.
 */

public class Info implements Parcelable{
    private String title, text, family1, family2, address, hashtag, date, time, music;


    protected Info(Parcel in) {
        title = in.readString();
        text = in.readString();
        family1 = in.readString();
        family2 = in.readString();
        address = in.readString();
        hashtag = in.readString();
        date = in.readString();
        time = in.readString();
        music= in.readString();
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    public Info() {
    }

    public Info(String title, String text, String family1, String family2, String address, String hashtag, String date, String time) {
        this.title = title;
        this.text = text;
        this.family1 = family1;
        this.family2 = family2;
        this.address = address;
        this.hashtag = hashtag;
        this.date = date;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFamily1() {
        return family1;
    }

    public void setFamily1(String family1) {
        this.family1 = family1;
    }

    public String getFamily2() {
        return family2;
    }

    public void setFamily2(String family2) {
        this.family2 = family2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(family1);
        dest.writeString(family2);
        dest.writeString(address);
        dest.writeString(hashtag);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(music);
    }
}
