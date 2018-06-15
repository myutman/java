package com.github.myutman.java;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.github.myutman.java.RequestProtos.Request;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.Socket;

public class ClientUI extends Application {

    private class NumericFormatter extends TextFormatter<String> {
        
        public NumericFormatter() {
            super((change) -> {
                String text = change.getText();

                if (text.matches("[0-9]*")) {
                    return change;
                }

                return null;
            });
        }
    } 

    final String ip = "127.0.0.1";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/client.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        Button test = (Button) scene.lookup("#test");

        ToggleGroup buttons = new ToggleGroup();
        RadioButton nButton = (RadioButton) scene.lookup("#n_button");
        RadioButton mButton = (RadioButton) scene.lookup("#m_button");
        RadioButton deltaButton = (RadioButton) scene.lookup("#delta_button");
        buttons.getToggles().add(nButton);
        buttons.getToggles().add(mButton);
        buttons.getToggles().add(deltaButton);
        nButton.setSelected(true);

        ToggleGroup server = new ToggleGroup();
        RadioButton simple = (RadioButton) scene.lookup("#simple");
        RadioButton readWrite = (RadioButton) scene.lookup("#read_write");
        RadioButton notBlocking = (RadioButton) scene.lookup("#not_blocking");
        server.getToggles().add(simple);
        server.getToggles().add(readWrite);
        server.getToggles().add(notBlocking);
        simple.setSelected(true);

        TextField nFieldFrom = (TextField) scene.lookup("#n_field_from");
        TextField mFieldFrom = (TextField) scene.lookup("#m_field_from");
        TextField deltaFieldFrom = (TextField) scene.lookup("#delta_field_from");
        TextField xFieldFrom = (TextField) scene.lookup("#x_field_from");

        TextField nFieldTo = (TextField) scene.lookup("#n_field_to");
        TextField mFieldTo = (TextField) scene.lookup("#m_field_to");
        TextField deltaFieldTo = (TextField) scene.lookup("#delta_field_to");

        TextField nFieldStep = (TextField) scene.lookup("#n_field_step");
        TextField mFieldStep = (TextField) scene.lookup("#m_field_step");
        TextField deltaFieldStep = (TextField) scene.lookup("#delta_field_step");

        Label nDash = (Label) scene.lookup("#n_dash");
        Label mDash = (Label) scene.lookup("#m_dash");
        Label deltaDash = (Label) scene.lookup("#delta_dash");

         

        nFieldFrom.setTextFormatter(new NumericFormatter());
        mFieldFrom.setTextFormatter(new NumericFormatter());
        deltaFieldFrom.setTextFormatter(new NumericFormatter());
        xFieldFrom.setTextFormatter(new NumericFormatter());
        nFieldTo.setTextFormatter(new NumericFormatter());
        mFieldTo.setTextFormatter(new NumericFormatter());
        deltaFieldTo.setTextFormatter(new NumericFormatter());
        nFieldStep.setTextFormatter(new NumericFormatter());
        mFieldStep.setTextFormatter(new NumericFormatter());
        deltaFieldStep.setTextFormatter(new NumericFormatter());

        nFieldTo.setVisible(true);
        nFieldStep.setVisible(true);
        nDash.setVisible(true);
        mFieldStep.setVisible(false);
        mFieldTo.setVisible(false);
        mDash.setVisible(false);
        deltaFieldStep.setVisible(false);
        deltaFieldTo.setVisible(false);
        deltaDash.setVisible(false);

        nButton.setOnAction(event -> {
            nFieldTo.setVisible(true);
            nFieldStep.setVisible(true);
            nDash.setVisible(true);
            mFieldStep.setVisible(false);
            mFieldTo.setVisible(false);
            mDash.setVisible(false);
            deltaFieldStep.setVisible(false);
            deltaFieldTo.setVisible(false);
            deltaDash.setVisible(false);
        });

        mButton.setOnAction(event -> {
            nFieldTo.setVisible(false);
            nFieldStep.setVisible(false);
            nDash.setVisible(false);
            mFieldStep.setVisible(true);
            mFieldTo.setVisible(true);
            mDash.setVisible(true);
            deltaFieldStep.setVisible(false);
            deltaFieldTo.setVisible(false);
            deltaDash.setVisible(false);
        });

        deltaButton.setOnAction(event -> {
            nFieldTo.setVisible(false);
            nFieldStep.setVisible(false);
            nDash.setVisible(false);
            mFieldStep.setVisible(false);
            mFieldTo.setVisible(false);
            mDash.setVisible(false);
            deltaFieldStep.setVisible(true);
            deltaFieldTo.setVisible(true);
            deltaDash.setVisible(true);
        });

