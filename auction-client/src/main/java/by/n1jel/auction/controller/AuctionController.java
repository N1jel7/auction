package by.n1jel.auction.controller;

import by.n1jel.auction.dto.LotResponseDto;
import by.n1jel.auction.service.AuctionLotClientService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@FxmlView("auction.fxml")
public class AuctionController {

    private final AuctionLotClientService clientService;
    private final FxWeaver fxWeaver;

    @FXML
    ScrollPane scrollPane;

    @FXML
    HBox lotBox;

    @FXML
    Button createButton, editButton, deleteButton;

    public void addLot(LotResponseDto lotResponseDto)
    {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().add(new Label(lotResponseDto.id().toString()));
        hBox.getChildren().add(new Label(lotResponseDto.name()));
        hBox.getChildren().add(new Label(lotResponseDto.type()));
        hBox.getChildren().add(new Label(lotResponseDto.price().toString()));
        scrollPane.getChi
    }

    @FXML
    public void initialize() {
        createButton.setOnAction(
                actionEvent -> fxWeaver.loadController(ModalCreateController.class).show()
        );
    }

}
