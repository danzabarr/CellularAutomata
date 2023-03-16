package com.danzabarr.cellularautomata;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Represents a mask to be used in a cellular automaton.
 */
public class Neighbourhood
{
    public static final Neighbourhood MASK_01 = loadQuick("masks/mask_01.png");
    public static final Neighbourhood MASK_02 = loadQuick("masks/mask_02.png");
    public static final Neighbourhood MASK_03 = loadQuick("masks/mask_03.png");
    public static final Neighbourhood VON_NEUMANN = loadQuick("masks/von_neumann.png");
    public static final Neighbourhood MOORE = loadQuick("masks/moore.png");

    private double[] values;
    public final double sumValue;
    private final BufferedImage mask;

    private Neighbourhood(BufferedImage mask, double[] values)
    {
        this.mask = mask;
        this.values = values;
        this.sumValue = sumValues();
    }

    public int getWidth()
    {
        return mask.getWidth();
    }

    public int getHeight()
    {
        return mask.getHeight();
    }

    private double sumValues()
    {
        double total = 0;
        for (double value : values)
            total += value;
        return total;
    }

    public static Neighbourhood loadQuick(String filePath)
    {
        try
        {
            return load(filePath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Neighbourhood load(String filePath) throws IOException
    {
        File file = new File(filePath);
        if (!file.exists())
            throw new IOException("File does not exist: " + filePath);

        BufferedImage image = ImageIO.read(file);

        int width = image.getWidth();
        int height = image.getHeight();
        double[] values = new double[width * height];

        for (int i = 0; i < width * height; i++)
        {
            values[i] = getValue(image.getRGB(i % width, i / width));
            int rgb = new Color((int) (values[i] * 255), (int) (values[i] * 255), (int) (values[i] * 255)).getRGB();
            image.setRGB(i % width, i / width, rgb);
        }

        return new Neighbourhood(image, values);
    }

    public static int getRGB(double value)
    {
        int v = (int) (value * 255);
        return (v << 16) | (v << 8) | v;
    }

    public static int getARGB(double value)
    {
        int v = (int) (value * 255);
        return (v << 24) | (v << 16) | (v << 8) | v;
    }

    public static double getValue(int rgb)
    {
        return (rgb & 0xFF) / 255.0;
    }

    public double get(int x, int y)
    {
        if (x < 0 || x >= mask.getWidth() || y < 0 || y >= mask.getHeight())
            return 0;

        return values[x + y * mask.getWidth()];
    }

    public void draw(Graphics2D g, double x, double y, double scale)
    {
        AffineTransform transform = g.getTransform();

        g.scale(scale, scale);
        g.translate(x, y);
        g.drawImage(mask, 0, 0, null);

        g.setTransform(transform);
    }
}
