package com.gameengine.api.math;

public class MathUtils {

    public static final float PI = 3.1415927410125732421875f;
    public static final float FLOAT_ROUNDING_ERROR = 0.000001f;
    public static final float HALF_PI = PI / 2f;
    public static final float PI2 = PI * 2f;

    public static final float E = (float) Math.E;

    public static final float radiansToDegrees = 180f * (1 / PI);
    public static final float degreesToRadians = (1 / 180f) * PI;
    public static final float radToDeg = 180f * (1 / PI);
    public static final float degToRad = (1 / 180f) * PI;

    public static float radToDeg(float val) { return val * radToDeg; }
    public static float degToRad(float val) { return val * degToRad; }

    private MathUtils() {}

    // MIN

    public static int min(int a, int b) {
        return Math.min(a, b);
    }

    public static float min(float a, float b) {
        return Math.min(a, b);
    }

    public static double min(double a, double b) {
        return Math.min(a, b);
    }

    public static long min(long a, long b) {
        return Math.min(a, b);
    }

    // MAX

    public static int max(int a, int b) {
        return Math.max(a, b);
    }

    public static float max(float a, float b) {
        return Math.max(a, b);
    }

    public static double max(double a, double b) {
        return Math.max(a, b);
    }

    public static long max(long a, long b) {
        return Math.max(a, b);
    }

    public static float sin(float x) {
        return (float) Math.sin(x);
    }

    public static float cos(float x) {
        return (float) Math.cos(x);
    }

    public static float tan(float x) {
        return (float) Math.tan(x);
    }

    public static float atan(float x) {
        return (float) Math.atan(x);
    }

    public static float atan2(float y, float x) {
        return (float) Math.atan2(y, x);
    }

    public static float sinDeg(float x) {
        return (float) Math.sin(degToRad(x));
    }

    public static float cosDeg(float x) {
        return (float) Math.cos(degToRad(x));
    }

    public static float tanDeg(float x) {
        return (float) Math.tan(degToRad(x));
    }

    public static float atanDeg(float x) {
        return (float) Math.atan(degToRad(x));
    }

    public static float atan2Deg(float y, float x) {
        return (float) Math.atan2(degToRad(y), degToRad(x));
    }

    // CLAMP

    public static int clamp(int value, int min, int max) {
        return min(max(value, min), max);
    }

    public static long clamp(long value, long min, long max) {
        return min(max(value, min), max);
    }

    public static float clamp(float value, float min, float max) {
        return min(max(value, min), max);
    }

    public static double clamp(double value, double min, double max) {
        return min(max(value, min), max);
    }

    // LERP

    static public float lerp(float fromValue, float toValue, float progress) {
        return fromValue + (toValue - fromValue) * progress;
    }

    // ROUNDING

    static private final int BIG_ENOUGH_INT = 16 * 1024;
    static private final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;
    static private final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5f;

    public static int floor(float value) {
        return (int)(value + BIG_ENOUGH_FLOOR) - BIG_ENOUGH_INT;
    }

    public static int ceil(float value) {
        return BIG_ENOUGH_INT - (int)(BIG_ENOUGH_FLOOR - value);
    }

    public static int round(float value) {
        return (int)(value + BIG_ENOUGH_ROUND) - BIG_ENOUGH_INT;
    }

    public static float pow(float a, float b) {
        return (float) Math.pow(a, b);
    }

    public static int abs(int a) {
        return Math.abs(a);
    }

    public static float abs(float a) {
        return Math.abs(a);
    }

    public static boolean isEqual(float a, float b) {
        return a == b;
    }
    static public boolean isEqual (float a, float b, float tolerance) {
        return Math.abs(a - b) <= tolerance;
    }

    public static boolean isZero(float value) {
        return value == 0;
    }

    static public boolean isZero (float value, float tolerance) {
        return Math.abs(value) <= tolerance;
    }


}
