package cphbusiness.ufo.letterfrequencies;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * Frequency analysis Inspired by
 * https://en.wikipedia.org/wiki/Frequency_analysis
 *
 * @author kasper
 */

public class Main {
    static class Timer {
        private long start, spent = 0;

        public Timer() {
            play();
        }

        public double check() {
            return (System.nanoTime() - start + spent) / 1e9;
        }

        public void pause() {
            spent += System.nanoTime() - start;
        }

        public void play() {
            start = System.nanoTime();
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

//        orginalprint();
//
//        System.out.println("***************");
//        orginal2();

        Mark5();

    }

    private static void orginal() throws IOException {
        String fileName = "D:\\DatSoft2021Spring\\UFO\\letterfrequencies\\src\\main\\resources\\FoundationSeries.txt";
        Reader reader = new FileReader(fileName);
        HashMap<Integer, Long> freq = new HashMap<>();
        tallyChars(reader, freq);
    }

    private static void orginal2() throws IOException {
        String fileName = "D:\\DatSoft2021Spring\\UFO\\letterfrequencies\\src\\main\\resources\\FoundationSeries.txt";

        Path filepath = Paths.get(fileName);

        Map<Integer, Long> freq =
                Files.lines(filepath, Charset.forName("UTF-8"))
                        .flatMapToInt(String::chars)
                        .boxed()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


    }

    private static void orginalprint() throws IOException {
        String fileName = "D:\\DatSoft2021Spring\\UFO\\letterfrequencies\\src\\main\\resources\\FoundationSeries.txt";
        Reader reader = new FileReader(fileName);
        HashMap<Integer, Long> freq = new HashMap<>();
        tallyChars(reader, freq);
        print_tally(freq);
    }


    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
        int b;
        while ((b = reader.read()) != -1) {
            try {
                freq.put(b, freq.get(b) + 1);
            } catch (NullPointerException np) {
                freq.put(b, 1L);
            }
        }
    }

//    private static void tallyCharsv1(Reader reader, Map<Integer, Long> freq) throws IOException {
//
//
//        while ((b = reader.read()) != -1) {
//            freq.put(b, freq.getOrDefault(b, 0L) + 1);
//        }
//    }

    private static void print_tally(Map<Integer, Long> freq) {
        int dist = 'a' - 'A';
        Map<Character, Long> upperAndlower = new LinkedHashMap();
        for (Character c = 'A'; c <= 'Z'; c++) {
            upperAndlower.put(c, freq.getOrDefault(c, 0L) + freq.getOrDefault(c + dist, 0L));
        }
        Map<Character, Long> sorted = upperAndlower
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        for (Character c : sorted.keySet()) {
            System.out.println("" + c + ": " + sorted.get(c));

        }
    }


    public static void Mark5() throws IOException {
        int n = 10, count = 1, totalCount = 0;
        //HashMap<Integer, Long> dummy = null;
        double runningTime = 0.0;

        do {
            count *= 2;
            double st = 0.0, sst = 0.0;
            for (int j = 0; j < n; j++) {
                Timer t = new Timer();
                for (int i = 0; i < count; i++) {


                    orginal2();
                    //dummy = tallyChars();
                    //dummy += multiply(i);
                }
                runningTime = t.check();
                double time = runningTime * 1e9 / count;
                st += time;
                sst += time * time;
                totalCount += count;
            }
            double mean = st / n, sdev = Math.sqrt((sst - mean * mean * n) / (n - 1));
            System.out.printf("%6.1f ns +/- %8.2f %10d%n", mean, sdev, count);
        } while (runningTime < 2 && count < Integer.MAX_VALUE / 2);


        //return dummy;
    }


}
