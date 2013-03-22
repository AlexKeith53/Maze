package com.darren.maze.ui;

import com.darren.maze.Maze;
import com.darren.maze.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import java.util.List;

/**
 */
public class JPanelScreen extends JPanel implements Screen {
    private MazeGraphicInterface mazeGraphicInterface;
    private Maze maze;
    private int blockSize;
    private Configuration configuration;
    private java.util.List<Point> solution = new ArrayList<Point>();

    public JPanelScreen(Maze maze, int blockSize, Configuration configuration) {
        mazeGraphicInterface = new MazeGraphicInterface(maze, this, blockSize);
        this.maze = maze;
        this.blockSize = blockSize;
        initialize(mazeGraphicInterface.getWidth(), mazeGraphicInterface.getHeight());
        this.configuration = configuration;
    }

    public void drawRectangle(Point startPoint, Point endPoint, Maze.Area area) {
        repaint();
    }

    public void setSolution(List<Point> solution) {
        this.solution = solution;
    }

    private Color getColor(Maze.Area area) {
        if(area == Maze.Area.PATH) {
            return  Color.WHITE;
        }
        return new Color(148,147,163);
    }

    private void initialize(int width, int height) {
        setPreferredSize(new Dimension(width,height));
        setBackground(Color.WHITE);
        addListeners();
    }

    private void addListeners() {
        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                mazeGraphicInterface.mouseClick(new Point(evt.getX(), evt.getY()), configuration.drawPath() ? Maze.Area.PATH : Maze.Area.WALL);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                mazeGraphicInterface.mouseClick(new Point(evt.getX(), evt.getY()), configuration.drawPath() ? Maze.Area.PATH : Maze.Area.WALL);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getColor(Maze.Area.WALL));
        g.fillRect(0, 0, mazeGraphicInterface.getWidth(), mazeGraphicInterface.getHeight());
        drawPath(g);
        showSolution(g);
    }

    private void drawPath(Graphics g) {
        g.setColor(getColor(Maze.Area.PATH));
        for(int i = 0; i < maze.getWidth(); i++) {
            for(int j = 0; j < maze.getHeight(); j++) {
                if(maze.getArea(i,j) == Maze.Area.PATH) {
                    drawBlock(g, new Point(i, j));
                }
            }
        }
    }

    private void drawBlock(Graphics g, Point mazePoint) {
        Point startPoint = mazePoint.multiply(blockSize);
        Point endPoint = mazePoint.incrementXY().multiply(blockSize);
        g.fillRect(startPoint.getX(), startPoint.getY(), startPoint.horizontalDistance(endPoint), startPoint.verticalDistance(endPoint));
    }

    private void showSolution(Graphics g) {
        Point lastPoint;
        for(Point point: solution) {
            lastPoint = point.multiply(blockSize);
            g.setColor(Color.black);
            System.out.println(point);
            g.fillOval(lastPoint.getX(), lastPoint.getY(), blockSize, blockSize);
            try {
                //Thread.sleep(50);
                //g.setColor(getColor(Maze.Area.PATH));
                //g.fillOval(lastPoint.getX(), lastPoint.getY(), blockSize/2, blockSize/2);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }


        }
    }

}
