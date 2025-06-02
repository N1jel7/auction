package by.n1jel.auction.controller;

import by.n1jel.auction.dto.TraderResponseDto;
import by.n1jel.auction.dto.TraderUpdateRequest;
import by.n1jel.auction.exception.UiAlertException;
import by.n1jel.auction.service.TraderClientService;
import by.n1jel.auction.utils.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;


@RequiredArgsConstructor
@Component
@FxmlView("modal-edit-trader.fxml")
public class ModalTraderEditController {

    private final TraderClientService traderService;
    private final FxWeaver fxWeaver;

    private TraderResponseDto currentTrader;
    private Stage stage;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField patronymicField;

    @FXML
    private Button saveButton;

    @FXML
    private Button resetButton;

    @FXML
    private VBox modalEditView;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(modalEditView));

        resetButton.setOnAction((e) -> {
            reset();
        });

        saveButton.setOnAction(actionEvent -> {
            edit();
        });

    }

    private TraderUpdateRequest getDtoFromFields() {
        if (!surnameField.getText().trim().isEmpty() && !nameField.getText().trim().isEmpty() && !patronymicField.getText().trim().isEmpty()) {
            return new TraderUpdateRequest(surnameField.getText(), nameField.getText(), patronymicField.getText());
        } else {
            AlertUtil.getAlert(ERROR, "Some fields are missing", "Fill the empty fields first")
                    .showAndWait();
            return null;
        }
    }

    public void edit() {
        TraderResponseDto traderResponseDto = null;
        try {
            traderResponseDto = traderService.updateTrader(currentTrader.id(), getDtoFromFields());
        } catch (UiAlertException ex) {
            AlertUtil.getAlert(ERROR, "Error", ex.getMessage(), ex.getDescription())
                    .showAndWait();
        }
        if (traderResponseDto != null) {
            AlertUtil.getAlert(INFORMATION, "Success", "Trader successfully updated")
                    .showAndWait();
            fxWeaver.loadController(LotsActiveController.class).refreshLots();
            stage.close();
        }
    }

    public void reset() {
        surnameField.setText(currentTrader.surname());
        nameField.setText(currentTrader.name());
        patronymicField.setText(currentTrader.patronymic());
    }

    public void show(TraderResponseDto currentTrader) {
        this.currentTrader = currentTrader;
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Trader edit menu");
        reset();
        stage.showAndWait();
    }

}