        test.setOnMouseClicked((event) -> {

            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();

            yAxis.setId("Metric value");

            LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
            XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
            XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
            XYChart.Series<Number, Number> series3 = new XYChart.Series<>();

            series1.setName("Metric 1");
            series2.setName("Metric 2");
            series3.setName("Metric 3");

            int mFrom = Integer.parseInt(mFieldFrom.getText());
            int mTo = mFrom;
            int mStep = 1;
            int nFrom = Integer.parseInt(nFieldFrom.getText());
            int nTo = nFrom;
            int nStep = 1;
            int deltaFrom = Integer.parseInt(deltaFieldFrom.getText());
            int deltaTo = deltaFrom;
            int deltaStep = 1;
            int x = Integer.parseInt(xFieldFrom.getText());

            String id = ((RadioButton) server.getSelectedToggle()).getId();
            Request.Builder builder = Request.newBuilder();
            String name = "";
            if (id.equals("simple")) {
                builder.setType(ServerSet.START_SIMPLE);
                name = "arch=simple";
            } else if (id.equals("read_write")) {
                builder.setType(ServerSet.START_READ_WRITE);
                name = "arch=read_write";
            } else if (id.equals("not_blocking")) {
                builder.setType(ServerSet.START_NOT_BLOCKING);
                name = "arch=not_blocking";
            }

            String parId = ((RadioButton) buttons.getSelectedToggle()).getId();
            if (parId.equals("n_button")) {
                nTo = Integer.parseInt(nFieldTo.getText());
                nStep = Integer.parseInt(nFieldStep.getText());
                xAxis.setId("N");
                builder.setLimit(((nTo - nFrom) / nStep + 1) * mFrom * x);
                name = name + "_n_changing_from_" + nFrom + "_to_" + nTo + "_step=" + nStep + "_m=" + mFrom + "_delta=" + deltaFrom + "_x=" + x;
            }  else if (parId.equals("m_button")) {
                mTo = Integer.parseInt(mFieldTo.getText());
                mStep = Integer.parseInt(mFieldStep.getText());
                xAxis.setId("M");
                int mCount = (mTo - mFrom) / mStep + 1;
                mTo = mFrom + (mCount - 1) * mStep;
                builder.setLimit(((mFrom + mTo) * mCount / 2) * x);
                name = name + "_m_changing_from_" + mFrom + "_to_" + mTo + "_step=" + mStep + "_n=" + nFrom + "_delta=" + deltaFrom + "_x=" + x;
            } else if (parId.equals("delta_button")) {
                deltaTo = Integer.parseInt(deltaFieldTo.getText());
                deltaStep = Integer.parseInt(deltaFieldStep.getText());
                xAxis.setId("Delta");
                builder.setLimit(((deltaTo - deltaFrom) / deltaStep + 1) * mFrom * x);
                name = name + "_delta_changing_from_" + deltaFrom + "_to_" + deltaTo + "_step=" + deltaStep + "_m=" + mFrom + "_n=" + nFrom + "_x=" + x;
            }

            File dataFile = new File("data" + File.separator + name + ".dat");
            File graphFile = new File("graphs" + File.separator + name + ".png");
            dataFile.getParentFile().mkdirs();
            graphFile.getParentFile().mkdirs();
            PrintWriter out;

            try {
                out = new PrintWriter(dataFile);
            } catch (FileNotFoundException e) {
                return;
            }

            try {
                Socket socket = new Socket(ip, 60000);
                builder.build().writeDelimitedTo(socket.getOutputStream());
                Request.parseDelimitedFrom(socket.getInputStream());
                socket.close();
            } catch (IOException e) {
                return;
            }

            Client.Metrics metrics;
            for (int n = nFrom; n <= nTo; n += nStep) {
                for (int m = mFrom; m <= mTo; m += mStep) {
                    for (int delta = deltaFrom; delta <= deltaTo; delta += deltaStep) {
                        metrics = Client.runAll(n, m, delta, x);
                        int changing = -1;
                        if (parId.equals("n_button")) {
                            changing = n;
                        } else if (parId.equals("m_button")) {
                            changing = m;
                        } else if (parId.equals("delta_button")) {
                            changing = delta;
                        }
                        series1.getData().add(new XYChart.Data<>(changing, metrics.getM1()));
                        series2.getData().add(new XYChart.Data<>(changing, metrics.getM2()));
                        series3.getData().add(new XYChart.Data<>(changing, metrics.getM3()));
                        System.out.println(metrics.getM1() + " " + metrics.getM2() + " " + metrics.getM3());
                        out.println(metrics.getM1() + " " + metrics.getM2() + " " + metrics.getM3());
                    }
                }
            }

            out.close();

            Scene scene1 = new Scene(chart, 800, 600);
            chart.setAnimated(false);
            chart.getData().add(series1);
            chart.getData().add(series2);
            chart.getData().add(series3);
            primaryStage.setScene(scene1);
            WritableImage image = chart.snapshot(new SnapshotParameters(), null);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", graphFile);
            } catch (IOException ignored) {

            }
        });



    }

    public static void main(String[] args) {
        launch(args);
    }
}
