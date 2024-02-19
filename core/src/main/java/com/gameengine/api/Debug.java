package com.gameengine.api;

import com.badlogic.gdx.utils.Array;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Debug {

    static Engine engine;

    private static Array<String> console;

    public static void init(Engine engine) {
        console = new Array<>();
    }

    public static void log(int i) {
        log(String.valueOf(i));
    }

    public static void log(float f) {
        log(String.valueOf(f));
    }

    public static void log(String s) {
        console.add("[OUT]" + s);
    }

    public static void log(boolean b) {
        log(String.valueOf(b));
    }

    public static void log(Object obj) {
        log(String.valueOf(obj));
    }

    public static void error(String s) {
        console.add("[ERR]" + s);
    }

    public static Array<String> readDebug() {
        return console;
    }

}
