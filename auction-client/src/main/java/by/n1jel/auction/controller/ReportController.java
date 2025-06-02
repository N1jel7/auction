package by.n1jel.auction.controller;

import atlantafx.base.theme.Styles;
import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.dto.ReportRequestDto;
import by.n1jel.auction.dto.ReportResponseDto;
import by.n1jel.auction.service.AuctionLotClientService;
import by.n1jel.auction.utils.AlertUtil;
import by.n1jel.auction.utils.ExcelConverter;
import by.n1jel.auction.utils.WordConverter;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;

@Component
@RequiredArgsConstructor
@FxmlView("reports.fxml")
public class ReportController {

    private final ExcelConverter excelConverter;
    private final WordConverter wordConverter;
    private final AuctionLotClientService clientService;

    private ReportResponseDto reportResponseDto;
    private ObservableList<LotResponseDto> lots = FXCollections.observableArrayList();

    private Stage stage;

    @FXML
    private AnchorPane reportView;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private Button formButton;

    @FXML
    private Button convertButton;

    @FXML
    private MenuButton selectButton;

    @FXML
    private MenuItem wordItem;

    @FXML
    private MenuItem excelItem;

    @FXML
    private TableView<LotResponseDto> reportTableView;

    @FXML
    private TableColumn<LotResponseDto, Long> lotIdColumn;

    @FXML
    private TableColumn<LotResponseDto, String> lotNameColumn;

    @FXML
    private TableColumn<LotResponseDto, String> lotTypeColumn;

    @FXML
    private TableColumn<LotResponseDto, BigDecimal> lotPriceColumn;

    @FXML
    private TableColumn<LotResponseDto, String> lotSoldAtColumn;

    @FXML
    private Label lotsPriceSumLabel;

    @FXML
    private Label lotsAmountLabel;
    
    private boolean validateDate() {
        return fromDatePicker.getValue() != null && toDatePicker.getValue() != null;
    }

    private void getReport() {
        this.reportResponseDto = clientService.getReport(
                new ReportRequestDto(fromDatePicker.getValue(), toDatePicker.getValue())
        );
        refreshView();
        selectButton.setDisable(false);
        convertButton.setDisable(false);
    }

    public void refreshView() {
        lots.clear();
        lots.addAll(reportResponseDto.lots());
        lotsPriceSumLabel.setText("Общая сумма продаж за период: " + reportResponseDto.totalLotsPrice() + "$");
        lotsAmountLabel.setText("Всего продано лотов: " + reportResponseDto.totalLotsAmount());
    }

    private String formatDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }

    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(reportView));

        selectButton.setMinSize(108, 25.6);

        lotIdColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().id()));
        lotNameColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().name()));
        lotTypeColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().type()));
        lotPriceColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(lot.getValue().price()));
        lotSoldAtColumn.setCellValueFactory(lot -> new ReadOnlyObjectWrapper<>(formatDate(lot.getValue().soldAt())));


        reportTableView.getStyleClass().addAll(Styles.BORDERED);
        reportTableView.setItems(lots);

        formButton.setOnAction(actionEvent -> {
            if (validateDate()) {
                getReport();
            }
        });

        convertButton.setOnAction(actionEvent -> {
            if(selectButton.getText().trim().isEmpty()) {
                AlertUtil.getAlert(WARNING, "Неправильно указан тип конвертирования", "Необходимо выбрать Word либо Excel")
                        .showAndWait();
            } else {
                switch (selectButton.getText().trim()) {
                    case "Excel":
                        if(excelConverter.convert(reportResponseDto)) {
                            AlertUtil.getAlert(INFORMATION,"Отчет успешно конвертирован в Excel", "Вы можете посмотреть результат в папке /resources/out/")
                                    .showAndWait();
                        }
                        break;
                    case "Word":
                        if(wordConverter.convert(reportResponseDto)) {
                            AlertUtil.getAlert(INFORMATION,"Отчет успешно конвертирован в Word", "Вы можете посмотреть результат в папке /resources/out/")
                                    .showAndWait();
                        }
                        break;
                }
            }
        });

        excelItem.setOnAction(actionEvent -> {
            selectButton.setText("Excel");
        });

        wordItem.setOnAction(actionEvent -> {
            selectButton.setText("Word");
        });

    }


}
