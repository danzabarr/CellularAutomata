package com.danzabarr.cellularautomata;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Represents a cellular automaton.
 */
public class Space
{
    public BufferedImage cells;
    public BufferedImage buffer;

    public HashMap<Rule, Neighbourhood> rules;

    public Space(int width, int height, HashMap<Rule, Neighbourhood> rules)
    {
        this.rules = rules;
        cells = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public int getWidth()
    {
        return cells.getWidth();
    }

    public int getHeight()
    {
        return cells.getHeight();
    }

    public double get(int x, int y)
    {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight())
            return 0;
        return Neighbourhood.getValue(cells.getRGB(x, y));
    }

    public double set(int x, int y, double value)
    {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight())
            return 0.0;

        double oldValue = get(x, y);
        cells.setRGB(x, y, Neighbourhood.getRGB(value));
        return oldValue;
    }

    public double toggle(int x, int y)
    {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight())
            return 0.0;
        double value = get(x, y) == 0.0 ? 1.0 : 0.0;
        set(x, y, value);
        return value;
    }

    public void step()
    {
        applyRules();
        swapBuffers();
    }

    protected void applyRules()
    {
        for (HashMap.Entry<Rule, Neighbourhood> entry : rules.entrySet())
        {
            Rule rule = entry.getKey();
            Neighbourhood neighbourhood = entry.getValue();

            for (int x = 0; x < getWidth(); x++)
                for (int y = 0; y < getHeight(); y++)
                {
                    double sum = 0;

                    for (int mx = 0; mx < neighbourhood.getWidth(); mx++)
                    {
                        for (int my = 0; my < neighbourhood.getHeight(); my++)
                        {
                            double maskValue = neighbourhood.get(mx, my);
                            double cellValue = get(x + mx - neighbourhood.getWidth() / 2, y + my - neighbourhood.getHeight() / 2);
                            sum += maskValue * cellValue;
                        }
                    }
                    double current = get(x, y);
                    double average = sum / neighbourhood.sumValue;

                    double value = Math.max(0.0, Math.min(1.0, rule.test(current, sum, average)));
                    int rgb = new Color((int) (value * 255), (int) (value * 255), (int) (value * 255)).getRGB();
                    buffer.setRGB(x, y, rgb);
                }
        }
    }

    protected void swapBuffers()
    {
        BufferedImage temp = cells;
        cells = buffer;
        buffer = temp;
    }

    public void draw(Graphics2D g, double scale)
    {
        g.drawImage(cells, 0, 0, (int)(getWidth() * scale), (int)(getHeight() * scale), null);
    }
}
