package com.darren.maze.io;

import com.darren.maze.core.Maze;
import com.darren.maze.core.Point;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 */
class MazeIO {
    private static final String HEADER_SEPARATOR ="#";
    private static final String ROW_SEPARATOR = "|";
    private static final String VALUE_SEPARATOR = ",";


    @VisibleForTesting
    String toString(Maze maze) {
        StringBuilder result = new StringBuilder();
        result.append(maze.getHeight()).append(MazeIO.VALUE_SEPARATOR).append(maze.getWidth());
        result.append(MazeIO.HEADER_SEPARATOR);
        List<String> data = new ArrayList<String>();
        for(int j = 0; j < maze.getHeight(); j++) {
            List<String> rowData = new ArrayList<String>();
            for(int i = 0; i < maze.getWidth(); i++) {
                if(maze.getArea(i, j) == Maze.Area.PATH) {
                    rowData.add(String.valueOf(i));
                }
            }
            data.add(Joiner.on(MazeIO.VALUE_SEPARATOR).join(rowData));
        }
        result.append(Joiner.on(MazeIO.ROW_SEPARATOR).join(data));
        return result.toString();
    }

    @VisibleForTesting
    Maze fromString(String mazeData) {
        Iterator<String> iterable = Splitter.on(HEADER_SEPARATOR).split(mazeData).iterator();
        String header =  iterable.next();
        String data = iterable.next();
        Iterator<String> headerData = Splitter.on(VALUE_SEPARATOR).split(header).iterator();
        int width = Integer.parseInt(headerData.next());
        int height = Integer.parseInt(headerData.next());
        Maze maze = new Maze(width, height);
        Iterable<String> graphData = Splitter.on(ROW_SEPARATOR).split(data);
        int x = 0;
        for(String y: graphData) {
            Point pathPoint = new Point(x, Integer.parseInt(y));
            maze.makeRectanglePath(pathPoint, pathPoint);
            ++x;
        }
        return  maze;
    }
}
