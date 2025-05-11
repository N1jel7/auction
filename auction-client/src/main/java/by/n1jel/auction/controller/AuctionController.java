package by.n1jel.auction.controller;

import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.service.AuctionLotClientService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
@FxmlView("auction.fxml")
public class AuctionController {
    private ObservableList<LotResponseDto> lots = FXCollections.observableArrayList();

    private final AuctionLotClientService clientService;
    private final FxWeaver fxWeaver;

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

    public void addLot(LotResponseDto lotResponseDto) {
        lots.add(lotResponseDto);
    }

    public void addLot(List<LotResponseDto> lotResponseDtos) {
        lots.addAll(lotResponseDtos);
    }


    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().id()));
        nameColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().name()));
        typeColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().type()));
        priceColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().price()));

        lotsTable.setItems(lots);


        createButton.setOnAction(
                actionEvent -> fxWeaver.loadController(ModalCreateController.class).show()
        );


    }


}

