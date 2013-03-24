package com.darren.maze.swing;

import com.darren.maze.core.Maze;
import com.darren.maze.io.MazeIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 */
public class SwingMaze extends JFrame {
    private Maze maze = new Maze(50,50);
    private JPanelScreen panelScreen = new JPanelScreen(maze, 10, configuration());
    private JRadioButton path = new JRadioButton("Path");
    private JRadioButton wall = new JRadioButton("Wall");
    private ButtonGroup group = new ButtonGroup();
    private JButton solve = new JButton("Solve");
    private JButton animateSolution = new JButton("Animate Logic");
    private File currentFile;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("File");
    private JMenuItem newItem = new JMenuItem("New");
    private JMenuItem save = new JMenuItem("Save");
    private JMenuItem saveAs = new JMenuItem("Save As");
    private JMenuItem open = new JMenuItem("Open");
    JFileChooser fileChooser = new JFileChooser();

    public SwingMaze(File file) throws HeadlessException {
        currentFile = file;
        loadMaze();
        setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(newItem);
        menu.add(open);
        menu.add(save);
        menu.add(saveAs);
        newItem.addActionListener(newActionListener());
        save.addActionListener(saveActionListener());
        open.addActionListener(openActionListener());
        saveAs.addActionListener(saveAsActionListener());
        fileChooser.setFileFilter(new FileNameExtensionFilter("Maze files", "mz"));

        Container content = getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new FlowLayout());
        path.setSelected(true);
        group.add(path);
        group.add(wall);
        content.add(path);
        content.add(wall);
        content.add(solve);
        content.add(animateSolution);
        solve.addActionListener(solveActionListener());
        animateSolution.addActionListener(animatedSolutionActionListener());
        content.add(panelScreen);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setPreferredSize(new Dimension(530, 550));
        setResizable(false);
        pack();
    }

    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                File file = null;
                if(args.length > 0) {
                    file = new File(args[0]);
                }
                new SwingMaze(file).setVisible(true);
            }
        });
    }

    private Configuration configuration() {
        return new Configuration() {
            public Maze.Area drawArea() {
                return path.isSelected() ? Maze.Area.PATH : Maze.Area.WALL;

            }
       };
    }

    private ActionListener solveActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(maze.getStatus().isValid()) {
                    System.out.println(maze.solve());
                    panelScreen.animateSolution(maze.solve());
                } else {
                    System.out.println(maze.getStatus().getMessage());
                }
            }
        };
    }

    private ActionListener openActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(panelScreen);
                if(returnValue == JFileChooser.APPROVE_OPTION) {
                    currentFile = fileChooser.getSelectedFile();
                    loadMaze();
                }
            }
        };
    }


    private void loadMaze() {
        if(currentFile == null) {
            return;
        }
        try {
            Maze newMaze = new MazeIO().readMaze(new FileInputStream(currentFile));
            maze.copyFrom(newMaze);
            panelScreen.repaint();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private ActionListener saveAsActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showSaveDialog(panelScreen);
                if(returnValue == JFileChooser.APPROVE_OPTION) {
                    currentFile = fileWithCorrectionExtension(fileChooser.getSelectedFile());
                    save();
                }
            }
        };
    }

    private void save() {
        try {
            new MazeIO().writeMaze(maze, new FileOutputStream(currentFile));
        } catch(Exception ex)  {
            throw new RuntimeException(ex);
        }
    }

    private ActionListener saveActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(currentFile == null) {
                    saveAsActionListener().actionPerformed(null);
                } else {
                    save();
                }
            }
        };
    }

    private ActionListener newActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentFile = null;
                maze.copyFrom(new Maze(50, 50));
                panelScreen.repaint();
            }
        };
    }

    private ActionListener animatedSolutionActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        maze.solve(new AnimatedSolutionVisitor(panelScreen));
                    }
                }).start();

            }
        };
    }

    private File fileWithCorrectionExtension(File file) {
        if(file.getName().toLowerCase().endsWith(".mz")) {
            return file;
        }
        return new File(file.getAbsolutePath() + ".mz");
    }
}
