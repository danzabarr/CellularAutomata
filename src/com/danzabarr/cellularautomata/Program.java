package com.danzabarr.cellularautomata;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;

public class Program extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{
    public static void main(String[] args)
    {
        try
        {
            new Program();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static final int SIZE = 8;
    public static final long INTERVAL = 1000000000L / 16; //16th second

    public Space space;
    public Point mouseCell = new Point();
    public boolean running;

    public Program() throws IOException
    {
        //space = new BitSpace(100, 100, Neighbourhood.CIRCLE_RADIUS_5, Rule.BACTERIA_2, Rule.BACTERIA_3);

        Neighbourhood mask_01 = Neighbourhood.load("masks/mask_01.png");
        Neighbourhood mask_02 = Neighbourhood.load("masks/mask_02.png");
        Neighbourhood mask_03 = Neighbourhood.load("masks/mask_03.png");

        Neighbourhood von_neumann = Neighbourhood.load("masks/von_neumann.png");
        Neighbourhood moore = Neighbourhood.load("masks/moore.png");

        System.out.println("moore" + moore.sumValue);

        space = new Space(128, 128, new HashMap<>() {{
            put(Rule.CONWAYS, moore);
        }});

        setPreferredSize(new Dimension(1024, 768));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        requestFocus();
        requestFocus();
        requestFocus();

        new Thread(() ->
        {
            long lastTime = System.nanoTime();
            long counter = 0;

            while (true)
            {
                long thisTime = System.nanoTime();
                counter += thisTime - lastTime;
                lastTime = thisTime;
                while (counter >= INTERVAL)
                {
                    counter -= INTERVAL;
                    if (running)
                    {
                        space.step();
                        repaint();
                    }
                }
            }

        }).start();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        space.draw((Graphics2D) g, SIZE);
    }

    private boolean painting = true;

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseCell = new Point(e.getX() / SIZE, e.getY() / SIZE);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        mouseCell = new Point(e.getX() / SIZE, e.getY() / SIZE);
        painting = space.toggle(mouseCell.x, mouseCell.y) > 0.5;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mouseCell = new Point(e.getX() / SIZE, e.getY() / SIZE);
        space.set(mouseCell.x, mouseCell.y, painting ? 1.0 : 0.0);
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_EQUALS)
        {
            System.out.println("STEP!");
            space.step();
            repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            running = !running;
    }






    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
    @Override
    public void keyTyped(KeyEvent e) { }
    @Override
    public void keyReleased(KeyEvent e) { }
}
