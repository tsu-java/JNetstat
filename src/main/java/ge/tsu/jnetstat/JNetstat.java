package ge.tsu.jnetstat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JNetstat extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("JNetstat");
        FXMLLoader fxmlLoader = new FXMLLoader(JNetstat.class.getResource("main.fxml"));
        Parent mainNode = loadFxml(stage, "main.fxml");
        Scene scene = new Scene(mainNode, 720, 480);
        scene.getStylesheets().add(
                JNetstat.class.getResource("style.css").toExternalForm()
        );
        stage.setScene(scene);
        stage.show();
    }

    private Parent loadFxml(Stage stage, String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JNetstat.class.getResource(fxmlPath));
        Parent parent = fxmlLoader.load();
        MainController controller = fxmlLoader.getController();
        controller.setStage(stage);
        return parent;
    }

    public static void main(String[] args) {
        launch();
    }
}