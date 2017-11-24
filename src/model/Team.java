/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Viclec
 */
public class Team {

    private ArrayList<Unit> units = new ArrayList<>();  //the team units (dead & alive)
    private String color;   //the color of the team
    private Unit[] rescues = new Unit[2];   //array for the rescued units
    private static int wins1=0; //wins of Team1
    private static int wins2=0; //wins of Team2

    /**
     * @param color is the team's name (on our case the color)
     */
    public Team(String color) {
        this.color = color;
        int i;
        for (i = 0; i < 1; i++) {
            if (null != color) {
                switch (color) {
                    case "bluePieces":
                        units.add(new Unit("dragonB.png", 10, 1));
                        break;
                    case "RedPieces":
                        units.add(new Unit("dragonR.png", 10, 1));
                        break;
                }
            }
        }
        for (i = 0; i < 1; i++) {
            if (null != color) {
                switch (color) {
                    case "bluePieces":
                        units.add(new Unit("mageB.png", 9, 1));
                        break;
                    case "RedPieces":
                        units.add(new Unit("mageR.png", 9, 1));
                        break;
                }
            }
        }
        for (i = 0; i < 2; i++) {
            if (null != color) {
                switch (color) {
                    case "bluePieces":
                        units.add(new Unit("knightB.png", 8, 2));
                        break;
                    case "RedPieces":
                        units.add(new Unit("knightR.png", 8, 2));
                        break;
                }
            }
        }
        for (i = 0; i < 3; i++) {
            if (null != color) {
                switch (color) {
                    case "bluePieces":
                        units.add(new Unit("beastRiderB.png", 7, 3));
                        break;
                    case "RedPieces":
                        units.add(new Unit("beastRiderR.png", 7, 3));
                        break;
                }
            }
        }
        for (i = 0; i < 2; i++) {
            if (null != color) {
                switch (color) {
                    case "bluePieces":
                        units.add(new Unit("sorceressB.png", 6, 2));
                        break;
                    case "RedPieces":
                        units.add(new Unit("sorceressR.png", 6, 2));
                        break;
                }
            }
        }
        for (i = 0; i < 2; i++) {
            if (null != color) {
                switch (color) {
                    case "bluePieces":
                        units.add(new Unit("yeti.png", 5, 2));
                        break;
                    case "RedPieces":
                        units.add(new Unit("lavaBeast.png", 5, 2));
                        break;
                }
            }
        }
        for (i = 0; i < 2; i++) {
            if (null != color) {
                switch (color) {
                    case "bluePieces":
                        units.add(new Unit("elfB.png", 4, 2));
                        break;
                    case "RedPieces":
                        units.add(new Unit("elfR.png", 4, 2));
                        break;
                }
            }
        }
        for (i = 0; i < 5; i++) {
            if (null != color) {
                switch (color) {
                    case "bluePieces":
                        units.add(new Unit("dwarfB.png", 3, 5));
                        break;
                    case "RedPieces":
                        units.add(new Unit("dwarfR.png", 3, 5));
                        break;
                }
            }
        }
        for (i = 0; i < 4; i++) {
            if (null != color) {
                switch (color) {
                    case "bluePieces":
                        units.add(new Unit("scoutB.png", 2, 4));
                        break;
                    case "RedPieces":
                        units.add(new Unit("scoutB.png", 2, 4));
                        break;
                }
            }
        }
        for (i = 0; i < 1; i++) {
            if (null != color) {
                switch (color) {
                    case "bluePieces":
                        units.add(new Unit("slayerB.png", 1, 1));
                        break;
                    case "RedPieces":
                        units.add(new Unit("slayerR.png", 1, 1));
                        break;
                }
            }
        }
        for (i = 0; i < 6; i++) {
            if (null != color) {
                switch (color) {
                    case "bluePieces":
                        units.add(new Unit("trapB.png", 0, 6));
                        break;
                    case "RedPieces":
                        units.add(new Unit("trapR.png", 0, 6));
                        break;
                }
            }
        }
        for (i = 0; i < 1; i++) {
            if (null != color) {
                switch (color) {
                    case "bluePieces":
                        units.add(new Unit("flagB.png", 0, 1));
                        break;
                    case "RedPieces":
                        units.add(new Unit("flagR.png", 0, 1));
                        break;
                }
            }
        }
        shuffle(units);
    }

    ;
    /**
    *@return the ArrayList which includes all team's units
    */
    public ArrayList<Unit> getUnits() {
        return units;
    }

    /**
    *@return the number of the rescued units
    */
    public Unit[] getRescues() {
        return rescues;
    }
    
    /**
    *@param s unit's name
    *@param a unit's power
    *@param b unit's pax
    */
    public void addRescue(String s, int a, int b) {
        if (rescues[0] == null) {
            rescues[0] = new Unit(s, a, b);
        }
    }

    /**
     * @return true if selected team is defeated (team's flag has been
     * captured), else false
     */
    public boolean isDefeated() {
        int i;
        for (i = 0; i < units.size(); i++) {
            if (units.get(i).toString().contains("flag")) {
                return !units.get(i).isAlive();
            }
        }
        return true;
    }
    
    public void won1(){
        wins1++;
    }
    
    public void won2(){
        wins2++;
    }
    
    /**
    *@return wins of Team1
    */
    public int getWins1(){
        return wins1;
    }
    
    /**
    *@return wins of Team2
    */
    public int getWins2(){
        return wins2;
    }
    /**
     * shuffles all the units in order to place them on the grid randomly
     */
    public void shuffle(ArrayList<Unit> a) {
        Random r = new Random();
        Unit tmp;
        int randPos;
        int i;
        for (i = 0; i < a.size(); i++) {
            tmp = a.get(i);
            randPos = r.nextInt((a.size() - 1 - 0) + 0) + 1;
            a.set(i, a.get(randPos));
            a.set(randPos, tmp);
        }
    }
    /**
     * @return team name (in our case the color)
     */
    public String toString() {
        return color;
    }
;
}
