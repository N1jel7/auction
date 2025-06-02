package by.n1jel.auction.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@Component
@FxmlView("about.fxml")
public class AboutController {

    private final FxWeaver fxWeaver;

    private Stage stage;

    @FXML
    private AnchorPane aboutView;

    public void initialize() {
        stage = new Stage();
        stage.setScene(new Scene(aboutView));
    }

    public void show() {
        stage.setResizable(false);
        stage.setTitle("About");
        stage.show();
    }
}
