package by.n1jel.auction.controller;

import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.service.AuctionLotClientService;
import by.n1jel.auction.utils.AlertUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.WARNING;

@RequiredArgsConstructor
@Component
@FxmlView("auction.fxml")
public class AuctionController {
    private ObservableList<LotResponseDto> lots = FXCollections.observableArrayList();
    private Stage stage;

    private final AuctionLotClientService clientService;
    private final FxWeaver fxWeaver;

    @FXML
    private AnchorPane auctionView;

    @FXML
    private TableView<LotResponseDto> lotsTable;

    @FXML
    public TableColumn<LotResponseDto, Long> idColumn;

    @FXML
    public TableColumn<LotResponseDto, String> nameColumn;

    @FXML
    public TableColumn<LotResponseDto, String> typeColumn;

    @FXML
    public TableColumn<LotResponseDto, BigDecimal> priceColumn;

    @FXML
    private Button createButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    public void refreshLots(){
        lots.clear();
        lots.addAll(clientService.findAll());
    }


    @FXML
    public void initialize() {

        this.stage = new Stage();
        stage.setScene(new Scene(auctionView));

        idColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().id()));
        nameColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().name()));
        typeColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().type()));
        priceColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().price()));

        lotsTable.setItems(lots);
        refreshLots();

        createButton.setOnAction(
                actionEvent -> fxWeaver.loadController(ModalCreateController.class).show()
        );

        editButton.setOnAction((e) -> {
            LotResponseDto currentLot = lotsTable.getSelectionModel().getSelectedItem();
            if(currentLot != null) {
                fxWeaver.loadController(ModalEditController.class).show(currentLot);
            } else {
                AlertUtil.getAlert(WARNING, "Item not selected", "Select item to edit");
            }
        });

        deleteButton.setOnAction((e) -> {
            LotResponseDto currentLot = lotsTable.getSelectionModel().getSelectedItem();
            if(currentLot != null) {
                clientService.deleteById(currentLot.id());
                refreshLots();
            } else {
                AlertUtil.getAlert(WARNING, "Item not selected", "Select item to delete");
            }
        });


    }

    public void show() {
        stage.setTitle("Auction");
        stage.show();
    }

}

