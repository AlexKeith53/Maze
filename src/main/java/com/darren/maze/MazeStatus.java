package com.darren.maze;

/**
 */
public class MazeStatus {
    private boolean isValid;
    private String message;

    public MazeStatus(boolean valid, String message) {
        isValid = valid;
        this.message = message;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getMessage() {
        return message;
    }
}
