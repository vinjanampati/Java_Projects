package com.dallas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Main {


    public static void main(String[] args) {
        // write your code here
        String fileName = "D:\\Java_Projects\\FirstProject\\src\\Input.txt";
        ArrayList<String> lines = GetDataFromFile(fileName);

        TreeMap<String, com.dallas.Node> nodeCollection = new TreeMap<String, com.dallas.Node>();

        for (String item: lines)
        {
            List<String> lineItem = GetDataFromLine(item);
            AddLineItem(lineItem, nodeCollection);
        }
        //System.out.print(lines);

        Display(nodeCollection);
    }

    private static void Display(TreeMap<String, Node> nodeCollection)
    {
        for(Map.Entry<String, Node> entry : nodeCollection.entrySet())
        {
            HashSet<String> visitedNodes = new HashSet<>();
            visitedNodes.add(entry.getKey());
            System.out.println(entry.getValue().getNodeName());
            System.out.println("|");

            for (String dependentNodeString : entry.getValue().getDependencyNodes())
            {
                if(nodeCollection.containsKey(dependentNodeString) && nodeCollection.get(dependentNodeString).getDependencyNodes().size() > 0) {
                    DisplayNodeRecursive(dependentNodeString, nodeCollection, visitedNodes);
                }
            }
        }
    }

    private static void DisplayNodeRecursive(String currentNode, TreeMap<String, com.dallas.Node> nodeCollection, HashSet<String> visitedNodes)
    {
        System.out.println("-" + currentNode);

        if(visitedNodes.contains(currentNode))
        {
            return;
        }



        if(nodeCollection.containsKey(currentNode))
        {
            for (String dependentNodeString : nodeCollection.get(currentNode).getDependencyNodes())
            {
                System.out.print("-" + dependentNodeString);
                System.out.println("|");

                if(visitedNodes.contains(dependentNodeString))
                {
                    return;
                }

                visitedNodes.add((dependentNodeString));
                if(nodeCollection.containsKey(dependentNodeString)) {
                    com.dallas.Node dependantNode = nodeCollection.get(dependentNodeString);
                    for(String newCurrentNode : dependantNode.getDependencyNodes())
                    {
                        DisplayNodeRecursive(newCurrentNode, nodeCollection, visitedNodes);
                    }
                }
            }
        }


    }






    private static void AddLineItem(List<String> lineItem, TreeMap<String, com.dallas.Node> nodeCollection) {

        String leftItem = lineItem.get(0).trim();
        String rightItem = lineItem.get(1).trim();

        if(nodeCollection.containsKey(leftItem))
        {
            Node currentNode = nodeCollection.get(leftItem);
            currentNode.getDependencyNodes().add(rightItem);
        }
        else
        {
            Node node = new Node();
            node.setNodeName(leftItem);
            List<String> dependencyNodes = new ArrayList<>();
            dependencyNodes.add(rightItem);
            node.setDependencyNodes(dependencyNodes);
            nodeCollection.put(leftItem, node);
        }
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
