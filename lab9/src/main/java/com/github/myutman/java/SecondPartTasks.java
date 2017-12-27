package com.github.myutman.java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.DoublePredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SecondPartTasks {

    private SecondPartTasks() {}

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) throws IOException {
        return paths.stream().flatMap(path -> {
            try {
                return Files.lines(new File(path).toPath())
                        .flatMap(s -> Arrays.stream(s.split(" ")))
                        .filter(s -> s.contains(sequence))
                        .collect(Collectors.toList())
                        .stream();
            } catch (IOException e) {
                return Stream.empty();
            }
        }).collect(Collectors.toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        return new Random().doubles(0, 1)
                .limit(20000)
                .filter(new DoublePredicate() {

                    boolean b = false;
                    private double last;

                    @Override
                    public boolean test(double value) {
                        b = !b;
                        if (b) {
                            last = value;
                            return false;
                        }
                        return last * last + value * value < 1;
                    }
                })
                .count() / 10000.0;
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        return compositions.entrySet().stream()
                .max(Comparator
                .comparingInt(l -> (Integer) l.getValue().stream()
                        .mapToInt(String::length)
                        .sum()))
                .get().getKey();
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders.stream()
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)));
    }
}

