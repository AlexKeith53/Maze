package com.darren.maze.swing;

import com.darren.maze.core.MazeVisitor;
import com.darren.maze.core.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: darrenm
 * Date: 3/23/13
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class AnimatedSolutionVisitor implements MazeVisitor {
    JPanelScreen jPanelScreen;

    public AnimatedSolutionVisitor(JPanelScreen jPanelScreen) {
        this.jPanelScreen = jPanelScreen;
    }

    public void enterPath(Point point) {
        jPanelScreen.setSolutions(Arrays.asList(point));
        jPanelScreen.repaint();
        try {
            Thread.sleep(50);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void exitPath(Point point) {
    }

}
