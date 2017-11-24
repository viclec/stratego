/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import view.Board;
import model.Tile;
import model.Unit;
import static view.Board.everwinter;
import static view.Board.newGame;
import static view.Board.output;
import static view.Board.score1;
import static view.Board.score2;
import static view.Board.volcandria;

/**
 *
 * @author Viclec
 */
public class TileListener implements ActionListener {
    
    private String team;    //current unit's team
    private Unit unit;  //the current unit
    private int x, y;   //the current x and y
    static Unit[] unitHistory = new Unit[2];    //the history record for the two selected units
    static Tile[] tileHistory = new Tile[2];    //the history record for the two selected tiles
    static int xHistory, yHistory;  //x and y of the first selected unit
    static String teamHistory;  //the team of the first selected unit

    /**
     *
     * @param s is the team name of the selected unit
     * @param u is the selected unit
     * @param x is the x value of the selected unit
     * @param y is the y value of the selected unit
     */
    public TileListener(String s, Unit u, int x, int y) {
        this.team = s;
        this.unit = u;
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param s is the button's name
     */
    public TileListener(String s) {
        this.team = s;
    }

    /**
     *
     * @param e is the action which performed
     */
    public void actionPerformed(ActionEvent e) {
        int i;
        Tile tmpTile = new Tile(Board.tiles[y][x].toString(), x, y);
        if (team.equals("Resign")) {
            if (Board.rounds % 2 == 0) {
                output.setText("<html>Everwinter is defeated.<br><br><br><br><br><br><br><br><br><br><html>");
                Board.volcandria.won1();
                score2.setText("" + volcandria.getWins1());
                Board.newGame();
                return;
            } else {
                output.setText("<html>Volcandria is defeated.<br><br><br><br><br><br><br><br><br><br><html>");
                Board.everwinter.won2();
                score1.setText("" + everwinter.getWins2());
                Board.newGame();
                return;
            }
        } else if (team.equals("Refresh")) {
            Board.window.validate();
            Board.window.repaint();
        } else if (tileHistory[0] == null) {
            if (!"blank.jpg".equals(tmpTile.toString()) && !tmpTile.toString().endsWith("Hidden.png") && !unit.toString().contains("flag") && !unit.toString().contains("trap")) {
                tileHistory[0] = tmpTile;
                unitHistory[0] = unit;
                teamHistory = team;
                xHistory = x;
                yHistory = y;
                output.setText("<html>Selected.<br><br><br><br><br><br><br><br><br><br><html>");
                Board.tiles[yHistory][xHistory].setBorder(new LineBorder(Color.RED, 2));
                Board.availableMoves(xHistory, yHistory);
            } else {
                output.setText("<html>Cannot select that tile.<br><br><br><br><br><br><br><br><br><br><html>");
            }
        } else {
            if (unit == unitHistory[0]) {
                output.setText("<html>Deselected.<br><br><br><br><br><br><br><br><br><br><html>");
                Board.resetBorders();
                tileHistory[0] = null;
            } else if ((Board.distance(xHistory, yHistory, x, y) != 1 && !Board.tiles[yHistory][xHistory].toString().contains("scout") && !Board.tiles[yHistory][xHistory].toString().contains("dragon") && !Board.tiles[yHistory][xHistory].toString().contains("beast") && !Board.tiles[yHistory][xHistory].toString().contains("knight")) || (Board.distance(xHistory, yHistory, x, y) > 2 && (Board.tiles[yHistory][xHistory].toString().contains("knight") || Board.tiles[yHistory][xHistory].toString().contains("beast"))) || (Board.distance(xHistory, yHistory, x, y) > 9 && (Board.tiles[yHistory][xHistory].toString().contains("scout")))) {
                output.setText("<html>Cannot do that.<br><br><br><br><br><br><br><br><br><br><html>");
            } else if ("blank.jpg".equals(tmpTile.toString())) {
                output.setText("<html>Move.<br><br><br><br><br><br><br><br><br><br><html>");
                Board.playboard.remove(Board.tiles[y][x]);
                Board.playboard.remove(Board.tiles[yHistory][xHistory]);
                Board.tiles[y][x] = Board.tiles[yHistory][xHistory];
                while (Board.tiles[y][x].getActionListeners().length > 0) {
                    Board.tiles[y][x].removeActionListener(Board.tiles[y][x].getActionListeners()[0]);
                }
                Board.tiles[y][x].addActionListener(new TileListener(teamHistory, unitHistory[0], x, y));
                while (Board.tiles[yHistory][xHistory].getActionListeners().length > 0) {
                    Board.tiles[yHistory][xHistory].removeActionListener(Board.tiles[yHistory][xHistory].getActionListeners()[0]);
                }
                while (Board.tiles[y][x].getActionListeners().length > 0) {
                    Board.tiles[y][x].removeActionListener(Board.tiles[y][x].getActionListeners()[0]);
                }
                Board.tiles[y][x].addActionListener(new TileListener(teamHistory, unitHistory[0], x, y));
                Board.tiles[yHistory][xHistory] = new Tile("blank.jpg", xHistory, yHistory);
                Board.tiles[yHistory][xHistory].addActionListener(new TileListener("", null, xHistory, yHistory));
                Board.tiles[yHistory][xHistory].changeCoordinates(x, y);
                Board.tiles[y][x].changeCoordinates(xHistory, yHistory);
                if (teamHistory.equals("RedPieces")) {
                    Board.playboard.add(Board.tiles[y][x], x + 10 * y);
                } else {
                    Board.playboard.add(Board.tiles[y][x], x + 10 * y - 1);
                }
                Board.playboard.add(Board.tiles[yHistory][xHistory], xHistory + 10 * yHistory);
                if ((teamHistory.equals("RedPieces") && y == 0) || (teamHistory.equals("bluePieces") && y == 7)) {
                    Board.revive(teamHistory);
                }
                Board.playboard.validate();
                Board.playboard.repaint();
                tileHistory[0] = null;
                Board.resetBorders();
                Board.nextRound();
            } else if (team.equals(teamHistory)) {
                output.setText("<html>Cannot attack an ally.<br><br><br><br><br><br><br><br><br><br><html>");
                Board.resetBorders();
                tileHistory[0] = null;
            } else if ((unitHistory[0].getPower() > unit.getPower() && !unit.toString().contains("trap")) || (unitHistory[0].toString().contains("dwarf") && unit.toString().contains("trap")) || (unitHistory[0].toString().contains("slayer") && unit.toString().contains("dragon"))) {
                output.setText("<html>" + unitHistory[0].toString() + " killed " + unit.toString() + "<br><br><br><br><br><br><br><br><br><br><html>");
                Board.playboard.remove(Board.tiles[y][x]);
                Board.playboard.remove(Board.tiles[yHistory][xHistory]);
                Board.tiles[y][x] = Board.tiles[yHistory][xHistory];
                while (Board.tiles[yHistory][xHistory].getActionListeners().length > 0) {
                    Board.tiles[yHistory][xHistory].removeActionListener(Board.tiles[yHistory][xHistory].getActionListeners()[0]);
                }
                while (Board.tiles[y][x].getActionListeners().length > 0) {
                    Board.tiles[y][x].removeActionListener(Board.tiles[y][x].getActionListeners()[0]);
                }
                Board.tiles[y][x].addActionListener(new TileListener(teamHistory, unitHistory[0], x, y));
                Board.tiles[yHistory][xHistory] = new Tile("blank.jpg", xHistory, yHistory);
                Board.tiles[yHistory][xHistory].addActionListener(new TileListener("", null, xHistory, yHistory));
                Board.tiles[yHistory][xHistory].changeCoordinates(x, y);
                Board.tiles[y][x].changeCoordinates(xHistory, yHistory);
                if (teamHistory.equals("RedPieces")) {
                    Board.playboard.add(Board.tiles[y][x], x + 10 * y);
                } else {
                    Board.playboard.add(Board.tiles[y][x], x + 10 * y - 1);
                }
                Board.playboard.add(Board.tiles[yHistory][xHistory], xHistory + 10 * yHistory);
                unitHistory[0].Attack(unit);
                
                Board.playboard.validate();
                Board.resetBorders();
                Board.playboard.repaint();
                if (unit.toString().contains("flag")) {
                    if (team.contains("Red")) {
                        output.setText("<html>Volcandria is defeated.<br><br><br><br><br><br><br><br><br><br><html>");
                        everwinter.won2();
                        score1.setText("" + everwinter.getWins2());
                        newGame();
                    } else {
                        output.setText("<html>Everwinter is defeated.<br><br><br><br><br><br><br><br><br><br><html>");
                        volcandria.won1();
                        score2.setText("" + volcandria.getWins1());
                        newGame();
                    }
                }
                tileHistory[0] = null;
                Board.nextRound();
            } else if (unitHistory[0].getPower() < unit.getPower() || unit.toString().contains("trap")) {
                output.setText("<html>" + unitHistory[0].toString() + " killed by " + unit.toString() + "<br><br><br><br><br><br><br><br><br><br><html>");
                Board.playboard.remove(Board.tiles[yHistory][xHistory]);
                Board.tiles[yHistory][xHistory] = new Tile("blank.jpg", xHistory, yHistory);
                Board.tiles[yHistory][xHistory].addActionListener(new TileListener("", null, xHistory, yHistory));
                Board.playboard.add(Board.tiles[yHistory][xHistory], xHistory + 10 * yHistory);
                unit.Attack(unitHistory[0]);
                
                Board.playboard.validate();
                Board.resetBorders();
                Board.playboard.repaint();
                tileHistory[0] = null;
                Board.nextRound();
            } else if (unitHistory[0].getPower() == unit.getPower()) {
                output.setText("<html>" + unitHistory[0].toString() + " and " + unit.toString() + " both killed." + "<br><br><br><br><br><br><br><br><br><br><html>");
                Board.playboard.remove(Board.tiles[y][x]);
                Board.playboard.remove(Board.tiles[yHistory][xHistory]);
                Board.tiles[y][x] = new Tile("blank.jpg", x, y);
                Board.tiles[yHistory][xHistory] = new Tile("blank.jpg", xHistory, yHistory);
                Board.tiles[y][x].addActionListener(new TileListener("", null, x, y));
                Board.tiles[yHistory][xHistory].addActionListener(new TileListener("", null, xHistory, yHistory));
                Board.tiles[yHistory][xHistory].changeCoordinates(x, y);
                Board.tiles[y][x].changeCoordinates(xHistory, yHistory);
                Board.playboard.add(Board.tiles[y][x], x + 10 * y);
                Board.playboard.add(Board.tiles[yHistory][xHistory], xHistory + 10 * yHistory);
                unit.Attack(unitHistory[0]);
                
                Board.playboard.validate();
                Board.resetBorders();
                Board.playboard.repaint();
                tileHistory[0] = null;
                Board.nextRound();
            }
            
        }
    }
}
