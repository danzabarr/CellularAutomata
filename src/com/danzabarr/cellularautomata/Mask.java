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
public class Mask
{
    private double[] values;
    public final double sumValue;
    private final BufferedImage image;

    private Mask(BufferedImage image, double[] values)
    {
        this.image = image;
        this.values = values;
        this.sumValue = sumValues();
    }

    public int getWidth()
    {
        return image.getWidth();
    }

    public int getHeight()
    {
        return image.getHeight();
    }

    private double sumValues()
    {
        double total = 0;
        for (double value : values)
            total += value;
        return total;
    }

    public static Mask load(String filePath) throws IOException
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

            /*
            System.out.println(image.getRGB(i % width, i / width));
            System.out.println(values[i]);

            System.out.println(rgb);
            System.out.println();

             */
        }

        return new Mask(image, values);
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
        if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight())
            return 0;

        return values[x + y * image.getWidth()];
    }

    public void draw(Graphics2D g, double x, double y, double scale)
    {
        AffineTransform transform = g.getTransform();

        g.scale(scale, scale);
        g.translate(x, y);
        g.drawImage(image, 0, 0, null);

        g.setTransform(transform);
    }
}
