package com.project;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

public class Controller0 {
    Random random = new Random();

    @FXML
    private Button button1, button2, button3;
    @FXML
    private AnchorPane container;
    @FXML
    private Label percentatge1, percentatge2, percentatge3;
    @FXML 
    private ProgressBar progressBar1, progressBar2, progressBar3;
    
    private ExecutorService executor = Executors.newFixedThreadPool(3); // Creem una pool de tres fils

    @FXML
    private void animateToView1(ActionEvent event) {
        UtilsViews.setViewAnimating("View1");
    }

    private boolean isRunning1 = false;

    @FXML
    private void runTask1() {
        if (!isRunning1) {
            progressBar1.setProgress(0);
            backgroundTask(1);
            button1.setText("Aturar");
            isRunning1 = true;
        } else {
            button1.setText("Iniciar");
            isRunning1 = false;
        }
    }

    private boolean isRunning2 = false;

    @FXML
    private void runTask2() {
        if (!isRunning2) {
            progressBar2.setProgress(0);
            backgroundTask(2);
            button2.setText("Aturar");
            isRunning2 = true;
        } else {
            button2.setText("Iniciar");
            isRunning2 = false;
        }
    }

    private boolean isRunning3 = false;

    @FXML
    private void runTask3() {
        if (!isRunning3) {
            progressBar3.setProgress(0);
            backgroundTask(3);
            button3.setText("Aturar");
            isRunning3 = true;
        } else {
            button3.setText("Iniciar");
            isRunning3 = false;
        }
    }

    private void backgroundTask(int index) {
        // Executar la tasca
        executor.submit(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    
                    if (index == 1 && isRunning1) {
                        final float percentage = i;
                        // Actualitzar el Label en el fil d'aplicació de l'UI
                        Platform.runLater(() -> {
                            percentatge1.setText("Tasca 1 - " + String.valueOf(percentage) + "%");
                            progressBar1.setProgress(percentage/100);
                            if (percentage < 100) {
                                progressBar1.setStyle("-fx-accent: blue");
                            } else {
                                progressBar1.setStyle("-fx-accent: green");
                                button1.setText("Iniciar");
                                isRunning1 = false;
                            }
                        });
                        System.out.println("Updating label: " + index + ", Value: " + percentage);
                        Thread.sleep(1000);
                        
                    }

                    if (index == 2 && isRunning2) {
                        i += random.nextInt(3) + 2;
                        if ( i >= 101 ){
                            i = 100;
                        }
                        final float percentage = i;
                        // Actualitzar el Label en el fil d'aplicació de l'UI
                        Platform.runLater(() -> {
                            percentatge2.setText("Tasca 2 - " + String.valueOf(percentage) + "%");
                            progressBar2.setProgress(percentage/100);
                            if (percentage < 100) {
                                progressBar2.setStyle("-fx-accent: orange");
                            } else {
                                progressBar2.setStyle("-fx-accent: green");
                                button2.setText("Iniciar");
                                isRunning2 = false;
                            }
                        });
                        System.out.println("Updating label: " + index + ", Value: " + percentage);
                        int randomSecs = (random.nextInt(4) + 3) * 1000;
                        Thread.sleep(randomSecs);
                    }

                    if (index == 3 && isRunning3) {
                        i += random.nextInt(5) + 4;
                        if ( i >= 101 ){
                            i = 100;
                        }
                        final float percentage = i;
                        // Actualitzar el Label en el fil d'aplicació de l'UI
                        Platform.runLater(() -> {
                            percentatge3.setText("Tasca 3 - " + String.valueOf(percentage) + "%");
                            progressBar3.setProgress(percentage/100);
                            if (percentage < 100) {
                                progressBar3.setStyle("-fx-accent: purple");
                            } else {
                                progressBar3.setStyle("-fx-accent: green");
                                button3.setText("Iniciar");
                                isRunning3 = false;
                            }
                        });
                        System.out.println("Updating label: " + index + ", Value: " + percentage);
                        int randomSecs = (random.nextInt(7) + 3) * 1000;
                        Thread.sleep(randomSecs);
                    }

                    
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    
    // Aquesta funció la cridaries quan vulguis tancar l'executor (per exemple, quan tanquis la teva aplicació)
    public void stopExecutor() {
        executor.shutdown();
    }

}