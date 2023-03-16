package com.danzabarr.cellularautomata;

import java.awt.*;

public class BitSpace extends Space
{
    public boolean[] cells;
    public boolean[] buffer;
    public Point[] neighbours;
    public Rule[] rules;

    public BitSpace(int width, int height, Point[] neighbourhood, Rule... rules)
    {
        super(width, height);
        cells = new boolean[width * height];
        buffer = new boolean[width * height];
        this.rules = rules;
        this.neighbours = neighbourhood;
    }

    public double get(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return 0;
        return cells[x + y * width] ? 1.0 : 0.0;
    }

    public double set(int x, int y, double value)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return 0.0;

        double oldValue = cells[x + y * width] ? 1.0 : 0.0;
        cells[x + y * width] = value > 0.5;
        return oldValue;
    }

    /**
     * Toggles the value of the cell at the given coordinates.
     * @param x The x coordinate of the cell to toggle.
     * @param y The y coordinate of the cell to toggle.
     * @return The new value of the cell.
     */
    public double toggle(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return 0.0;
        double value = cells[x + y * width] ? 0.0 : 1.0;
        set(x, y, value);
        return value;
    }

    protected void applyRules()
    {
        for (Rule rule : rules)
        {
            for (int i = 0; i < width * height; i++)
            {
                int x = i % width;
                int y = i / width;

                boolean active = cells[i];
                int count = 0;
                int total = 0;
                //boolean[] neighbourhood = new boolean[neighbours.length];

                for (int j = 0; j < neighbours.length; j++)
                {
                    int nx = x + neighbours[j].x;
                    int ny = y + neighbours[j].y;

                    if (nx < 0 || nx >= width)
                        continue;

                    if (ny < 0 || ny >= height)
                        continue;

                    int ni = nx + ny * width;

                    if (cells[ni])
                        count++;

                    total++;
                }

                buffer[i] = rule.test(active, count, (double) count / total);
            }
        }
    }

    protected void swapBuffers()
    {
        for (int i = 0; i < cells.length; i++)
            cells[i] = buffer[i];
    }

    public void draw(Graphics2D g, double scale)
    {
        int size = (int) (scale);
        for (int i = 0; i < cells.length; i++)
        {
            if (cells[i])
                g.setColor(Color.white);
            else
                g.setColor(Color.black);

            g.fillRect((i % width) * size, (i / width) * size, size, size);
            //else
            //  g.drawRect((i % width) * size, (i / width) * size, size, size);
        }
        /*
        g.setColor(Color.gray);
        for (int i = 0; i < cells.length; i++)
            g.drawRect((i % width) * size, (i / width) * size, size, size);
        */
    }
}
