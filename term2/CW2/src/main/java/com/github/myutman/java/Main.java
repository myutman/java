package com.github.myutman.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * Created by myutman on 3/15/18.
 */
public class Main extends Application {
    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */

    private static int n;
    private static Label[][] field;
    private static Stage primaryStage;
    private static Controller game;

    public static void initScreen() {
        double height = (primaryStage.getHeight() - 50) / n;
        double width = primaryStage.getWidth() / n;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++) {
                synchronized (field[i][j]) {
                    field[i][j].setText("?");
                    field[i][j].setFont(new Font(height / 2));
                    field[i][j].setLayoutX(i * width);
                    field[i][j].setLayoutY(j * height);
                }
            }
        }
    }

    public static void updatePos(int i, int j) {
        synchronized (field[i][j]) {
            if (game.getOpened()[i][j]) {
                field[i][j].setText(Integer.toString(game.getField()[i][j]));
            } else {
                field[i][j].setText("?");
            }
        }
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        AnchorPane gameField = FXMLLoader.load(getClass().getResource("/game_field.fxml"));

        final Scene gameFieldScene = new Scene(gameField);
        game = new Controller(n);
        final ImageView who = (ImageView) gameField.lookup("#who");
        final ImageView what = (ImageView) gameField.lookup("#what");

        field = new Label[n][n];

        primaryStage.setTitle("Find the match");
        primaryStage.setScene(gameFieldScene);
        primaryStage.show();

        for (Node x: gameField.getChildren()){
            if ("restart_button".equals(x.getId())) {
                x.setOnMouseClicked(event -> {
                    synchronized (game) {
                        game.startGame();
                    }
                });
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                field[i][j] = new Label();
                field[i][j].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                field[i][j].setTextAlignment(TextAlignment.CENTER);
                int finalI = i;
                int finalJ = j;
                field[i][j].setOnMouseClicked(event -> {
                    synchronized (field[finalI][finalJ]) {
                        synchronized (game) {
                            if (!game.isFrozen()) {
                                game.makeTurn(finalI, finalJ);
                            }
                        }
                    }
                });
                gameField.getChildren().add(field[i][j]);
            }
        }

        game.startGame();
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            n = Math.max(4, Math.min(Integer.parseInt(args[0]), 16));
        } else {
            n = 4;
        }
        launch(args);
    }
}
