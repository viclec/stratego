/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.TileListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import model.Team;
import model.Tile;

/**
 *
 * @author Viclec
 */
public class Board {

    public static int rounds = 0;
    public static Tile[][] tiles = new Tile[8][10];
    public static JFrame window = new JFrame();
    public static JPanel stats = new JPanel();
    public static JPanel playboard = new JPanel();
    public static JLabel output = new JLabel("<html>Output<br><br><br><br><br><br><br><br><br><br><html>");
    public static Team volcandria = new Team("RedPieces");
    public static Team everwinter = new Team("bluePieces");
    public static JLabel score2 = new JLabel("" + volcandria.getWins1());
    public static JLabel score1 = new JLabel("" + everwinter.getWins2());

    /**
     * creates the window which contains the playboard and the UI
     */
    public Board() {
        playboard.setPreferredSize(new Dimension(850, 1000));
        window.setSize(new Dimension(1600, 1000));
        stats.setPreferredSize(new Dimension(300, 1000));
        window.setLayout(new GridLayout(1, 2));
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        playboard.setLayout(new GridLayout(8, 10));
        newGame();
        playboard.setVisible(true);
        stats.setVisible(true);
        window.add(playboard);
        JButton resign = new JButton("Resign");
        resign.addActionListener(new TileListener("Resign"));
        JButton refresh = new JButton("Refresh");
        resign.addActionListener(new TileListener("Refresh"));
        JLabel quit = new JLabel("In case you want to resign and quit press here");
        score1.setFont(new Font("Serif", Font.PLAIN, 40));
        ImageIcon icon = new ImageIcon("images/ice.png");
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(85, 125, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        score1.setIcon(icon);
        score1.setIconTextGap(0);
        score1.setHorizontalTextPosition(JLabel.CENTER);
        icon = new ImageIcon("images/fire.png");
        img = icon.getImage();
        newimg = img.getScaledInstance(85, 125, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        score2.setIcon(icon);
        score2.setIconTextGap(0);
        score2.setFont(new Font("Serif", Font.PLAIN, 40));
        score2.setHorizontalTextPosition(JLabel.CENTER);
        output.setFont(new Font("Serif", Font.PLAIN, 40));
        stats.add(output);
        stats.add(score1);
        stats.add(score2);
        stats.add(quit);
        stats.add(resign);
        window.add(stats);
        window.setVisible(true);
        Board.resetBorders();
    }

    /**
     * indicates current player and determines which player's cards should hide
     */
    public static void nextRound() {
        int i, j, z = 0;
        window.revalidate();
        Team team;
        rounds++;
        if (volcandria.isDefeated()) {
            output.setText("<html>Volcandria is defeated.<br><br><br><br><br><br><br><br><br><br><html>");
            everwinter.won2();
            score1.setText("" + everwinter.getWins2());
            newGame();
        } else if (everwinter.isDefeated()) {
            output.setText("<html>Everwinter is defeated.<br><br><br><br><br><br><br><br><br><br><html>");
            volcandria.won1();
            score2.setText("" + volcandria.getWins1());
            newGame();
        }
        if (rounds % 2 == 1) {
            team = volcandria;
        } else {
            team = everwinter;
        }
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 10; j++) {
                if (z == volcandria.getUnits().size()) {
                    z = 0;
                }
                if (team == volcandria) {
                    if (tiles[i][j].toString().startsWith("R")) {
                        tiles[i][j].setSrc(tiles[i][j].toString2());
                        z++;
                    } else if (tiles[i][j].toString().startsWith("blue")) {
                        tiles[i][j].setSrc(everwinter.toString() + "/blueHidden.png");
                        playboard.validate();
                        playboard.repaint();
                    }
                }
                if (team == everwinter) {
                    if (tiles[i][j].toString().startsWith("R")) {
                        tiles[i][j].setSrc(volcandria.toString() + "/redHidden.png");
                        playboard.validate();
                        playboard.repaint();
                    } else if (tiles[i][j].toString().startsWith("blue")) {
                        tiles[i][j].setSrc(tiles[i][j].toString2());
                        z++;
                    }
                }
            }
        }
    }

    public static int distance(int x1, int y1, int x2, int y2) {
        int z1 = x1 + 10 * y1;
        int z2 = x2 + 10 * y2;
        int i;
        for (i = 1; i < 9; i++) {
            if ((z1 - z2 == i || z1 - z2 == -i || z1 - z2 == 10 * i || z1 - z2 == -10 * i)&&(x1==x2||y1==y2)) {
                return i;
            }
        }
        return 10;
    }

