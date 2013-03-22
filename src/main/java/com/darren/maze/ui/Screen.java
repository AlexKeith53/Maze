package com.darren.maze.ui;

import com.darren.maze.Maze;
import com.darren.maze.Point;

/**
 */
public interface Screen {

    void drawRectangle(Point startPoint, Point endPoint, Maze.Area area);
}
