package com.github.myutman.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    @Override
    public void start(final Stage primaryStage) throws Exception {
        AnchorPane mainMenu = FXMLLoader.load(getClass().getResource("/main_menu.fxml"));
        AnchorPane gameField = FXMLLoader.load(getClass().getResource("/game_field.fxml"));
        AnchorPane cpuSelect = FXMLLoader.load(getClass().getResource("/cpu_select.fxml"));

        final Scene mainMenuScene = new Scene(mainMenu);
        final Scene gameFieldScene = new Scene(gameField);
        final Scene cpuSelectScene = new Scene(cpuSelect);
        final Controller game = new Controller();
        final ImageView who = (ImageView) gameField.lookup("#who");
        final ImageView what = (ImageView) gameField.lookup("#what");

        Image imageX = new Image("/cross.png");
        Image imageO = new Image("/nolik.jpg");
        Image empty = new Image("/white.png");
        Image turn = new Image("/turn.jpg");
        Image win = new Image("/wins.jpeg");
        Image draw = new Image("/draw.png");
        ImageView[][] field = new ImageView[3][3];


        /// Game UI thread. Changes pictures.
        Thread thread = new Thread(() -> {
            while (true) {
                synchronized (game) {
                    try {
                        game.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (game.getState()[i][j].equals(GameState.None)) {
                                field[i][j].setImage(empty);
                            } else if (game.getState()[i][j].equals(GameState.X)) {
                                field[i][j].setImage(imageX);
                            } else if (game.getState()[i][j].equals(GameState.O)) {
                                field[i][j].setImage(imageO);
                            }
                        }
                    }
                    if (game.gameOver()) {
                        GameState result = game.result();
                        if (result.equals(GameState.Draw)) {
                            who.setImage(null);
                            what.setImage(draw);
                        } else if (result.equals(GameState.X)) {
                            who.setImage(imageX);
                            what.setImage(win);
                        }
                        else if (result.equals(GameState.O)) {
                            who.setImage(imageO);
                            what.setImage(win);
                        }
                    } else {
                        if (game.getTurn().equals(GameState.X)) {
                            who.setImage(imageX);
                            what.setImage(turn);
                        } else {
                            who.setImage(imageO);
                            what.setImage(turn);
                        }
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();

        for (Node x: mainMenu.getChildren()){
            if ("pvp_button".equals(x.getId())) {
                x.setOnMouseClicked(event -> {
                    primaryStage.setScene(gameFieldScene);
                    primaryStage.show();
                    Thread thread1 = new Thread(() -> game.startGame(new HumanPlayer(GameState.X), new HumanPlayer(GameState.O)));
                    thread1.setDaemon(true);
                    thread1.start();
                });
            } else if ("cpu_button".equals(x.getId())) {
                x.setOnMouseClicked(event -> {
                    primaryStage.setScene(cpuSelectScene);
                    primaryStage.show();
                });
            }
        }

        ToggleGroup level = new ToggleGroup();
        ToggleGroup color = new ToggleGroup();

        for (Node x: cpuSelect.getChildren()){
            if ("main_menu_button".equals(x.getId())) {
                x.setOnMouseClicked(event -> {
                    primaryStage.setScene(mainMenuScene);
                    primaryStage.show();
                });
            } else if ("game_button".equals(x.getId())) {
                x.setOnMouseClicked(event -> {
                    primaryStage.setScene(gameFieldScene);
                    primaryStage.show();
                    Player player1;
                    Player player2;
                    if ("x".equals(color.getSelectedToggle().getUserData())) {
                        player1 = new HumanPlayer(GameState.X);
                        if ("easy".equals(level.getSelectedToggle().getUserData())) {
                            player2 = new EasyCPUPlayer(GameState.O);
                        } else {
                            player2 = new HardCPUPlayer(GameState.O);
                        }
                    } else {
                        if ("easy".equals(level.getSelectedToggle().getUserData())) {
                            player1 = new EasyCPUPlayer(GameState.X);
                        } else {
                            player1 = new HardCPUPlayer(GameState.X);
                        }
                        player2 = new HumanPlayer(GameState.O);
                    }
                    Thread thread1 = new Thread(() -> game.startGame(player1, player2));
                    thread1.setDaemon(true);
                    thread1.start();
                });
            } else if ("easy".equals(x.getId()) || "hard".equals(x.getId())) {
                RadioButton b = (RadioButton) x;
                b.setToggleGroup(level);
                b.setUserData(x.getId());
            } else if ("x".equals(x.getId()) || "o".equals(x.getId())) {
                RadioButton b = (RadioButton) x;
                b.setToggleGroup(color);
                b.setUserData(x.getId());
            }
        }

        for (Node x: gameField.getChildren()){
            if ("main_menu_button".equals(x.getId())) {
                x.setOnMouseClicked(event -> {
                    primaryStage.setScene(mainMenuScene);
                    primaryStage.show();
                });
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = new ImageView();
                field[i][j].setFitHeight(100);
                field[i][j].setFitWidth(100);
                field[i][j].setX(i * 100);
                field[i][j].setY(j * 100);
                int finalI = i;
                int finalJ = j;
                field[i][j].setOnMouseClicked(event -> {
                    if (field[finalI][finalJ].getImage().equals(empty)) {
                        synchronized (game) {
                            if (game.isCurrentPlayerHuman() && !game.gameOver()) {
                                game.set(finalI, finalJ, game.getTurn());
                                game.changeTurn();
                            }
                        }
                    }
                });
                gameField.getChildren().add(field[i][j]);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
