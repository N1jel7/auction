package by.n1jel.auction.controller;

import atlantafx.base.theme.Styles;
import by.n1jel.auction.dto.TraderResponseDto;
import by.n1jel.auction.service.TraderClientService;
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

import java.util.List;

import static javafx.scene.control.Alert.AlertType.WARNING;

@Component
@RequiredArgsConstructor
@FxmlView("traders.fxml")
public class TraderController {
    private final TraderClientService traderService;
    private final FxWeaver fxWeaver;

    private Stage stage;
    private ObservableList<TraderResponseDto> traders = FXCollections.observableArrayList();
    private CustomPageImpl<TraderResponseDto> tradersList;
    @FXML
    private AnchorPane tradersView;

    @FXML
    private Button createButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<TraderResponseDto> tradersTable;

    @FXML
    private TableColumn<TraderResponseDto, Long> idColumn;

    @FXML
    private TableColumn<TraderResponseDto, String> surnameColumn;

    @FXML
    private TableColumn<TraderResponseDto, String> nameColumn;

    @FXML
    private TableColumn<TraderResponseDto, String> patronymicColumn;

    public void refreshTable() {
        this.tradersList = traderService.findAllTraders(pagination.getCurrentPageIndex(), 10);
        traders.clear();
        traders.addAll(tradersList.toList());
    }

    public void show() {
        stage.show();
    }

    public void initialize() {

        stage = new Stage();
        stage.setScene(new Scene(tradersView));

        refreshButton.setGraphic(new FontIcon(Feather.REFRESH_CW));
        refreshButton.getStyleClass().addAll(
                Styles.BUTTON_ICON, Styles.FLAT, Styles.ACCENT
        );

        createButton.setGraphic(new FontIcon(Feather.PLUS));
        createButton.getStyleClass().addAll(
                Styles.ROUNDED, Styles.ACCENT
        );

        editButton.setGraphic(new FontIcon(Feather.EDIT));
        editButton.getStyleClass().addAll(
                Styles.ROUNDED, Styles.ACCENT
        );

        deleteButton.setGraphic(new FontIcon(Feather.TRASH));
        deleteButton.getStyleClass().addAll(
                Styles.ROUNDED, Styles.DANGER
        );

        idColumn.setCellValueFactory(trader -> new ReadOnlyObjectWrapper<>(trader.getValue().id()));
        surnameColumn.setCellValueFactory(trader -> new ReadOnlyObjectWrapper<>(trader.getValue().surname()));
        nameColumn.setCellValueFactory(trader -> new ReadOnlyObjectWrapper<>(trader.getValue().name()));
        patronymicColumn.setCellValueFactory(trader -> new ReadOnlyObjectWrapper<>(trader.getValue().patronymic()));


        tradersTable.getStyleClass().addAll(Styles.BORDERED);
        tradersTable.setItems(traders);
        refreshTable();

        createButton.setOnAction(
                actionEvent -> fxWeaver.loadController(ModalCreateTraderController.class).show()
        );

        refreshButton.setOnAction(actionEvent -> {
            refreshTable();
        });

        editButton.setOnAction((e) -> {
            TraderResponseDto currentTrader = tradersTable.getSelectionModel().getSelectedItem();
            if (currentTrader != null) {
                fxWeaver.loadController(ModalTraderEditController.class).show(currentTrader);
            } else {
                AlertUtil.getAlert(WARNING, "Trader is not selected", "You need to select trader which you want to edit")
                        .showAndWait();
            }
        });

        deleteButton.setOnAction((e) -> {
            TraderResponseDto currentTrader = tradersTable.getSelectionModel().getSelectedItem();
            if (currentTrader != null) {
                traderService.deleteTrader(currentTrader.id());
                refreshTable();
            } else {
                AlertUtil.getAlert(WARNING, "Item is not selected", "Select item to delete");
            }
        });

        pagination.setCurrentPageIndex(1);
        pagination.setPageCount(tradersList.getTotalPages());
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(pageNum -> {
            tradersTable.getItems().setAll(traderService.findAllTraders(pageNum, 10).toList());
            return new StackPane();
        });
    }
}
