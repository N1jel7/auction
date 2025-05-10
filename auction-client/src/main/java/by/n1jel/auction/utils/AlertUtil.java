package by.n1jel.auction.utils;

import javafx.scene.control.Alert;

public abstract class AlertUtil {

    public static Alert getAlert(Alert.AlertType alertType, String title, String context) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(context);
        return alert;
    }

}
