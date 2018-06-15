package com.github.myutman.java;


import com.github.myutman.java.MyArrayProtos.MyArray;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Client {

    public static class Metrics {
        private double m1;
        private double m2;
        private double m3;

        public Metrics(double m1, double m2, double m3) {
            this.m1 = m1;
            this.m2 = m2;
            this.m3 = m3;
        }

        public double getM1() {
            return m1;
        }

        public double getM2() {
            return m2;
        }

        public double getM3() {
            return m3;
        }

        public void setM3(double m3) {
            this.m3 = m3;
        }

        public void setM2(double m2) {
            this.m2 = m2;
        }

        public void setM1(double m1) {
            this.m1 = m1;
        }
    }

    public static Metrics run(int n, int delta, int x, String ip) {
        long start = System.currentTimeMillis();
        long sm1 = 0;
        long sm2 = 0;
        for (int j = 0; j < x; j++) {
            try (Socket socket = new Socket(ip, 55555);
                 InputStream inputStream = socket.getInputStream();
                 OutputStream outputStream = socket.getOutputStream()) {
                MyArray.Builder builder = MyArray.newBuilder();
                builder.setSize(n);
                for (int i = n; i > 0; i--) {
                    builder.addBase(i);
                }
                MyArray array = builder.build();
                array.writeDelimitedTo(outputStream);
                array = MyArray.parseDelimitedFrom(inputStream);
                sm1 += array.getQueryTime();
                sm2 += array.getClientTime();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(delta);
            } catch (InterruptedException ignored) {

            }
        }
        return new Metrics(sm1 / (double) x, sm2 / (double) x, (System.currentTimeMillis() - start) / (double) x);
    }

    public static Metrics runAll(int n, int m, int delta, int x, String ip) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        ArrayList<Thread> list = new ArrayList<>();
        Metrics metrics = new Metrics(0, 0, 0);
        for (int i = 0; i < m; i++) {
            Thread thread = new Thread(() -> {
                Metrics metrics1 = run(n, delta, x, ip);
                synchronized (metrics) {
                    metrics.setM1(metrics.getM1() + metrics1.getM1());
                    metrics.setM2(metrics.getM2() + metrics1.getM2());
                    metrics.setM3(metrics.getM3() + metrics1.getM3());
                }
            });
            list.add(thread);
            thread.start();
        }
        for (Thread thread: list) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {

            }
        }
        service.shutdown();
        return new Metrics(metrics.getM1() / (double) m, metrics.getM2() / (double) m, metrics.getM3() / (double) m);
    }
}
