package com.darren.maze.ui;

import com.darren.maze.Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 */
public class SwingMaze extends JFrame {
    private Maze maze = new Maze(50,50);
    private JPanelScreen panelScreen = new JPanelScreen(maze, 10, configuration());
    private JRadioButton path = new JRadioButton("Path");
    private JRadioButton wall = new JRadioButton("Wall");
    private ButtonGroup group = new ButtonGroup();
    private JButton solve = new JButton("Solve");


    public SwingMaze() throws HeadlessException {
        Container content = getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new FlowLayout());
        group.add(path);
        group.add(wall);
        content.add(path);
        content.add(wall);
        content.add(solve);
        solve.addActionListener(solveActionListener());
        content.add(panelScreen);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SwingMaze().setVisible(true);
            }
        });
    }

    private Configuration configuration() {
        return new Configuration() {
            public boolean drawPath() {
                return path.isSelected();
            }

            public boolean drawWall() {
                return wall.isSelected();
            }
        };
    }

    private ActionListener solveActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(maze.getStatus().isValid()) {
                    System.out.println(maze.solve());
                    panelScreen.setSolution(maze.solve());
                    panelScreen.repaint();
                } else {
                    System.out.println(maze.getStatus().getMessage());
                }
            }
        };
    }
}
