package com.dallas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String fileName = "D:\\HelloWorld\\src\\Input.txt";
        ArrayList<String> lines = GetDataFromFile(fileName);
        for (String item: lines
             ) {
            GetDataFromLine(item);
        }
        System.out.print(lines);
    }

    private static ArrayList<String> GetDataFromFile(String fileName)
    {
        ArrayList<String> lines = new ArrayList<>();

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            // stream.forEach(System.out::println);

            stream.forEach(item -> lines.add(item));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;

    }

    private static ArrayList<String> GetDataFromLine(String line)
    {
        ArrayList<String> lines = new ArrayList<>();

        if(line.isEmpty())
        {
            return lines;
        }

        lines = new ArrayList<String>(Arrays.asList(line.split("->")));

        return lines;

    }
}
