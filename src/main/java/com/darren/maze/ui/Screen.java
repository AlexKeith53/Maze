package com.darren.maze.ui;

import com.darren.maze.core.Maze;
import com.darren.maze.core.Point;

/**
 */
public interface Screen {

    void drawRectangle(Point startPoint, Point endPoint, Maze.Area area);
}
