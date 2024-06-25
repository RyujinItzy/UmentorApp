package com.example.shashank.umentorapplication.Domain;

public class CourseDomain {
    private String title;
    private String instructor;
    private String duration;
    private String imageUrl; // New field for image URL
    private int lessonCount;
    private int totalDuration;

    public CourseDomain(){

    }

    public CourseDomain(String title, String instructor, String duration, String imageUrl) {
        this.title = title;
        this.instructor = instructor;
        this.duration = duration;
        this.imageUrl = imageUrl;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(int lessonCount) {
        this.lessonCount = lessonCount;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }
}
