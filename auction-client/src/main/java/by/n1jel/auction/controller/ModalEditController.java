package by.n1jel.auction.controller;

import by.n1jel.auction.dto.LotCreateRequestDto;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.LotUpdateRequestDto;
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
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;


@RequiredArgsConstructor
@Component
@FxmlView("modal-edit.fxml")
public class ModalEditController {

    private final AuctionLotClientService clientService;

    private final AuctionController auctionController;

    private LotResponseDto currentLot;
    private Stage stage;

    @FXML
    TextField nameField, priceField, typeField;

    @FXML
    Button saveButton, resetButton;

    @FXML
    private VBox modalEditView;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(modalEditView));

    }

    private LotUpdateRequestDto getUpdateRequest(){
        if(!nameField.getText().trim().isEmpty() && !typeField.getText().trim().isEmpty() && !priceField.getText().trim().isEmpty()) {
            return new LotUpdateRequestDto(nameField.getText(), typeField.getText(), new BigDecimal(priceField.getText()));
        } else {
            AlertUtil.getAlert(ERROR, "Some fields are missing", "Fill the empty fields first");
            return null;
        }
    }

    public void edit() {
        LotResponseDto lotResponseDto = null;
        try{
            lotResponseDto = clientService.updateById(currentLot.id(), getUpdateRequest());
        } catch (UiAlertException ex){
            AlertUtil.getAlert(ERROR, "Error", ex.getMessage(), ex.getDescription())
                    .showAndWait();
        }
        if (lotResponseDto != null) {
            AlertUtil.getAlert(INFORMATION, "Success", "Lot successfully updated")
                    .showAndWait();
            auctionController.refreshLots();
            stage.close();
        }
    }

    public void reset() {
        nameField.setText(currentLot.name());
        priceField.setText(currentLot.price().toString());
        typeField.setText(currentLot.type());
    }

    public void show(LotResponseDto currentLot) {
        this.currentLot = currentLot;
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Lot edit menu");
        stage.showAndWait();
    }

}
