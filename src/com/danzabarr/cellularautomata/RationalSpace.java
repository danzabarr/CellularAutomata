package com.danzabarr.cellularautomata;

import java.awt.*;

public class RationalSpace extends Space
{
    public double[] cells;
    public double[] buffer;

    public Mask mask;
    public RationalRule[] rules;

    public RationalSpace(int width, int height, Mask mask, RationalRule... rules)
    {
        super(width, height);
        cells = new double[width * height];
        buffer = new double[width * height];
        this.mask = mask;
        this.rules = rules;
    }

    public double get(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return 0;
        return cells[x + y * width];
    }

    public double set(int x, int y, double value)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return 0.0;

        double oldValue = cells[x + y * width];
        cells[x + y * width] = value;
        return oldValue;
    }

    public double toggle(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return 0.0;
        double value = cells[x + y * width] == 0.0 ? 1.0 : 0.0;
        set(x, y, value);
        return value;
    }

    protected void applyRules()
    {
        for (RationalRule rule : rules)
            for (int i = 0; i < cells.length; i++)
            {
                int x = i % width;
                int y = i / width;

                double sum = 0;

                for (int mx = 0; mx < mask.getWidth(); mx++)
                {
                    for (int my = 0; my < mask.getHeight(); my++)
                    {
                        double maskValue = mask.get(mx, my);
                        double cellValue = get(x + mx - mask.getWidth() / 2, y + my - mask.getHeight() / 2);
                        sum += maskValue * cellValue;
                    }
                }
                double current = get(x, y);
                double average = sum / mask.sumValue;

                buffer[i] = Math.max(0.0, Math.min(1.0, rule.test(current, sum, average)));
            }
    }

    protected void swapBuffers()
    {
        for (int i = 0; i < cells.length; i++)
            cells[i] = buffer[i];
    }

    @Override
    public void draw(Graphics2D g, double scale)
    {

    }
}
