package com.danzabarr.cellularautomata;

/**
 * Represents a rule to be used in a cellular automaton.
 */
public interface Rule
{
    double test(double current, double sum, double average);

    Rule LIFE = (current, sum, average) ->

            average >= 0.262364076538086 && average <= 0.342407354125977 ? current * 1.500 + 0.01 :
            average >= 0.342407354125977 && average <= 0.382428992919922 ? current * 0.500 + 0.00 :
            average >= 0.382428992919922 && average <= 0.489153363037109 ? current * 1.250 + 0.01 :
            average >= 0.489153363037109 && average <= 0.538068699340820 ? current * 0.125 + 0.00 :
            current;

    Rule LIFE_2 = (current, sum, average) ->

            average >= 0.262364076538086 && average <= 0.342407354125977 ? current * 0.500 + 0.01 :
            average >= 0.342407354125977 && average <= 0.382428992919922 ? current * 0.500 + 0.00 :
            average >= 0.382428992919922 && average <= 0.489153363037109 ? current * 1.250 + 0.01 :
            average >= 0.489153363037109 && average <= 0.538068699340820 ? current * 0.125 + 0.00 :
            current;

    Rule CONWAYS = (current, sum, average) ->
    {
        boolean active = current > 0.5;
        int count = (int) sum;

        return count == 3 || active && count == 2 ? 1 : 0;
    };
}
