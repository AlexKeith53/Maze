package com.darren.maze.io;

import com.darren.maze.core.Maze;
import com.google.common.base.Joiner;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class MazeWriter {
    public String toString(Maze maze) {
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

}
