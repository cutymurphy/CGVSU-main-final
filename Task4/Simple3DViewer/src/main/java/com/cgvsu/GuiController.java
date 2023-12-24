package com.cgvsu;

import com.cgvsu.deletePolygons.PolygonsDeleter;
import com.cgvsu.deletePolygons.PolygonsDeleterException;
import com.cgvsu.deleteVertex.VertexDeleter;
import com.cgvsu.deleteVertex.VertexDeleterException;
import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objreader.ObjReaderException;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.objwriter.ObjWriterException;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.triangulation.TriangulatedModelWithCorrectNormal;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.vecmath.Vector3f;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class GuiController {

    final private float TRANSLATION = 0.5F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;
    private Model copyOfMesh = null;

    private Camera camera = new Camera(
            new Vector3f(0, 00, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();

        canvas.setOnMousePressed(event -> {
            dragStartX = event.getX();
            dragStartY = event.getY();
            isDragging = true;
        });

        canvas.setOnMouseReleased(event -> {
            isDragging = false;
        });

        canvas.setOnMouseDragged(event -> {
            if (isDragging) {
                double dragEndX = event.getX();
                double dragEndY = event.getY();

                double deltaX = (dragEndX - dragStartX) * 0.1;
                double deltaY = (dragEndY - dragStartY) * 0.1;

                camera.movePosition(new Vector3f((float) deltaX, (float) -deltaY, 0));

                dragStartX = dragEndX;
                dragStartY = dragEndY;
            }
        });

    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        // Установка начального каталога
        File initialDirectory = new File("D:\\IdeaProjects\\CGVSU-main\\3DModels");
        fileChooser.setInitialDirectory(initialDirectory);

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            copyOfMesh = mesh.getCopy();
        } catch (ObjReaderException | IOException exception) {
            showLoadErrorAlert(exception);
        }
    }

    @FXML
    private void onSaveModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");

        // Установка начального каталога
        File initialDirectory = new File("D:\\IdeaProjects\\CGVSU-main\\3DModels");
        fileChooser.setInitialDirectory(initialDirectory);

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            ObjWriter.writeModelToObjFile(String.valueOf(fileName), mesh);
            showSaveSuccessAlert(fileName);
        } catch (ObjWriterException exception) {
            showSaveErrorAlert(exception);
        }
    }

    @FXML
    private void onTriangulateModel() {
        if (mesh != null) {
            TriangulatedModelWithCorrectNormal newModel = new TriangulatedModelWithCorrectNormal(mesh);
            mesh.setPolygons(newModel.getTriangulatedPolygons());
            // После триангуляции необходимо обновить отображение
            RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) canvas.getWidth(), (int) canvas.getHeight());
        }
    }

    @FXML
    private TextField textField1;

    @FXML
    private void onDeleteVertexes() {
        String stringOfVertexes = textField1.getText();
        try {
            int[] indices = getNumbersFromStr(stringOfVertexes);
            VertexDeleter.deleteVertices(mesh, indices);
            RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) canvas.getWidth(), (int) canvas.getHeight());
        } catch (VertexDeleterException | NumberFormatException exception) {
            showDeleteVertexErrorAlert(exception);
        }
    }

    @FXML
    private TextField textField2;

    @FXML
    private void onDeletePolygons() {
        String stringOfVertexes = textField2.getText();
        try {
            int[] indices = getNumbersFromStr(stringOfVertexes);
            PolygonsDeleter.deletePolygons(mesh, indices);
            RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) canvas.getWidth(), (int) canvas.getHeight());
        } catch (PolygonsDeleterException | NumberFormatException exception) {
            showDeleteVertexErrorAlert(exception);
        }
    }

    private static int[] getNumbersFromStr(String str) {
        String[] numbersArray = str.split("[,\\s]+"); // Разделение строки по запятой или пробелу с запятой

        int[] numbers = new int[numbersArray.length]; // Создание массива для чисел

        for (int i = 0; i < numbersArray.length; i++) {
            numbers[i] = Integer.parseInt(numbersArray[i].trim()); // Преобразование строковых чисел в int
        }
        return numbers;
    }

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private void onRasterizeModel() {
        Color chosenColor = colorPicker.getValue(); // Получаем выбранный цвет из ColorPicker

        // Теперь можно использовать этот цвет в вашей логике рендеринга
        RenderEngine.renderColor(
                canvas.getGraphicsContext2D(),
                camera,
                mesh,
                (int) canvas.getWidth(),
                (int) canvas.getHeight(),
                chosenColor // Используем выбранный цвет
        );    }

    @FXML
    private void onPrintOriginalMesh() {
        mesh = copyOfMesh.getCopy();
        RenderEngine.renderColor(canvas.getGraphicsContext2D(), camera, mesh, (int) canvas.getWidth(), (int) canvas.getHeight(), Color.BLACK);
    }

    @FXML
    private void onRasterizeModelRed() {
        RenderEngine.renderColor(canvas.getGraphicsContext2D(), camera, mesh, (int) canvas.getWidth(), (int) canvas.getHeight(),
                Color.RED);
    }

    @FXML
    private void onRasterizeModelGreen() {
        RenderEngine.renderColor(canvas.getGraphicsContext2D(), camera, mesh, (int) canvas.getWidth(), (int) canvas.getHeight(),
                Color.GREEN);
    }
    @FXML
    private void onRasterizeModelBlue() {
        RenderEngine.renderColor(canvas.getGraphicsContext2D(), camera, mesh, (int) canvas.getWidth(), (int) canvas.getHeight(),
                Color.BLUE);
    }

    private void showSaveSuccessAlert(Path filePath) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText(null);
        alert.setContentText("Модель сохранена успешно в " + filePath.toString() + " !");
        alert.showAndWait();
    }

    private void showLoadErrorAlert(Exception error) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка!!!");
        alert.setHeaderText(null);
        alert.setContentText("Ошибка при открытии модели из файла: " + error.toString());
        alert.showAndWait();
    }

    private void showSaveErrorAlert(Exception error) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка!!!");
        alert.setHeaderText(null);
        alert.setContentText("Ошибка при сохранении в модели файл " + error.toString());
        alert.showAndWait();
    }

    private void showDeleteVertexErrorAlert(Exception error) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка!!!");
        alert.setHeaderText(null);
        alert.setContentText("Ошибка при удалении вершин модели " + error.toString());
        alert.showAndWait();
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }


    private double dragStartX, dragStartY;

    private boolean isDragging = false;

}