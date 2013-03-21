package com.darren.maze;

/**
 */
public interface MazeVisitor {

    void enterPath(Point point);

    void exitPath(Point point);

}
