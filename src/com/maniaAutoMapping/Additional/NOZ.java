package com.maniaAutoMapping.Additional;

public class NOZ
{
    public static double NOTiming(double rate1, double rate2)
    {
        long z1 = Math.round(1/rate1);
        long z2 = Math.round(1/rate2);

        long z = NOD(z1, z2);

        return 1/z;
    }

    private static long NOD(long a, long b)
    {
        if(a == 0 || b == 0)
            return a+b;
        else {
            if (a > b) {
                return NOD(a % b, b);
            } else {
                return NOD(a, b % a);
            }
        }
    }
}
