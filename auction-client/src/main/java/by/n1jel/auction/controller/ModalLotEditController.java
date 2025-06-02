package by.n1jel.auction.controller;

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
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;


@RequiredArgsConstructor
@Component
@FxmlView("modal-edit-lot.fxml")
public class ModalLotEditController {

    private final AuctionLotClientService clientService;
    private final FxWeaver fxWeaver;

    private LotResponseDto currentLot;
    private Stage stage;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField sellerIdField;

    @FXML
    private TextField buyerIdField;

    @FXML
    private Button resetButton;

    @FXML
    private Button saveButton;

    @FXML
    private VBox modalEditView;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(modalEditView));

        resetButton.setOnAction((e) -> {
            reset();
        });

    }

    private boolean verified() {
        if (!nameField.getText().trim().isEmpty() && !typeField.getText().trim().isEmpty() && !priceField.getText().trim().isEmpty()) {
            return true;
        } else {
            AlertUtil.getAlert(ERROR, "Some fields are missing", "Fill the empty fields first")
                    .showAndWait();
            return false;
        }
    }

    public void edit() {
        LotResponseDto lotResponseDto = null;
        try {
            if(verified()) {
                if(!sellerIdField.getText().trim().isEmpty() && !buyerIdField.getText().trim().isEmpty()) {
                    lotResponseDto = clientService.updateById(currentLot.id(), new LotUpdateRequestDto(
                            nameField.getText(),
                            typeField.getText(),
                            new BigDecimal(priceField.getText()),
                            Long.parseLong(sellerIdField.getText()),
                            Long.parseLong(buyerIdField.getText())
                    ));
                }

                if(!sellerIdField.getText().trim().isEmpty() && buyerIdField.getText().trim().isEmpty()) {
                    lotResponseDto = clientService.updateById(currentLot.id(), new LotUpdateRequestDto(
                            nameField.getText(),
                            typeField.getText(),
                            new BigDecimal(priceField.getText()),
                            Long.parseLong(sellerIdField.getText()),
                            null
                    ));
                }

                if(sellerIdField.getText().trim().isEmpty() && buyerIdField.getText().trim().isEmpty()) {
                    lotResponseDto = clientService.updateById(currentLot.id(), new LotUpdateRequestDto(
                            nameField.getText(),
                            typeField.getText(),
                            new BigDecimal(priceField.getText()),
                            null,
                            null
                    ));
                }
            }


        } catch (UiAlertException ex) {
            AlertUtil.getAlert(ERROR, "Error", ex.getMessage(), ex.getDescription())
                    .showAndWait();
        }
        if (lotResponseDto != null) {
            AlertUtil.getAlert(INFORMATION, "Success", "Lot successfully updated")
                    .showAndWait();
            fxWeaver.loadController(LotsActiveController.class).refreshLots();
            stage.close();
        }
    }

    public void reset() {
        nameField.setText(currentLot.name());
        priceField.setText(currentLot.price().toString());
        typeField.setText(currentLot.type());
        sellerIdField.setText(String.valueOf(currentLot.sellerId()));
        buyerIdField.setText(String.valueOf(currentLot.buyerId()));
    }

    public void show(LotResponseDto currentLot) {
        this.currentLot = currentLot;
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Lot edit menu");
        reset();
        stage.showAndWait();
    }

}
