package com.darren.maze.core;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 */
public class SerializerTest {

    @Test
    public void test() {
        Maze maze = createMaze();
        String asString = Serializer.toString(maze);
        maze = Serializer.fromString(asString);
        assertThat(asString, is(equalTo(Serializer.toString(maze))));
    }


    public Maze createMaze() {
        Maze maze = new Maze(9,9);
        maze.makeRectanglePath(new Point(1,0), new Point(1,7));
        maze.makeRectanglePath(new Point(1,1), new Point(3,1));
        maze.makeRectanglePath(new Point(5,1), new Point(7,1));
        maze.makeRectanglePath(new Point(3,3), new Point(3,5));
        maze.makeRectanglePath(new Point(1,5), new Point(3,5));
        maze.makeRectanglePath(new Point(1,7), new Point(3,7));
        maze.makeRectanglePath(new Point(5,1), new Point(5,7));
        maze.makeRectanglePath(new Point(5,7), new Point(7,7));
        maze.makeRectanglePath(new Point(7,5), new Point(7,8));
        maze.makeRectanglePath(new Point(7, 1), new Point(7, 3));
        maze.makeRectanglePath(new Point(4, 3), new Point(4, 3));
        return maze;
    }
}
