package com.example.downloadfiles;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloadFile implements Runnable{
    private final String url;
    private final String downloadPath;
    private final DoubleProperty progress = new SimpleDoubleProperty(0);
    private final Label progessLabel;

    public DownloadFile(String url, String downloadPath, Label label){
        this.url = url;
        this.downloadPath = downloadPath;
        this.progessLabel = label;
    }

    public DoubleProperty progressProperty() {
        return progress;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(this.url);
            InputStream in = url.openStream();
            FileOutputStream out = new FileOutputStream(downloadPath);
            byte[] buffer = new byte[1024];
            int bytesRead;
            long fileSize = url.openConnection().getContentLengthLong();
            long totalBytesRead = 0;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                long finalTotalBytesRead = totalBytesRead;
                Platform.runLater(() -> {
                    double progressValue = ((double) finalTotalBytesRead / (double) fileSize);
                    progress.set(progressValue);
                    String progressText = String.format("%.0f%%", progressValue * 100);
                    progessLabel.setText(progressText);
                });
            }
            in.close();
            out.close();
            System.out.println("Successfully download");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Download Failed");
        }
    }
}
