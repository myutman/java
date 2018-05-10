package com.github.myutman.java;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import oracle.jrockit.jfr.jdkevents.ThrowableTracer;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class FTPClientGUI extends Application {

    private Label label;
    private String ipValue;
    private int portNum;
    private String current;
    private  ObservableList observableList;
    private Stage primaryStage;

    public void downloadFile(String name) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        File defaultDirectory = new File(".");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(primaryStage);
        Dialog dialog = new Dialog();
        try (Socket socket = new Socket(ipValue, portNum);) {
            String path = selectedDirectory.getCanonicalPath();
            FTPClient.get(socket, current + File.separator + name,  path + File.separator + name);
        } catch (UnknownHostException e) {
            observableList.add("Uknown host");
        } catch (IOException e) {
            observableList.add("Error with connection");
        }
    }

    public void updateList(){
        label.setText(current);
        try (Socket socket = new Socket(ipValue, portNum)) {
            observableList.clear();
            System.err.println(current);
            ArrayList list = new ArrayList(FTPClient.list(socket, current));
            Map<String, Object> smallMap = new HashMap<>();
            smallMap.put("name", "..");
            smallMap.put("is_dir", true);
            list.add(smallMap);
            list.sort(Comparator.comparing(object -> {
                Map<String, Object> map = (Map) object;
                return (String) map.get("name");
            }));
            for (Object object : list) {
                Map<String, Object> map = (Map) object;
                Button newButton = new Button();
                observableList.add(newButton);
                String name = (String) map.get("name");
                newButton.setText(name);
                if (!map.get("is_dir").equals(true)) {
                    newButton.setStyle("-fx-base: #ff0000");
                    newButton.setOnMouseClicked(event -> {
                        downloadFile(name);
                    });
                } else {
                    newButton.setOnMouseClicked(event -> {
                        current = current + File.separator + name;
                        File file = new File(current);
                        try {
                            current = file.getCanonicalPath();
                        } catch (IOException e) {
                            observableList.clear();
                            observableList.add("No such file");
                        }
                        updateList();
                    });
                }
            }
        } catch (UnknownHostException e) {
            observableList.add("Uknown host");
        } catch (IOException e) {
            observableList.add("Error with connection");
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
        ListView listView = (ListView) scene.lookup("#list");
        TextField ip = (TextField) scene.lookup("#ip");
        TextField port = (TextField) scene.lookup("#port");
        label = (Label) scene.lookup("#label");
        ip.setText("127.0.0.1");
        port.setText("6666");
        final ArrayList arrayList = new ArrayList();
        observableList = FXCollections.observableArrayList(arrayList);
        listView.setItems(observableList);
        button.setOnMouseClicked(event -> {
            ipValue = ip.getText();
            String portValue = port.getText();
            observableList.clear();
            if (!ipValue.matches("([0-1]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])\\.([0-1]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])\\.([0-1]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])\\.([0-1]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])")) {
                observableList.add("Incorrect ip");
                return;
            }
            if (!portValue.matches("[0-9]?[0-9]?[0-9]?[0-9]?[0-9]")) {
                observableList.add("Incorrect port");
                return;
            }
            portNum = Integer.parseInt(portValue);
            if (portNum > 65535) {
                observableList.add("Incorrect port");
                return;
            }
            current = "/";
            updateList();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
