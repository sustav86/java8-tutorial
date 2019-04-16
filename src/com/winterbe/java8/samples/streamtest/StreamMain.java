package com.winterbe.java8.samples.streamtest;

import java.io.Serializable;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamMain {
    public static void main(String[] args) {
        Stream<Object> empty = Stream.empty();
        Stream<String> emptyString = Stream.empty();

        List<String> objects = new ArrayList<>() {
            {
                add("a"); add("b"); add("c");
            }};
        List<Integer> integerList = Arrays.asList(10, 30, 59);
        List<Double> doubles = List.of(3.5, 6.7, 8.9);

        List<Integer> collect = integerList.parallelStream().filter(integer -> integer > 30).collect(Collectors.toList());

//        int sum = IntStream.range(1, 20).filter(x -> x >= 6).map(operand -> operand + 5).limit(3).sum();
//        System.out.println(sum);
//
//        Stream.of("dfdfdf", 44, 56).filter(x -> !((Serializable) x).equals(4)).forEach(System.out::println);
//
//        String str = Math.random() > 0.5 ? "I'm feeling lucky" : null;
//        Stream.ofNullable(str)
//                .forEach(System.out::println);
//
//        Stream.generate(() -> 7).limit(50).forEach(System.out::println);
//
//        Stream.iterate(1, x -> x * 2)
//                .limit(6)
//                .forEach(System.out::println);
//
//        Stream.iterate(4, x -> x < 100, x -> x * 4)
//                .forEach(System.out::println);

//        Stream<? extends Serializable> concat = Stream.concat(Stream.of("dfdfdf", "dfddff"), Stream.of(34, 56));
//        concat.forEach(System.out::println);
//
//        Stream.Builder<Object> builder = Stream.builder();
//        builder.add(23).add(56).add(34).build().forEach(System.out::println);

//        Stream.of("10", "11", "32")
//                        .map(x -> Integer.parseInt(x, 16))
//                        .forEach(System.out::println);

//        IntStream.range(0, 100000000)
//                .sorted()
//                .limit(3)
//                .forEach(System.out::println);
//
//        Stream.of(120, 410, 85, 32, 314, 12)
//                .sorted(Comparator.reverseOrder())
//                .forEach(System.out::println);

//        IntStream.range(0, 100000)
//                .parallel()
//                .filter(x -> x % 10000 == 0)
//                .map(x -> x / 10000)
//                .forEach(System.out::println);
//
//        IntStream.range(0, 100000)
//                .parallel()
//                .filter(x -> x % 10000 == 0)
//                .map(x -> x / 10000)
//                .forEachOrdered(System.out::println);

//        List<String> list = Stream.of("a", "b", "c", "d")
//                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

//        int sum = IntStream.of(2, 4, 6, 8)
//                .reduce(5, (acc, x) -> acc + x);
//        System.out.println(sum);
//
//        Stream<Integer> integerStream = Stream.of(3, 5, 7);

//        String str = null;
//        String s = Objects.requireNonNull(str);
//        System.out.println(s);

//        IntStream.concat(
//                IntStream.range(2, 6),
//                IntStream.rangeClosed(-1, 2))
//                .forEach(System.out::println);

//        IntStream.range(5, 30)
//                .limit(12)
//                .skip(3)
//                .limit(6)
//                .skip(2)
//                .forEach(System.out::println);

//        IntStream.range(0, 10)
//                .skip(2)
//                .dropWhile(x -> x < 5)
//                .limit(3)
//                .forEach(System.out::println);

//        IntStream.range(0, 10)
//                .skip(3)
//                .takeWhile(x -> x < 5)
//                .limit(3)
//                .forEach(System.out::println);

//        IntStream.range(1, 5)
//                .flatMap(i -> IntStream.generate(() -> i).limit(i))
//                .forEach(System.out::println);

//        IntStream.range(-2, 2).forEach(System.out::println);
//        IntStream.range(-2, 2).map(i -> i + i).forEach(System.out::println);
//        int x = IntStream.range(-2, 2)
//                .map(i -> i * 5)
//                .reduce(10, Integer::sum);
//        System.out.println(x);

//        IntStream.range(0, 10)
//                .boxed()
//                .collect(Collectors.groupingBy(i -> i % 2 == 0))
//        .entrySet().forEach(System.out::println);

//        IntStream.range(-5, 0)
//                .flatMap(i -> IntStream.of(i, -1 * i))
//                .sorted()
//                .forEach(System.out::println);

//        IntStream.range(-5, 0)
//                .flatMap(i -> IntStream.of(i, -1 * i))
//                .boxed()
//                .sorted(Comparator.comparing(Math::abs))
//                .forEach(System.out::println);

        IntStream.range(1, 5)
                .flatMap(i -> IntStream.generate(() -> i).limit(i))
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().forEach(System.out::println);
    }

    public static <T> Collector<T, ?, Map<Boolean, List<T>>> partitioningByUniqueness() {

        Collector<T, Map.Entry<List<T>, Set<T>>, Map<Boolean, List<T>>> entrySupplier = Collector.<T, Map.Entry<List<T>, Set<T>>, Map<Boolean, List<T>>>of(
                () -> new AbstractMap.SimpleImmutableEntry<>(new ArrayList<T>(), new LinkedHashSet<T>()),
                (c, e) -> {
                    if (!c.getValue().add(e)) {
                        c.getKey().add(e);
                    }
                },
                (c1, c2) -> {
                    c1.getKey().addAll(c2.getKey());
                    for (T e : c2.getValue()) {
                        if (!c1.getValue().add(e)) {
                            c1.getKey().add(e);
                        }
                    }
                    return c1;
                },
                c -> {
                    Map<Boolean, List<T>> result = new HashMap<>(2);
                    result.put(Boolean.FALSE, c.getKey());
                    result.put(Boolean.TRUE, new ArrayList<>(c.getValue()));
                    return result;
                });

        return entrySupplier;
    }

}
