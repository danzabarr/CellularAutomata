package com.danzabarr.cellularautomata;

public interface Rule
{
    boolean test(boolean active, int count, double average);

    Rule CONWAYS = (active, count, average)

            -> count == 3 || active && count == 2;


    Rule MAZE = (active, count, average)

            -> count == 3 || active && count >= 1 && count <= 5;


    Rule SOLVE_MAZE = (active, count, average)

            -> count == 3 || active;


    Rule BACTERIA = (active, count, average)

        -> average >= 0.262364076538086 && average <= 0.902710297241211 ? true :
            average >= 0.876029204711914 && average <= 0.764857985839844 ? false :
            average >= 0.533621850585938 && average <= 0.911603994750977 ? false :
            average >= 0.787092229614258 && average <= 0.449131724243164 ? true :
            active;

    Rule BACTERIA_2 = (active, count, average)

            -> average >= 0.342407354125977 && average <= 0.382428992919922 ? true :
                average >= 0.755964288330078 && average <= 0.53806869934082 ? true :
                average >= 0.195661345214844 && average <= 0.217895588989258 ? false :
                average >= 0.671474161987305 && average <= 0.489153363037109 ? true :
                active;

    Rule BACTERIA_3 = (active, count, average)

            -> average >= 0.262364076538086 && average <= 0.342407354125977 ? true :
                average >= 0.342407354125977 && average <= 0.382428992919922 ? false :
                average >= 0.382428992919922 && average <= 0.489153363037109 ? true :
                average >= 0.489153363037109 && average <= 0.53806869934082 ? false :
                active;
}


