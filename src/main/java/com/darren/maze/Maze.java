package com.darren.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 */
public class Maze {
    public static enum Area {
        WALL,
        PATH
    }
    private Area[][] area;

    public Maze(int width, int height) {
        initialize(width, height);
    }

    private void initialize(int width, int height) {
        checkArgument(width > 0, "Width needs to be greater than 0");
        checkArgument(height > 0, "Height needs to be greater than 0");
        this.area = new Area[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                area[i][j] = Area.WALL;
            }
        }
    }

    public void makeRectanglePath(Point startPoint, Point endPoint) {
        makeRectangle(startPoint, endPoint, Area.PATH);
    }

    public void makeRectangleWall(Point startPoint, Point endPoint) {
        makeRectangle(startPoint, endPoint, Area.WALL);
    }

    public void makeRectangle(Point startPoint, Point endPoint, Area area) {
        for(int i = startPoint.getX(); i <= endPoint.getX(); i++) {
            for(int j = startPoint.getY(); j <= endPoint.getY(); j++) {
                this.area[i][j] = area;
            }
        }
    }

    public int getHeight() {
        return area.length;
    }

    public int getWidth() {
        return area[0].length;
    }

    Area getArea(int x, int y) {
        return area[x][y];
    }


    public MazeStatus getStatus() {
        int entrance = getPathsInRow(0).size();
        int exists = getPathsInRow(area[0].length -1).size();
        if(entrance != 1 || exists != 1) {
            return new MazeStatus(false,"A maze must consists of only one entrance and one exist");
        }
        return new MazeStatus(true, "");
    }

    private List<Point> getPathsInRow(int rowIndex) {
        List<Point> result = new ArrayList<Point>();
        for(int x = 0; x < area.length; x++) {
            if(area[x][rowIndex] == Area.PATH) {
                result.add(new Point(x, rowIndex));
            }
        }
        return result;
    }


    public List<Point> solve(MazeVisitor visitor) {
        checkState(getStatus().isValid(),"Maze is not valid. Please check getStatus()");
        Stack<Point> path = new Stack<Point>();
        solve(visitor, path, entrance());
        System.out.println(Arrays.asList(path.toArray(new Point[path.size()])));
        if(path.isEmpty() || !path.peek().equals(exit())) {
            throw new IllegalStateException("Maze does not have an exist");
        }
        return Arrays.asList(path.toArray(new Point[path.size()]));
    }

    private void solve(MazeVisitor visitor, Stack<Point> path, Point lastPointVisited) {
        path.push(lastPointVisited);
        visitor.enterPath(lastPointVisited);
        if(isSolved(path)) {
            return;
        }
        if(pathAvailableOnLeft(lastPointVisited) && !path.contains(lastPointVisited.leftPoint())) {
            solve(visitor, path, lastPointVisited.leftPoint());
        }
        if(pathAvailableOnRight(lastPointVisited) && !path.contains(lastPointVisited.rightPoint())) {
            solve(visitor, path, lastPointVisited.rightPoint());
        }
        if(pathAvailableOnTop(lastPointVisited) && !path.contains(lastPointVisited.upPoint())) {
            solve(visitor, path, lastPointVisited.upPoint());
        }
        if(pathAvailableDown(lastPointVisited) && !path.contains(lastPointVisited.downPoint())) {
            solve(visitor, path, lastPointVisited.downPoint());
        }
        if(!isSolved(path)) {
            Point point = path.pop();
            visitor.exitPath(point);
        }
    }

    private boolean isSolved(Stack path) {
        return !path.isEmpty() && path.peek().equals(exit());
    }

    private Point entrance() {
        return getPathsInRow(0).get(0);
    }

    private Point exit() {
        return getPathsInRow(area[0].length -1).get(0);
    }

    private boolean canMoveLeft(Point point) {
        return point.getX() > 0;
    }

    private boolean canMoveRight(Point point) {
        return point.getX() < area.length-1;
    }

    private boolean canMoveUp(Point point) {
        return point.getY() > 0;
    }

    private boolean canMoveDown(Point point) {
        return point.getY() < area[0].length -1;
    }

    private boolean pathAvailableOnLeft(Point point) {
        return canMoveLeft(point) && getArea(point.leftPoint()) == Area.PATH;
    }

    private boolean pathAvailableOnRight(Point point) {
        return canMoveRight(point) && getArea(point.rightPoint()) == Area.PATH;
    }

    private boolean pathAvailableOnTop(Point point) {
        return canMoveUp(point) && getArea(point.upPoint()) == Area.PATH;
    }

    private boolean pathAvailableDown(Point point) {
        return canMoveDown(point) && getArea(point.downPoint()) == Area.PATH;
    }

    private Area getArea(Point point) {
        return area[point.getX()][point.getY()];
    }
}
