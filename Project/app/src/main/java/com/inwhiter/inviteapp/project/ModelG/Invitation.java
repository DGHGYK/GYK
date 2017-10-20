package com.inwhiter.inviteapp.project.ModelG;

import android.net.Uri;

/**
 * Created by gamze on 01/10/2017.
 */

public class Invitation {

    private String imageText;
    private Uri imageURI;
    private String details;


    public Invitation() {
    }

    public Invitation(String imageText, Uri imageURI) {
        this.imageText = imageText;
        this.imageURI = imageURI;
    }

    public String getImageText() {
        return imageText;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }

    public Uri getImageURI() {
        return imageURI;
    }

    public void setImageURI(Uri imageURI) {
        this.imageURI = imageURI;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
