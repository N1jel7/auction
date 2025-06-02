package by.n1jel.auction.controller;


import atlantafx.base.theme.Styles;
import by.n1jel.auction.utils.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
@FxmlView("registration.fxml")
public class RegistrationController {

    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private AnchorPane registerView;

    @FXML
    private TextField loginField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button registerButton;


    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(registerView));

        loginField.getStyleClass().addAll(Styles.ROUNDED);
        emailField.getStyleClass().addAll(Styles.ROUNDED);
        passField.getStyleClass().addAll(Styles.ROUNDED);

        passField.setPrefWidth(250);

        registerButton.getStyleClass().addAll(
                Styles.ROUNDED, Styles.ACCENT
        );

        registerButton.setOnAction(actionEvent -> {
            fxWeaver.loadController(AuctionController.class).show();
            fxWeaver.loadController(AuthentificationController.class).close();
        });
    }

    public void show() {
        stage.setTitle("Registration");
        stage.show();
    }

    public void close() {
        stage.close();
    }
}
