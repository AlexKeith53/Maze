package com.darren.maze.core;

import com.darren.maze.core.Maze;
import com.darren.maze.core.Point;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 */
public class MazeTest {

    @Test
    public void test() {
        Maze maze = createMaze();
        for(int j = 0; j < maze.getHeight(); j++) {
            StringBuilder line = new StringBuilder();
            for(int i = 0; i < maze.getWidth(); i++) {
               line.append(maze.getArea(i,j) == Maze.Area.WALL? "#" : " ");
            }
            System.out.println(line.toString());
        }
    }

    @Test
    public void testMazeIsValid() {
        assertThat(createMaze().getStatus().isValid(), is(equalTo(true)));
    }

    @Test
    public void testSolve() {
        List<Point> path = createMaze().solve();
        assertThat(path.get(path.size()-1), is(equalTo(new Point(7,8))));
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
