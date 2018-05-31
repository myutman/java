package com.github.myutman.java;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class FTPClientGUI extends Application {

    private String ipValue;
    private int portNum;
    private ObservableList<String> observableList;
    private Stage primaryStage;
    private String current;

    private void downloadFile(String src, String name) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        File defaultDirectory = new File(current);
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(primaryStage);
        String path = null;
        try {
            path = selectedDirectory.getCanonicalPath();
            current = path;
        } catch (NullPointerException ignored) {
            return;
        } catch (IOException e) {
            observableList.add(0, "Error: Couldn't write to chosen directory");
        }
        try (Socket socket = new Socket(ipValue, portNum);) {
            FTPClient.get(socket, src + File.separator + name,path + File.separator + name);
        } catch (IOException e) {
            observableList.add(0, "Error: Couldn't download file");
        }
        observableList.add(0, "File " + name + " saved to " + path);
    }

    private ImageView newFolderIcon() {
        ImageView icon = new ImageView("/folder.png");
        icon.setPreserveRatio(true);
        icon.setFitHeight(25);
        return icon;
    }

    private ImageView newFileIcon() {
        ImageView icon = new ImageView("/file.png");
        icon.setPreserveRatio(true);
        icon.setFitHeight(25);
        return icon;
    }

    private void updateTree(TreeItem<Label> item){
        ArrayList<Map<String, Object>> list = null;
        TreeItem<Label> cur = item;
        StringBuilder builder = new StringBuilder();
        while (cur != null) {
            builder.insert(0, File.separator + cur.getValue().getText());
            cur = cur.getParent();
        }
        String path = builder.toString();
        try {
            path = new File(path).getCanonicalPath();
        } catch (IOException e) {
            observableList.add(0, "Error: " + e.getMessage());
        }

        try (Socket socket = new Socket(ipValue, portNum)) {
            list = new ArrayList(FTPClient.list(socket, path));
        } catch (UnknownHostException e) {
            observableList.add(0, "Error: Unknown host");
            return;
        } catch (IOException e) {
            observableList.add(0, "Error: No connection");
            return;
        }
        list.sort(Comparator.comparing(map -> (String) map.get("name")));
        item.getChildren().clear();
        for (Map<String, Object> map : list) {
            String name = (String) map.get("name");
            TreeItem<Label> newItem = new TreeItem<>(new Label(name));
            if (!map.get("is_dir").equals(true)) {
                newItem.setGraphic(newFileIcon());
                String finalPath = path;
                newItem.getGraphic().setOnMouseClicked(event -> downloadFile(finalPath, name));
            } else {
                newItem.setGraphic(newFolderIcon());
                newItem.getGraphic().setOnMouseClicked(event -> updateTree(newItem));
            }
            item.getChildren().add(newItem);
        }
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        AnchorPane screen = FXMLLoader.load(getClass().getResource("/screen.fxml"));
        Scene scene = new Scene(screen);
        primaryStage.setTitle("FTPCLient");
        primaryStage.setScene(scene);
        primaryStage.show();
        Button button = (Button) scene.lookup("#button");
        ListView<String> listView = (ListView) scene.lookup("#list");
        TreeView<Label> treeView = (TreeView) scene.lookup("#tree");
        TextField ip = (TextField) scene.lookup("#ip");
        TextField port = (TextField) scene.lookup("#port");
        ip.setText("127.0.0.1");
        port.setText("6666");
        observableList = listView.getItems();

        button.setOnMouseClicked(event -> {
            ipValue = ip.getText();
            String portValue = port.getText();
            if (!ipValue.matches("([0-1]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])\\.([0-1]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])\\.([0-1]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])\\.([0-1]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])")) {
                observableList.add(0, "Error: Incorrect ip");
                return;
            }
            if (!portValue.matches("[0-9]?[0-9]?[0-9]?[0-9]?[0-9]")) {
                observableList.add(0, "Error: Incorrect port");
                return;
            }
            portNum = Integer.parseInt(portValue);
            if (portNum > 65535) {
                observableList.add(0, "Error: Incorrect port");
                return;
            }
            current = "/";
            TreeItem<Label> root = new TreeItem<Label>(new Label("/"));
            root.setGraphic(newFolderIcon());
            treeView.setRoot(root);
            root.getValue().setFont(Font.font(20));
            root.getGraphic().setOnMouseClicked(event1 -> updateTree(root));
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
