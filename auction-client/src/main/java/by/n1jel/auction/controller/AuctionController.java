package by.n1jel.auction.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@FxmlView("auction.fxml")
public class AuctionController {

    private Stage stage;
    private final FxWeaver fxWeaver;

    @FXML
    private AnchorPane auctionView;

    @FXML
    private MenuItem aboutMenu;

    @FXML
    private MenuItem logoutMenu;

    @FXML
    private Tab activeTab;

    @FXML
    private Tab soldTab;

    @FXML
    private Tab buyersTab;

    @FXML
    private Tab sellersTab;

    @FXML
    private Tab tradersTab;

    @FXML
    private Tab reportsTab;


    @FXML
    public void initialize() {

        this.stage = new Stage();
        stage.setScene(new Scene(auctionView));

        logoutMenu.setOnAction(actionEvent -> {
            stage.close();
            fxWeaver.loadController(AuthentificationController.class).show();
        });

        aboutMenu.setOnAction(actionEvent -> {
            fxWeaver.loadController(AboutController.class).show();
        });

    }

    public void show() {
        stage.setResizable(false);
        stage.setTitle("Auction");
        stage.show();
    }

}

