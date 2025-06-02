package by.n1jel.auction.controller;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.TraderCreateRequest;
import by.n1jel.auction.dto.TraderResponseDto;
import by.n1jel.auction.exception.UiAlertException;
import by.n1jel.auction.service.AuctionLotClientService;
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

import java.math.BigDecimal;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;


@RequiredArgsConstructor
@Component
@FxmlView("modal-create-trader.fxml")
public class ModalCreateTraderController {

    private final TraderClientService traderService;
    private final FxWeaver fxWeaver;

    private Stage stage;

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField patronymicField;

    @FXML
    Button saveButton, resetButton;

    @FXML
    private VBox modalCreateView;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(modalCreateView));

    }

    private TraderCreateRequest getCreateRequest(){
        if(!nameField.getText().trim().isEmpty() && !surnameField.getText().trim().isEmpty() && !patronymicField.getText().trim().isEmpty()) {
            return new TraderCreateRequest(surnameField.getText(), nameField.getText(), patronymicField.getText());
        } else {
            AlertUtil.getAlert(ERROR, "Some fields are missing", "Fill the empty fields first");
            return null;
        }
    }

    public void create() {
        TraderResponseDto traderResponseDto = null;
        try{
            traderResponseDto = traderService.createTrader(getCreateRequest());
        } catch (UiAlertException ex){
            AlertUtil.getAlert(ERROR, "Error", ex.getMessage(), ex.getDescription())
                    .showAndWait();
        }
        if (traderResponseDto != null) {
            AlertUtil.getAlert(INFORMATION, "Success", "Trader successfully created")
                    .showAndWait();
            fxWeaver.loadController(TraderController.class).refreshTable();
            stage.close();
        }
    }

    public void clear() {
        surnameField.clear();
        nameField.clear();
        patronymicField.clear();
    }

    public void show() {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Trader creation");
        stage.showAndWait();
    }

}
