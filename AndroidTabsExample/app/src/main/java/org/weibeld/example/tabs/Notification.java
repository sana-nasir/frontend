package org.weibeld.example.tabs;

/**
 * Created by moham on 2017-11-20.
 */

public class Notification {
    private String Title, Alert;

    public Notification(String Title, String Alert){
        this.setTitle(Title);
        this.setAlert(Alert);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAlert() {
        return Alert;
    }

    public void setAlert(String alert) {
        Alert = alert;
    }
}
