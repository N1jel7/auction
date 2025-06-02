package by.n1jel.auction.controller;

import atlantafx.base.theme.Styles;
import by.n1jel.auction.service.AuctionLotClientService;
import by.n1jel.auction.utils.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

@Component
@RequiredArgsConstructor
@FxmlView("connection-window.fxml")
public class ConnectionWindowController {

    private final AuctionLotClientService clientService;
    private final FxWeaver fxWeaver;

    @Setter
    private Stage stage;

    @FXML
    private Button connectButton;

    @FXML
    private TextField addressField;


    public void connect() {
        boolean addressFilled = !addressField.getText().isEmpty();
        boolean connected = clientService.isAddressAlive(addressField.getText());

        if (addressFilled && connected) {
            fxWeaver.loadController(AuthentificationController.class).show();
            stage.close();
            AlertUtil.getAlert(INFORMATION,
                            "Success",
                            "You successfully connected to the server. Try to login or register",
                            "Connected to the server")
                    .show();

        } else {
            AlertUtil.getAlert(ERROR, "Error", "Server is unavailable")
                    .showAndWait();
        }

    }

    @FXML
    public void initialize() {

        addressField.getStyleClass().addAll(Styles.ROUNDED);

        connectButton.getStyleClass().addAll(
                Styles.LARGE, Styles.ROUNDED, Styles.BUTTON_OUTLINED, Styles.SUCCESS
        );

        connectButton.setOnAction(e -> {
            connect();
        });
    }

    public void close() {
        stage.close();
    }
}
