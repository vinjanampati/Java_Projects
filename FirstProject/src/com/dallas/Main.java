package com.dallas;

import java.io.Console;
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

        TreeMap<String, com.dallas.Node> nodeCollection = new TreeMap<>();

        for (String item: lines)
        {
            List<String> lineItem = GetDataFromLine(item);
            AddLineItem(lineItem, nodeCollection);
        }

        DisplayNew(nodeCollection);
    }

    private static void DisplayNew(TreeMap<String, Node> nodeCollection) {

        for (Map.Entry<String, Node> entry : nodeCollection.entrySet()) {
            HashSet<String> visitedNodes = new HashSet<>();
            visitedNodes.add(entry.getKey());
            Queue<DisplayNode> childrenWithDepth = new LinkedList<>();
            for (String dependentNodeString : entry.getValue().getDependencyNodes()) {

                int depth= 0;

                if (nodeCollection.containsKey(dependentNodeString) && nodeCollection.get(dependentNodeString).getDependencyNodes().size() > 0) {
                    AddDisplayNodesRecursive(depth, dependentNodeString, visitedNodes, nodeCollection, childrenWithDepth);
                } else {
                    DisplayNode displayNode = new DisplayNode();
                    displayNode.setNodeDepth(depth);
                    displayNode.setNodeName(dependentNodeString);
                    childrenWithDepth.add(displayNode);
                }
            }

            PrintDisplayNodesNew(entry.getKey(), childrenWithDepth);

            System.out.println("*********************************8");
        }

    }


    private static void PrintDisplayNodesNew(String key, Queue<DisplayNode> childrenWithDepth) {

        System.out.println(key);

        while(childrenWithDepth.iterator().hasNext())
        {
            DisplayNode currentNode = childrenWithDepth.remove();

            int currentDepth = currentNode.getNodeDepth();

            //String space = " ";
            //String parentLinkDepthSpace = String.join("", Collections.nCopies(leastParentDepth, space));
            //String currentDepthSpace = String.join("", Collections.nCopies(currentDepth - leastParentDepth, space));
            //String repeatedSpace = String.join("", Collections.nCopies(depthIndex - rootDepth, space));

            if(currentDepth == 0)
            {
                System.out.println("|_" + currentNode.getNodeName());
            }
            else {

                StringBuffer buffer = new StringBuffer();

                for(int i = 0; i <= currentDepth; i++)
                {
                    String currentSpace = " ";
                    buffer.append("|" + currentSpace);
                }

                buffer.append("_" + currentNode.getNodeName());

                System.out.println(buffer.toString());
            }

        }
    }



    private static void PrintDisplayNodes(String key, Queue<DisplayNode> childrenWithDepth) {

        System.out.println(key);
        int currentDepth = 0;
        int leastParentDepth = 0;
        while(childrenWithDepth.iterator().hasNext())
        {



            DisplayNode currentNode = childrenWithDepth.remove();

            currentDepth = currentNode.getNodeDepth();
            if(leastParentDepth == 0)
            {
                leastParentDepth = currentDepth;
            }
            else if(currentDepth < leastParentDepth)
            {
                leastParentDepth = currentDepth;
            }


            String currentNodeName = currentNode.getNodeName();

            String space = "  ";
            String parentLinkDepthSpace = String.join("", Collections.nCopies(leastParentDepth, space));
            String currentDepthSpace = String.join("", Collections.nCopies(currentDepth - leastParentDepth, space));
            //String repeatedSpace = String.join("", Collections.nCopies(depthIndex - rootDepth, space));

            if(currentDepth == 0)
            {
                System.out.println(currentDepthSpace + "|_" + currentNodeName);
            }
            else {

                if(currentDepth - leastParentDepth == 0) {
                    System.out.println("|" + parentLinkDepthSpace + "|" + currentDepthSpace + "_" + currentNodeName);
                }
                else
                {
                    System.out.println("|" + parentLinkDepthSpace + "|" + currentDepthSpace + "|_" + currentNodeName);
                }
            }

        }
    }

    private static void AddDisplayNodesRecursive(int currentDepth, String currentNode, HashSet<String> visitedNodes, TreeMap<String, Node> nodeCollection, Queue<DisplayNode> displayNodes)
    {

        if(visitedNodes.contains(currentNode))
        {
            DisplayNode displayNode = new DisplayNode();
            displayNode.setNodeDepth(currentDepth);
            displayNode.setNodeName(currentNode);
            displayNodes.add(displayNode);
            return;
        }


        DisplayNode displayNode = new DisplayNode();
        displayNode.setNodeDepth(currentDepth++);
        displayNode.setNodeName(currentNode);
        displayNodes.add(displayNode);
        visitedNodes.add(currentNode);

        if(nodeCollection.containsKey(currentNode)) {

            for (String childNodeString : nodeCollection.get(currentNode).getDependencyNodes()) {
                if (nodeCollection.containsKey(childNodeString) && nodeCollection.get(childNodeString).getDependencyNodes().size() > 0) {
                    AddDisplayNodesRecursive(currentDepth, childNodeString, visitedNodes, nodeCollection, displayNodes);
                } else {
                    DisplayNode nodeWithNoChildren = new DisplayNode();
                    nodeWithNoChildren.setNodeDepth(currentDepth);
                    nodeWithNoChildren.setNodeName(childNodeString);
                    displayNodes.add(nodeWithNoChildren);
                    visitedNodes.add(childNodeString);
                }
            }
        }


    }

    /*DisplayNewHierarchy(String root, Queue<String> currentQueue, TreeMap<String, Node> nodeCollection)
    {




    }*/

    private static void Display(TreeMap<String, Node> nodeCollection)
    {
        for(Map.Entry<String, Node> entry : nodeCollection.entrySet())
        {
            HashSet<String> visitedNodes = new HashSet<>();
            visitedNodes.add(entry.getKey());
            System.out.println(entry.getValue().getNodeName());


            for (String dependentNodeString : entry.getValue().getDependencyNodes()) {
                if (nodeCollection.containsKey(dependentNodeString) && nodeCollection.get(dependentNodeString).getDependencyNodes().size() > 0) {
                    DisplayNodeRecursive(0, 0, dependentNodeString, nodeCollection, visitedNodes);
                }
                else
                {
                    System.out.println("|_"+ dependentNodeString);
                }
            }


            System.out.println("******************");
        }
    }

    private static void DisplayNodeRecursive(int rootDepth ,int depthIndex, String currentNode, TreeMap<String, com.dallas.Node> nodeCollection, HashSet<String> visitedNodes)
    {
        String space = "  ";
        String rootDepthSpace = String.join("", Collections.nCopies(rootDepth, space));
        String repeatedSpace = String.join("", Collections.nCopies(depthIndex - rootDepth, space));




        if(depthIndex == 0)
        {
            System.out.println("|" + repeatedSpace + "_" + currentNode);
        }
        else

        {
            System.out.println("|" + repeatedSpace + "|_" + currentNode);
        }

        if(visitedNodes.contains(currentNode))
        {
            System.out.println("Already visited the node Root" + currentNode);
            return;
        }



        if(nodeCollection.containsKey(currentNode))
        {
            for (String dependentNodeString : nodeCollection.get(currentNode).getDependencyNodes())
            {
                depthIndex++;
                String repeatedSpaceChildNode = String.join("", Collections.nCopies(depthIndex, space));

                System.out.println("|" + repeatedSpaceChildNode + "|_" + dependentNodeString);


                if(visitedNodes.contains(dependentNodeString))
                {
                    continue;
                }

                visitedNodes.add((dependentNodeString));
                if(nodeCollection.containsKey(dependentNodeString)) {
                    com.dallas.Node dependantNode = nodeCollection.get(dependentNodeString);
                    rootDepth = depthIndex;
                    for(String newCurrentNode : dependantNode.getDependencyNodes())
                    {
                        depthIndex++;

                        DisplayNodeRecursive(rootDepth, depthIndex ,newCurrentNode, nodeCollection, visitedNodes);
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

        lines = new ArrayList<>(Arrays.asList(line.split("->")));

        return lines;

    }
}
