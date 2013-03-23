package com.darren.maze.core;

/**
 */
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    Point leftPoint() {
        return new Point(x-1, y);
    }

    Point rightPoint() {
        return new Point(x+1, y);
    }

    Point upPoint() {
        return new Point(x, y-1);
    }

    Point downPoint() {
        return new Point(x, y+1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        if (y != point.y) return false;

        return true;
    }

    public Point multiply(int variable) {
        return new Point(x * variable, y * variable);
    }

    public Point incrementXY() {
        return new Point(x+1, y+1);
    }

    public Point incrementXY(int variable) {
        return new Point(x+variable, y+variable);
    }

    public int horizontalDistance(Point point) {
        return Math.abs(point.x - x);
    }

    public int verticalDistance(Point point) {
        return Math.abs(point.y - y);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
