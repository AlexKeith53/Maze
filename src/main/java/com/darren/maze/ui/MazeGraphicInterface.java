package com.darren.maze.ui;

import com.darren.maze.core.Maze;
import com.darren.maze.core.Point;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 */
public class MazeGraphicInterface {
    private Maze maze;
    private Screen screen;
    private int blockSize;

    public MazeGraphicInterface(Maze maze, Screen screen, int blockSize) {
        this.maze = checkNotNull(maze);
        this.screen = checkNotNull(screen);
        this.blockSize = blockSize;
        checkArgument(blockSize > 0, "Block size must be greater than 0");
    }

    public void mouseClick(Point pointOnTheScreen, Maze.Area area) {
        Point pointInTheMaze = pointInMaze(pointOnTheScreen);
        maze.makeRectangle(pointInTheMaze, pointInTheMaze, area);
        screen.drawRectangle(pointInTheMaze.multiply(blockSize), pointInTheMaze.incrementXY().multiply(blockSize), area);
    }

    private Point pointInMaze(Point pointOnTheScreen) {
        int mazeX = pointOnTheScreen.getX()/blockSize;
        int mazeY = pointOnTheScreen.getY()/blockSize;
        return new Point(mazeX, mazeY);
    }

    public int getWidth() {
        return maze.getWidth() * blockSize;
    }

    public int getHeight() {
        return maze.getHeight() * blockSize;
    }
}
