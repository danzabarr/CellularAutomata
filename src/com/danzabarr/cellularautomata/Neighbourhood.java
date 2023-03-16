package com.danzabarr.cellularautomata;

import java.awt.*;

public class Neighbourhood
{
    private Neighbourhood()
    {
    }

    public static final Point[] VON_NEUMANN = new Point[]
            {
                    new Point(1, 0),
                    new Point(0, -1),
                    new Point(-1, 0),
                    new Point(0, 1)
            };

    public static final Point[] HEXAGONAL = new Point[]
            {
                    new Point(1, 0),
                    new Point(0, -1),
                    new Point(-1, -1),
                    new Point(-1, 0),
                    new Point(0, 1),
                    new Point(1, 1)
            };

    public static final Point[] MOORE = new Point[]
            {
                    new Point(1, 0),
                    new Point(1, -1),
                    new Point(0, -1),
                    new Point(-1, -1),
                    new Point(-1, 0),
                    new Point(-1, 1),
                    new Point(0, 1),
                    new Point(1, 1)
            };

    public static final Point[] CIRCLE_RADIUS_5 = new Point[]
            {
                    new Point(0, 0),
                    new Point(0, 1),
                    new Point(0, -1),
                    new Point(1, 0),
                    new Point(-1, 0),
                    new Point(1, 1),
                    new Point(-1, 1),
                    new Point(1, -1),
                    new Point(-1, -1),
                    new Point(2, 0),
                    new Point(-2, 0),
                    new Point(0, 2),
                    new Point(0, -2),
                    new Point(2, 1),
                    new Point(-2, 1),
                    new Point(2, -1),
                    new Point(-2, -1),
                    new Point(1, 2),
                    new Point(-1, 2),
                    new Point(1, -2),
                    new Point(-1, -2),
                    new Point(3, 0),
                    new Point(-3, 0),
                    new Point(0, 3),
                    new Point(0, -3),
                    new Point(3, 1),
                    new Point(-3, 1),
                    new Point(3, -1),
                    new Point(-3, -1),
                    new Point(1, 3),
                    new Point(-1, 3),
                    new Point(1, -3),
                    new Point(-1, -3),
                    new Point(4, 0),
                    new Point(-4, 0),
                    new Point(0, 4),
                    new Point(0, -4),
                    new Point(4, 1),
                    new Point(-4, 1),
                    new Point(4, -1),
                    new Point(-4, -1),
                    new Point(1, 4),
                    new Point(-1, 4),
                    new Point(1, -4),
                    new Point(-1, -4),
                    new Point(2, 3),
                    new Point(-2, 3),
                    new Point(2, -3),
                    new Point(-2, -3),
                    new Point(3, 2),
                    new Point(-3, 2),
                    new Point(3, -2),
                    new Point(-3, -2),
                    new Point(5, 0),
                    new Point(-5, 0),
                    new Point(0, 5),
                    new Point(0, -5),
                    new Point(5, 1),
                    new Point(-5, 1),
                    new Point(5, -1),
                    new Point(-5, -1),
                    new Point(1, 5),
                    new Point(-1, 5),
                    new Point(1, -5),
                    new Point(-1, -5)
            };
}
