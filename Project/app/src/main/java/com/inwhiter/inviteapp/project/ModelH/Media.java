package com.inwhiter.inviteapp.project.ModelH;

/**
 * Created by hatice on 02.10.2017.
 */

public class Media {

    @Override
    public String toString() {
        return super.toString();
    }

    String music_name;
    Boolean is_play;



    public Media(String music_name, Boolean is_play) {
        super();
        this.is_play = is_play;
        this.music_name=music_name;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public Boolean getIs_play() {
        return is_play;
    }

    public void setIs_play(Boolean is_play) {
        this.is_play = is_play;
    }




}
