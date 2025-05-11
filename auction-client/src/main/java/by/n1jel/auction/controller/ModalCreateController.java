package by.n1jel.auction.controller;

import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.service.AuctionLotClientService;
import by.n1jel.auction.utils.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
@FxmlView("modal-create.fxml")
public class ModalCreateController {

    private final AuctionLotClientService clientService;

    private Stage stage;

    @FXML
    TextField nameField, priceField, typeField;

    @FXML
    Button saveButton, resetButton;

    @FXML
    private VBox modalCreateView;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(modalCreateView));

    }

    public void create() {
        LotResponseDto LotResponseDto = clientService.create(clientService.mapFieldsToCreateDto(nameField, priceField, typeField));
        if (LotResponseDto != null) {
            Alert alert = AlertUtil.getAlert(Alert.AlertType.INFORMATION, "Success", "Lot successfully created");
            alert.showAndWait();
            stage.close();
        } else {
            Alert alert = AlertUtil.getAlert(Alert.AlertType.ERROR, "Error", "Can't create lot, try again");
            alert.showAndWait();
            reset();
        }
    }

    public void reset() {
        nameField.clear();
        priceField.clear();
        typeField.clear();
    }

    public void edit() {

    }

    public void delete() {

    }

    public void show() {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Lot creation");
        stage.showAndWait();
    }

}
