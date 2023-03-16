package com.danzabarr.cellularautomata;

import java.awt.*;

public abstract class Space
{

    protected int width, height;

    protected Space(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    /**
     * Computes the next step of the space.
     */
    public void step()
    {
        applyRules();
        swapBuffers();
    }

    /**
     * Applies rules to the space.
     */
    protected abstract void applyRules();

    /**
     * Swaps the buffers of the space.
     */
    protected abstract void swapBuffers();

    public abstract void draw(Graphics2D g, double scale);


    /**
     * Returns the value of the cell at the given coordinates.
     * @param x The x coordinate of the cell to get.
     * @param y The y coordinate of the cell to get.
     * @return
     */
    public abstract double get(int x, int y);

    /**
     * Sets the value of the cell at the given coordinates.
     * @param x The x coordinate of the cell to set.
     * @param y The y coordinate of the cell to set.
     * @param value The new value of the cell.
     * @return The old value of the cell.
     */
    public abstract double set(int x, int y, double value);

    /**
     * Toggles the value of the cell at the given coordinates.
     * @param x The x coordinate of the cell to toggle.
     * @param y The y coordinate of the cell to toggle.
     * @return The new value of the cell.
     */
    public abstract double toggle(int x, int y);

}
