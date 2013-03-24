package com.darren.maze.io;

import com.darren.maze.core.Maze;
import com.darren.maze.core.Serializer;

import java.io.*;

/**
 */
public class MazeIO {

    public void writeMaze(Maze maze, OutputStream os) throws IOException {
        write(Serializer.toString(maze), os);
    }

    public Maze readMaze(InputStream inputStream) throws IOException {
        return Serializer.fromString(read(inputStream));
    }

    public void write(String serializedMaze, OutputStream outputStream) throws IOException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream));
        pw.println(serializedMaze);
        pw.close();
    }

    public String read(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String serializedMaze = br.readLine();
        br.close();
        return serializedMaze;
    }
}
