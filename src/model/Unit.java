/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Viclec
 */
public class Unit {

    private int power;
    private int pax;
    private String img;
    private boolean isDead;
    private boolean hasRescued;

    /**
     * kills the selected unit
     */
    private void Kill() {
        isDead = true;
    }

    /**
     *
     * @param img is the image path (or unit's name in our case)
     * @param power is the power of the current unit
     * @param pax is the population of the current unit. this is useful for
     * finding the team's flag
     */
    Unit(String img, int power, int pax) {
        this.img = img;
        this.power = power;
        this.pax = pax;
    }

    /**
     *
     * @return unit's power
     */
    public int getPower() {
        return power;
    }

    /**
     *
     * @return unit's population
     */
    public int getPax() {
        return pax;
    }

    /**
     *
     * @param adversary is the enemy unit which is under attack if our unit is
     * stronger then it kills the adversary if the adversary unit is stronger
     * then out unit dies if they are equal in strength they both die
     */
    public void Attack(Unit adversary) {
        if (adversary.getPower() > getPower()) {
            Kill();
            isDead=true;
        } else if (adversary.getPower() < getPower()) {
            adversary.Kill();
            adversary.isDead=true;
        } else {
            Kill();
            isDead=true;
            adversary.isDead=true;
            adversary.Kill();
        }
    }

    /**
     *
     * @return the state of the current unit
     */
    public boolean isAlive() {
        return !isDead;
    }

    public void revived() {
        hasRescued = true;
    }


    /**
     *
     * @return true if the unit has revived another, else false
     */
    public boolean hasRevived() {
        return hasRescued;
    }

    /**
     *
     * @return unit's name or unit's image path
     */
    public String toString() {
        return img;
    }
}
