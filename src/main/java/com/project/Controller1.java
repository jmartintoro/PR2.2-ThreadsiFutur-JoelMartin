package com.project;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class Controller1 implements initialize {

    @FXML
    private Button buttonCarregar, buttonAturar;
    @FXML
    private ImageView img;
    @FXML
    private AnchorPane container;
    @FXML
    private HBox hBox1, hBox2, hBox3;
    @FXML
    private Label textProgres;
    @FXML
    private ProgressBar progressBar;

    Random random = new Random();

    int fotosCarregades = -1;

    private ArrayList<ImageView> images1 = new ArrayList<ImageView>();
    private ArrayList<ImageView> images2 = new ArrayList<ImageView>();
    private ArrayList<ImageView> images3 = new ArrayList<ImageView>();
  
    @FXML
    private void animateToView0(ActionEvent event) {
        UtilsViews.setViewAnimating("View0");
    }

    @FXML
    private void loadImage() {
        generateImageViews();
        System.out.println("Loading image...");
        for (ImageView imageV : images1) {
        loadImageBackground((image) -> {
            System.out.println("Image loaded");
            imageV.setImage(image);
        });
    }
        for (ImageView ima : images2) {
        loadImageBackground((image) -> {
            System.out.println("Image loaded");
            ima.setImage(image);
        });
    }
        for (ImageView imgV : images3) {
        loadImageBackground((image) -> {
            System.out.println("Image loaded");
            imgV.setImage(image);
        });}
    }

    public void loadImageBackground(Consumer<Image> callBack) {
        // Use a thread to avoid blocking the UI
        CompletableFuture<Image> futureImage = CompletableFuture.supplyAsync(() -> {
            try {
                // Wait a second to simulate a long loading time
                int randomSecs = (random.nextInt(50) + 5) * 1000;
                Thread.sleep(randomSecs);
                
                fotosCarregades++;

                // Load the data from the assets file
                Image image = new Image(getClass().getResource("/assets/image.png").toString());
                Platform.runLater(() -> {
                    textProgres.setText("Progrés, " + (fotosCarregades + 1) + " de 24:");
                });

                final double i = (double) (fotosCarregades + 1) / 24;

                Platform.runLater(() -> {
                    final double roundedProgress = Math.round(i * 100.0) / 100.0;  // Redondear a 2 decimales
                    progressBar.setProgress(roundedProgress);
                    if (Math.abs(roundedProgress - 1.0) < 0.001){
                        progressBar.setStyle("-fx-accent: green");
                    } else {
                        progressBar.setStyle("-fx-accent: blue");
                    }
                });

                return image;

            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        })
        .exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });

        futureImage.thenAcceptAsync(result -> {
            callBack.accept(result);
        }, Platform::runLater);
    }

    private void generateImageViews() {
        for (int line=0; line < 3; line++) {
            System.out.println("linea " + line);
            for (int column=0; column < 8;column++){
                ImageView img = new ImageView();
                img.setFitWidth(50);
                img.setFitHeight(50);
                if (line == 0) {
                    hBox1.getChildren().add(img);
                    images1.add(img);
                } else if (line == 1) {
                    hBox2.getChildren().add(img);
                    images2.add(img);
                } else if (line == 2) {
                    hBox3.getChildren().add(img);
                    images3.add(img);
                } else {
                    System.out.println("Error, linea mal");
                }
            }
        }
    }
}