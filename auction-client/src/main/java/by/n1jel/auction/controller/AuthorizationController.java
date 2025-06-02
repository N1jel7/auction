package by.n1jel.auction.controller;


import atlantafx.base.theme.Styles;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@FxmlView("authorization.fxml")
public class AuthorizationController {

    private final FxWeaver fxWeaver;
    private final AuthentificationController authentificationController;
    private Stage stage;

    @FXML
    private AnchorPane authView;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button loginButton;


    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(authView));

        loginButton.getStyleClass().addAll(
                Styles.ROUNDED, Styles.ACCENT
        );

        loginField.getStyleClass().addAll(Styles.ROUNDED);

        passField.getStyleClass().addAll(Styles.ROUNDED);
        passField.setPrefWidth(250);


        loginButton.setOnAction((actionEvent -> {
            authentificationController.close();
            fxWeaver.loadController(AuctionController.class).show();
        }));
    }

    public void show() {
        stage.setTitle("Authorization");
        stage.show();
    }
}
