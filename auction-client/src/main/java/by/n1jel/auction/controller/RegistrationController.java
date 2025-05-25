package by.n1jel.auction.controller;


import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@FxmlView("registration.fxml")
public class RegistrationController {

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
    }

    public void show() {
        stage.setTitle("Registration");
        stage.show();
    }
}
