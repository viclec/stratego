/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Viclec
 */
public class Tile extends JButton {

    private String src; //image src
    private String oldsrc;  //image src of the unit in order to display again the unit after hiding it
    private int x, y;   //x and y of the tile

    /**
     *
     * @param src is the image src of the current tile
     * @param x is the x value of the tile on the grid
     * @param y is the y value of the tile on the grid
     *
     *
     */
    public Tile(String src, int x, int y) {
        if (!src.endsWith("Hidden.png")) {
            oldsrc = src;
        }
        setSrc(src);
        changeCoordinates(x, y);
    }

    /**
     *
     * @param x changes the x value
     * @param y changes the y value
     */
    public void changeCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param src sets new image src
     */
    public void setSrc(String src) {
        this.src = src;
        ImageIcon icon = new ImageIcon("images/" + src);
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(85, 125, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        this.setIcon(icon);
        this.setIconTextGap(0);
    }

    /**
     *
     * @return the image path
     */
    public String toString() {
        return src;
    }


    /**
     *
     * @return the image path
     */
    public String toString2() {
        return oldsrc;
    }
}
