package com.example.shashank.umentorapplication.Domain;

public class Lesson {
    private String title;
    private String videoUrl;
    private int duration;

    // Default constructor required for Firestore serialization
    public Lesson() {
    }

    public Lesson(String title, String videoUrl, int duration) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}