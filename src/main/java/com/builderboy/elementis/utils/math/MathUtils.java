package com.builderboy.elementis.utils.math;

public class MathUtils {

    public static int square(int value) { return value*value; }
    public static long square(long value) { return value*value; }
    public static float square(float value) { return value*value; }
    public static double square(double value) { return value*value; }

    public static int cube(int value) { return value*value*value; }
    public static long cube(long value) { return value*value*value; }
    public static float cube(float value) { return value*value*value; }
    public static double cube(double value) { return value*value*value; }

    public static double distance(int a, int b) { return Math.sqrt(square(a) + square(b)); }
    public static double distance(long a, long b) { return Math.sqrt(square(a) + square(b)); }
    public static double distance(float a, float b) { return Math.sqrt(square(a) + square(b)); }
    public static double distance(double a, double b) { return Math.sqrt(square(a) + square(b)); }
}