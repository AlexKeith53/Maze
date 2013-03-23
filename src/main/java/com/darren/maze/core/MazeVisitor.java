package com.darren.maze.core;

/**
 */
public interface MazeVisitor {

    void enterPath(Point point);

    void exitPath(Point point);

}
