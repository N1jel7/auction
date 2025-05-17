package by.n1jel.auction.controller;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.exception.EmptyFieldException;
import by.n1jel.auction.exception.UiAlertException;
import by.n1jel.auction.service.AuctionLotClientService;
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
@FxmlView("modal-create.fxml")
public class ModalCreateController {

    private final AuctionLotClientService clientService;
    private final FxWeaver fxWeaver;

    private Stage stage;

    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField typeField;

    @FXML
    Button saveButton, resetButton;

    @FXML
    private VBox modalCreateView;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(modalCreateView));

    }

    private LotCreateRequestDto getCreateRequest(){
        if(!nameField.getText().trim().isEmpty() && !typeField.getText().trim().isEmpty() && !priceField.getText().trim().isEmpty()) {
            return new LotCreateRequestDto(nameField.getText(), typeField.getText(), new BigDecimal(priceField.getText()));
        } else {
            AlertUtil.getAlert(ERROR, "Some fields are missing", "Fill the empty fields first");
            return null;
        }
    }

    public void create() {
        LotResponseDto lotResponseDto = null;
        try{
            lotResponseDto = clientService.create(getCreateRequest());
        } catch (UiAlertException ex){
            AlertUtil.getAlert(ERROR, "Error", ex.getMessage(), ex.getDescription())
                    .showAndWait();
        }
        if (lotResponseDto != null) {
            AlertUtil.getAlert(INFORMATION, "Success", "Lot successfully created")
                    .showAndWait();
            fxWeaver.loadController(AuctionController.class).refreshLots();
            stage.close();
        }
    }

    public void clear() {
        nameField.clear();
        priceField.clear();
        typeField.clear();
    }

    public void show() {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Lot creation");
        stage.showAndWait();
    }

}
