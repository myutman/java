package com.github.myutman.java;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class FirstPartTasks {

    private FirstPartTasks() {}

    // Список названий альбомов
    public static List<String> allNames(Stream<Album> albums) {
        return albums.map(Album::getName).collect(Collectors.toList());
    }

    // Список названий альбомов, отсортированный лексикографически по названию
    public static List<String> allNamesSorted(Stream<Album> albums) {
        return albums.map(Album::getName).sorted().collect(Collectors.toList());
    }

    // Список треков, отсортированный лексикографически по названию, включающий все треки альбомов из 'albums'
    public static List<String> allTracksSorted(Stream<Album> albums) {
        return albums.map(Album::getTracks).flatMap(List::stream).map(Track::getName).sorted().collect(Collectors.toList());
    }

    // Список альбомов, в которых есть хотя бы один трек с рейтингом более 95, отсортированный по названию
    public static List<Album> sortedFavorites(Stream<Album> albums) {
        return albums.filter(x -> x.getTracks().stream().anyMatch(y -> y.getRating() > 95)).sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
    }

    // Сгруппировать альбомы по артистам
    public static Map<Artist, List<Album>> groupByArtist(Stream<Album> albums) {
        Map<Artist, List<Album>> res = new HashMap<>();
        albums.forEach(x -> {
            if (res.get(x.getArtist()) == null)
                res.put(x.getArtist(), new ArrayList<>());
            res.get(x.getArtist()).add(x);
        });
        return res;
    }

    // Сгруппировать альбомы по артистам (в качестве значения вместо объекта 'Album' использовать его имя)
    public static Map<Artist, List<String>> groupByArtistMapName(Stream<Album> albums) {
        Map<Artist, List<String>> res = new HashMap<>();
        albums.forEach(x -> {
            if (res.get(x.getArtist()) == null)
                res.put(x.getArtist(), new ArrayList<>());
            res.get(x.getArtist()).add(x.getName());
        });
        return res;
    }

    // Число повторяющихся альбомов в потоке
    public static long countAlbumDuplicates(Stream<Album> albums) {
        final Album[] prev = {null};
        return albums.sorted(((o1, o2) -> o1.getName().compareTo(o2.getName()))).filter(x -> {
            boolean b = x.equals(prev[0]);
            prev[0] = x;
            return b;
        }).distinct().count();
    }

    // Альбом, в котором максимум рейтинга минимален
    // (если в альбоме нет ни одного трека, считать, что максимум рейтинга в нем --- 0)
    public static Optional<Album> minMaxRating(Stream<Album> albums) {
        Function<List<Track>, Integer> fun = x -> x.isEmpty() ? 0 : x.stream().map(Track::getRating).max((y, z) -> y.compareTo(z)).get();
        return albums.min((o1, o2) -> fun.apply(o1.getTracks()).compareTo(fun.apply(o2.getTracks())));
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    public static List<Album> sortByAverageRating(Stream<Album> albums) {
        Function<List<Track>, Integer> fun = x -> x.stream().map(Track::getRating).flatMap((x, y) -> (Integer) ((int) x + y));
        albums.sorted((o1, o2) -> );
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(IntStream stream, int modulo) {
        throw new UnsupportedOperationException();
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    public static String joinTo(String... strings) {
        throw new UnsupportedOperationException();
    }

    // Вернуть поток из объектов класса 'clazz'
    public static <R> Stream<R> filterIsInstance(Stream<?> s, Class<R> clazz) {
        throw new UnsupportedOperationException();
    }
}
