package com.android.assessment.footballapp.models;

public class ScrollState {

    private boolean state;

    public ScrollState(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
