package com.darren.maze.core;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 */
public class Serializer {
    private static final String HEADER_SEPARATOR ="#";
    private static final String ROW_SEPARATOR = "|";
    private static final String VALUE_SEPARATOR = ",";

    public static String toString(Maze maze) {
        StringBuilder result = new StringBuilder();
        result.append(maze.getHeight()).append(Serializer.VALUE_SEPARATOR).append(maze.getWidth());
        result.append(Serializer.HEADER_SEPARATOR);
        List<String> data = new ArrayList<String>();
        for(int j = 0; j < maze.getHeight(); j++) {
            List<String> rowData = new ArrayList<String>();
            for(int i = 0; i < maze.getWidth(); i++) {
                if(maze.getArea(i, j) == Maze.Area.PATH) {
                    rowData.add(String.valueOf(i));
                }
            }
            data.add(Joiner.on(Serializer.VALUE_SEPARATOR).join(rowData));
        }
        result.append(Joiner.on(Serializer.ROW_SEPARATOR).join(data));
        return result.toString();
    }

    public static Maze fromString(String mazeData) {
        Iterator<String> iterable = Splitter.on(HEADER_SEPARATOR).split(mazeData).iterator();
        String header =  iterable.next();
        String data = iterable.next();
        Iterator<String> headerData = Splitter.on(VALUE_SEPARATOR).split(header).iterator();
        int width = Integer.parseInt(headerData.next());
        int height = Integer.parseInt(headerData.next());
        Maze maze = new Maze(width, height);
        Iterable<String> graphData = Splitter.on(ROW_SEPARATOR).split(data);
        int y = 0;
        for(String row: graphData) {
            if(row.isEmpty()) {
                continue;
            }
            Iterable<String> column = Splitter.on(VALUE_SEPARATOR).split(row);
            for(String x: column) {
                Point pathPoint = new Point(Integer.parseInt(x), y);
                maze.makeRectanglePath(pathPoint, pathPoint);
            }
            ++y;
        }
        return  maze;
    }
}
