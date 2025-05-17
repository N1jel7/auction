package by.n1jel.auction.utils;

import javafx.scene.control.Alert;

public abstract class AlertUtil {

    public static Alert getAlert(Alert.AlertType alertType, String title, String context) {
        return getAlert(alertType, title, context, "");
    }

    public static Alert getAlert(Alert.AlertType alertType, String title, String context, String description) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(context);
        alert.setHeaderText(description);
        return alert;
    }

}
