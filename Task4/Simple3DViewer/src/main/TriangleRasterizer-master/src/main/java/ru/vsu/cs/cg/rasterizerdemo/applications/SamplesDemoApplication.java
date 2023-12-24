package src.main.java.ru.vsu.cs.cg.rasterizerdemo.applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SamplesDemoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SamplesDemoApplication.class.getResource("samples-demo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1760, 990);
        stage.setTitle("Samples");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}