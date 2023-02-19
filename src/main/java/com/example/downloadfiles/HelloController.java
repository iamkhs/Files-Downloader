package com.example.downloadfiles;

import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloController {
    public TextField downloadUrl;
    public Button downloadButton;
    public ProgressBar progressBar;
    public Label progressLabel;


    public void initialize() {
        downloadButton.setOnAction(e -> downloadFile());
    }

    private void downloadFile(){
        progressBar.setVisible(true);
        progressLabel.setVisible(true);
        String urlText = downloadUrl.getText();
        System.out.println(urlText);
        if (urlText.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Url not found!");
            alert.setHeaderText("Please provide the Url!");
            alert.show();
            return;
        }
        Path path = Paths.get(urlText);

        String fileName = path.toString();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        System.out.println("File extension: " + extension);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.setInitialFileName("untitled." + extension);

        File selectedFile = fileChooser.showSaveDialog(downloadButton.getScene().getWindow());
        if (selectedFile == null){
            System.out.println("couldn't be null");
            return;
        }

        String downloadPath = selectedFile.getAbsolutePath();

        DownloadFile downloadFile = new DownloadFile(urlText, downloadPath, progressLabel);
        Thread downloadThread = new Thread(downloadFile);

        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(downloadFile.progressProperty());
        downloadThread.start();
        downloadUrl.clear();
    }

}