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
    }

    public static Metrics run(int n, int delta, int x) {
        long start = System.currentTimeMillis();
        long sm1 = 0;
        long sm2 = 0;
        for (int j = 0; j < x; j++) {
            try (Socket socket = new Socket("127.0.0.1", 55555);
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

    public static Metrics runAll(int n, int m, int delta, int x) {
        ExecutorService service = Executors.newCachedThreadPool();
        ArrayList<Future<Metrics>> list = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            list.add(service.submit(() -> run(n, delta, x)));
        }
        double sm1 = 0;
        double sm2 = 0;
        double sm3 = 0;
        for (Future<Metrics> future: list) {
            try {
                Metrics metrics = future.get();
                sm1 += metrics.getM1();
                sm2 += metrics.getM2();
                sm3 += metrics.getM3();
            } catch (InterruptedException | ExecutionException e) {
                return new Metrics(0, 0, 0);
            }
        }
        service.shutdown();
        return new Metrics(sm1 / (double) m, sm2 / (double) m, sm3 / (double) m);
    }

    public static void main(String[] args) {
        int n = 10000;//Integer.parseInt(args[0]);
        int m = 5;//Integer.parseInt(args[1]);
        int delta = 5;//Integer.parseInt(args[2]);
        int x = 2;//Integer.parseInt(args[3]);
        Metrics metrics = runAll(n, m, delta, x);
        System.out.println(metrics.getM1() + " " + metrics.getM2() + " " + metrics.getM3());
    }
}
