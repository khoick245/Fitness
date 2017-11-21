package com.amazonaws.fitness;

/**
 * Created by khoinguyen on 11/20/17.
 */

public class RunningData {
    String user;
    String dateRunning;
    String timeRunning;

    public RunningData(String user, String dateRunning, String timeRunning){
        this.user = user;
        this.dateRunning = dateRunning;
        this.timeRunning = timeRunning;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDateRunning() {
        return dateRunning;
    }

    public void setDateRunning(String dateRunning) {
        this.dateRunning = dateRunning;
    }

    public String getTimeRunning() {
        return timeRunning;
    }

    public void setTimeRunning(String timeRunning) {
        this.timeRunning = timeRunning;
    }
}
