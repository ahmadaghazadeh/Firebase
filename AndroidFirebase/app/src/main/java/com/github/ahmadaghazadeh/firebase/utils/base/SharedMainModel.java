package com.github.ahmadaghazadeh.firebase.utils.base;

public class SharedMainModel {

    private boolean isIndeterminate;
    private int progress;

    public boolean isIndeterminate() {
        return isIndeterminate;
    }

    public void setIndeterminate(boolean indeterminate) {
        isIndeterminate = indeterminate;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public SharedMainModel(boolean isIndeterminate, int progress) {

        this.isIndeterminate = isIndeterminate;
        this.progress = progress;
    }
}
