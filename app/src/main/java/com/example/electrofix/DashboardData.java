package com.example.electrofix;

import com.google.gson.annotations.SerializedName;

public class DashboardData {

    @SerializedName("pending")
    private int pending;

    @SerializedName("in_progress")
    private int in_progress;

    @SerializedName("completed")
    private int completed;

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getIn_progress() {
        return in_progress;
    }

    public void setIn_progress(int in_progress) {
        this.in_progress = in_progress;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
