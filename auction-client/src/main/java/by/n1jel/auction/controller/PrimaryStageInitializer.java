package by.n1jel.auction.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.stage;
        stage.setTitle("Connection manager");
        Scene scene = new Scene(fxWeaver.loadView(ConnectionWindowController.class));
        stage.setScene(scene);
        fxWeaver.loadController(ConnectionWindowController.class).setStage(stage);
        stage.setResizable(false);
        stage.show();

    }
}
