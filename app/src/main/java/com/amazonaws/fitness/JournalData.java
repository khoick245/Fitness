package com.amazonaws.fitness;

/**
 * Created by khoinguyen on 11/9/17.
 */

public class JournalData {
    String email;
    String dateworkout;
    int noofwork;
    String bodypart;
    String exercise;

    JournalData(String email, String dateworkout, int noofwork, String bodypart, String exercise)
    {
        this.email = email;
        this.dateworkout = dateworkout;
        this.noofwork = noofwork;
        this.bodypart = bodypart;
        this.exercise = exercise;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateworkout() {
        return dateworkout;
    }

    public void setDateworkout(String dateworkout) {
        this.dateworkout = dateworkout;
    }

    public int getNoofwork() {
        return noofwork;
    }

    public void setNoofwork(int noofwork) {
        this.noofwork = noofwork;
    }

    public String getBodypart() {
        return bodypart;
    }

    public void setBodypart(String bodypart) {
        this.bodypart = bodypart;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}
