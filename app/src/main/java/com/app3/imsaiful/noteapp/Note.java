package com.app3.imsaiful.noteapp;

/**
 * Created by imsaiful on 23/7/17.
 */

public class Note {


    private String Title,Description,Image;

    public Note() {
    }

    public Note(String title, String description, String image) {
        Title = title;
        Description = description;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
