package com.danzabarr.cellularautomata;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedSpace extends Space
{
    public BufferedImage cells;
    public BufferedImage buffer;

    public Mask mask;
    public RationalRule[] rules;

    public BufferedSpace(int width, int height, Mask mask, RationalRule... rules)
    {
        super(width, height);
        this.rules = rules;
        this.mask = mask;
        cells = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public double get(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return 0;
        return Mask.getValue(cells.getRGB(x, y));
    }

    public double set(int x, int y, double value)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return 0.0;

        double oldValue = get(x, y);
        cells.setRGB(x, y, Mask.getRGB(value));
        return oldValue;
    }

    public double toggle(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return 0.0;
        double value = get(x, y) == 0.0 ? 1.0 : 0.0;
        set(x, y, value);
        return value;
    }

    protected void applyRules()
    {
        for (RationalRule rule : rules)
            for (int x = 0; x < width; x++)
                for (int y = 0; y < height; y++)
                {
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

                    double value = Math.max(0.0, Math.min(1.0, rule.test(current, sum, average)));
                    int rgb = new Color((int) (value * 255), (int) (value * 255), (int) (value * 255)).getRGB();
                    buffer.setRGB(x, y, rgb);
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
        g.drawImage(cells, 0, 0, (int)(width * scale), (int)(height * scale), null);
    }

}
