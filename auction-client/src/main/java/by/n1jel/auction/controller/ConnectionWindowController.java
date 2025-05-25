package by.n1jel.auction.controller;

import by.n1jel.auction.service.AuctionLotClientService;
import by.n1jel.auction.utils.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    private Stage stage;

    @FXML
    private Button connectButton;

    @FXML
    private TextField addressField;


    public void connect() {
        boolean addressFilled = !addressField.getText().isEmpty();
        boolean connected = clientService.isAddressAlive(addressField.getText());

        if (addressFilled && connected) {
            fxWeaver.loadController(AuctionController.class).show();
            AlertUtil.getAlert(INFORMATION, "Success", "Connected to the server")
                    .showAndWait();

        } else {
            AlertUtil.getAlert(ERROR, "Error", "Server is unavailable")
                    .showAndWait();
        }

    }

    @FXML
    public void initialize() {
        connectButton.setOnAction(e -> {
            connect();
        });
    }

    public void show() {
        stage.show();
    }
}
