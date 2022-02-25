package com.price.ui.model.request;

public class Discounts {

    private boolean slopeClosed;
    private boolean snowParkClosed;
    private boolean freshSnow;
    private boolean valleyRun;

    public boolean isSlopeClosed() {
        return slopeClosed;
    }

    public void setSlopeClosed(boolean slopeClosed) {
        this.slopeClosed = slopeClosed;
    }

    public boolean isSnowParkClosed() {
        return snowParkClosed;
    }

    public void setSnowParkClosed(boolean snowParkClosed) {
        this.snowParkClosed = snowParkClosed;
    }

    public boolean isFreshSnow() {
        return freshSnow;
    }

    public void setFreshSnow(boolean freshSnow) {
        this.freshSnow = freshSnow;
    }

    public boolean isValleyRun() {
        return valleyRun;
    }

    public void setValleyRun(boolean valleyRun) {
        this.valleyRun = valleyRun;
    }
}
