package by.n1jel.auction.controller;

import atlantafx.base.theme.Styles;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.service.AuctionLotClientService;
import by.n1jel.auction.utils.AlertUtil;
import by.n1jel.auction.utils.CustomPageImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.WARNING;

@Component
@RequiredArgsConstructor
@FxmlView("sellers.fxml")
public class SellersController {
    private final AuctionLotClientService clientService;
    private final FxWeaver fxWeaver;

    private Stage stage;
    private ObservableList<LotResponseDto> lots = FXCollections.observableArrayList();
    private CustomPageImpl<LotResponseDto> lotsList;

    @FXML
    private AnchorPane sellersView;

    @FXML
    private Button deleteButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<LotResponseDto> lotsTable;

    @FXML
    private TableColumn<LotResponseDto, Long> sellerIdColumn;

    @FXML
    private TableColumn<LotResponseDto, Long> buyerIdColumn;

    @FXML
    private TableColumn<LotResponseDto, Long> lotIdColumn;

    @FXML
    private TableColumn<LotResponseDto, String> sellerFullnameColumn;

    @FXML
    private TableColumn<LotResponseDto, String> soldAtColumn;

    @FXML
    private TableColumn<LotResponseDto, BigDecimal> priceColumn;


    public void refreshLots() {
        this.lotsList = clientService.findAllSold(pagination.getCurrentPageIndex(), 10);
        lots.clear();
        lots.addAll(lotsList.toList());
    }


    private String formatDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }

    public void show() {
        stage.show();
    }

    public void initialize() {

        stage = new Stage();
        stage.setScene(new Scene(sellersView));

        refreshButton.setGraphic(new FontIcon(Feather.REFRESH_CW));
        refreshButton.getStyleClass().addAll(
                Styles.BUTTON_ICON, Styles.FLAT, Styles.ACCENT
        );

        deleteButton.setGraphic(new FontIcon(Feather.TRASH));
        deleteButton.getStyleClass().addAll(
                Styles.ROUNDED, Styles.DANGER
        );

        sellerIdColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().sellerId()));
        buyerIdColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().buyerId()));
        lotIdColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().id()));
        sellerFullnameColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().sellerFullname()));
        soldAtColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(formatDate(lot.getValue().soldAt())));
        priceColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().price()));


        lotsTable.getStyleClass().addAll(Styles.BORDERED);
        lotsTable.setItems(lots);
        refreshLots();

        refreshButton.setOnAction(actionEvent -> {
            refreshLots();
        });

        deleteButton.setOnAction((e) -> {
            LotResponseDto currentLot = lotsTable.getSelectionModel().getSelectedItem();
            if (currentLot != null) {
                clientService.deleteById(currentLot.id());
                refreshLots();
            } else {
                AlertUtil.getAlert(WARNING, "Item not selected", "Select item to delete");
            }
        });

        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(lotsList.getTotalPages());
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(pageNum -> {
            lotsTable.getItems().setAll(clientService.findAllSold(pageNum, 10).toList());
            return new StackPane();
        });
    }
}
