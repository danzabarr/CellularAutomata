package com.danzabarr.cellularautomata;

public interface RationalRule
{
    double test(double current, double sum, double average);

    RationalRule LIFE = (current, sum, average)

            -> average >= 0.262364076538086 && average <= 0.342407354125977 ? current * 1.5 + 0.01 :
            average >= 0.342407354125977 && average <= 0.382428992919922 ? current * 0.5:
            average >= 0.382428992919922 && average <= 0.489153363037109 ? current * 1.25 + 0.01 :
            average >= 0.489153363037109 && average <= 0.53806869934082 ? current * 0.125 :
            current;
}
