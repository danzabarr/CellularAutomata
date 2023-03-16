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

    public void step()
    {
        applyRules();
        swapBuffers();
    }

    protected abstract void applyRules();

    protected abstract void swapBuffers();

    public abstract void draw(Graphics2D g, double scale);

    public abstract double get(int x, int y);

    public abstract double set(int x, int y, double value);

    public abstract double toggle(int x, int y);

}
