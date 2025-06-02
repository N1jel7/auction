package by.n1jel.auction.controller;

import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import by.n1jel.auction.utils.AlertUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.stereotype.Component;

import static javafx.scene.control.Alert.AlertType.INFORMATION;

@RequiredArgsConstructor
@Component
@FxmlView("authentification.fxml")
public class AuthentificationController {
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab regTab;

    @FXML
    private Tab authTab;

    @FXML
    private AnchorPane authView;

    public void initialize() {

        tabPane.getStyleClass().add(Styles.TABS_CLASSIC);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setMinWidth(450);

        regTab.setGraphic(new FontIcon("mdal-how_to_reg"));
        authTab.setGraphic(new FontIcon("mdal-log_in"));

        this.stage = new Stage();
        stage.setScene(new Scene(authView));
        stage.setResizable(false);
    }

    public void show() {
        stage.setResizable(false);
        stage.setTitle("Welcome page");
        stage.show();
    }

    public void close() {
        stage.close();
    }
}