    public static void availableMoves(int x, int y) {
        int i, j;
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 8; j++) {
                if (Board.distance(x, y, i, j) == 1 || Board.tiles[y][x].toString().contains("dragon")) {
                    Board.tiles[j][i].setBorder(new LineBorder(Color.BLUE, 2));
                } else if (Board.distance(x, y, i, j) == 2 && (Board.tiles[y][x].toString().contains("knight") || Board.tiles[y][x].toString().contains("beast") || Board.tiles[y][x].toString().contains("scout"))) {
                    Board.tiles[j][i].setBorder(new LineBorder(Color.BLUE, 2));
                } else if (Board.distance(x, y, i, j) == 3 && Board.tiles[y][x].toString().contains("scout")) {
                    Board.tiles[j][i].setBorder(new LineBorder(Color.BLUE, 2));
                } else if (Board.distance(x, y, i, j) == 4 && Board.tiles[y][x].toString().contains("scout")) {
                    Board.tiles[j][i].setBorder(new LineBorder(Color.BLUE, 2));
                } else if (Board.distance(x, y, i, j) == 5 && Board.tiles[y][x].toString().contains("scout")) {
                    Board.tiles[j][i].setBorder(new LineBorder(Color.BLUE, 2));
                } else if (Board.distance(x, y, i, j) == 6 && Board.tiles[y][x].toString().contains("scout")) {
                    Board.tiles[j][i].setBorder(new LineBorder(Color.BLUE, 2));
                } else if (Board.distance(x, y, i, j) == 7 && Board.tiles[y][x].toString().contains("scout")) {
                    Board.tiles[j][i].setBorder(new LineBorder(Color.BLUE, 2));
                } else if (Board.distance(x, y, i, j) == 8 && Board.tiles[y][x].toString().contains("scout")) {
                    Board.tiles[j][i].setBorder(new LineBorder(Color.BLUE, 2));
                } else if (Board.distance(x, y, i, j) == 9 && Board.tiles[y][x].toString().contains("scout")) {
                    Board.tiles[j][i].setBorder(new LineBorder(Color.BLUE, 2));
                }
            }
        }
    }

    public static void resetBorders() {
        int i, j;
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 8; j++) {
                Board.tiles[j][i].setBorder(new LineBorder(Color.BLACK, 1));
            }
        }
    }

    public static void revive(String team) {
        int i, j;
        if (team.equals("RedPieces")) {
            for (i = 0; i < 80; i++) {
                if (!volcandria.getUnits().get(i).isAlive() && volcandria.getRescues()[1] == null) {
                    for (j = 50; j < 80; j++) {
                        if (Board.tiles[j / 10][j % 10].toString().contains("blank")) {
                            volcandria.getUnits().get(i).revived();
                            volcandria.addRescue(volcandria.getUnits().get(i).toString(), volcandria.getUnits().get(i).getPower(), volcandria.getUnits().get(i).getPax());
                            tiles[i][j] = new Tile(volcandria.toString() + "/" + volcandria.getUnits().get(i), j / 10, j % 10);
                            tiles[i][j].addActionListener(new TileListener(volcandria.toString(), volcandria.getUnits().get(i), j / 10, j % 10));
                        }
                        break;
                    }
                    break;
                }
            }
        } else {
            for (i = 0; i < 80; i++) {
                if (!everwinter.getUnits().get(i).isAlive()) {
                    for (j = 0; j < 30; j++) {
                        if (Board.tiles[j / 10][j % 10].toString().contains("blank")) {
                            tiles[i][j] = new Tile(everwinter.toString() + "/" + everwinter.getUnits().get(i), j / 10, j % 10);
                            tiles[i][j].addActionListener(new TileListener(everwinter.toString(), everwinter.getUnits().get(i), j / 10, j % 10));
                        }
                        break;
                    }
                    break;
                }
            }
        }
    }

    public static void newGame() {
        rounds = 0;
        playboard.removeAll();
        int i, j, z = 0;
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 10; j++) {
                if (i < 3) {
                    tiles[i][j] = new Tile(everwinter.toString() + "/" + everwinter.getUnits().get(z), j, i);
                    tiles[i][j].addActionListener(new TileListener(everwinter.toString(), everwinter.getUnits().get(z), j, i));
                    z++;
                    if (z == everwinter.getUnits().size()) {
                        z = 0;
                    }
                } else if (i < 5) {
                    if (j == 2 || j == 3 || j == 6 || j == 7) {
                        tiles[i][j] = new Tile("disabled.jpg", j, i);
                    } else {
                        tiles[i][j] = new Tile("blank.jpg", j, i);
                        tiles[i][j].addActionListener(new TileListener("", null, j, i));
                    }
                } else {
                    tiles[i][j] = new Tile(volcandria.toString() + "/" + volcandria.getUnits().get(z), j, i);
                    tiles[i][j].addActionListener(new TileListener(volcandria.toString(), volcandria.getUnits().get(z), j, i));
                    z++;
                    if (z == volcandria.getUnits().size()) {
                        z = 0;
                    }
                }
                playboard.add(tiles[i][j]);
            }
        }
        nextRound();
        volcandria = new Team("RedPieces");
        everwinter = new Team("bluePieces");
        window.validate();
        window.repaint();
    }
}
