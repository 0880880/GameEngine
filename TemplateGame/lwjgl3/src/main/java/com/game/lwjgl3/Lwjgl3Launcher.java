package com.game.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.game.Main;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowAdapter;
import com.badlogic.gdx.utils.Json;
import com.gameengine.api.SerializableProject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    private static boolean checkFile(String file) {
        return Lwjgl3Launcher.class.getResource(file) != null;
    }

    private static String getFileName(String path) {
        return path.substring(path.lastIndexOf("\\")+1, path.length());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Game");
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
        configuration.setWindowedMode(640, 480);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("main.json");
        if (is != null) {
            try {
                SerializableProject proj = new Json().fromJson(SerializableProject.class, new String(is.readAllBytes(), Charset.defaultCharset()));

                ArrayList<String> icons = new ArrayList<>();

                if (!proj.windowIcon16.isBlank()) {
                    String icon = getFileName(proj.windowIcon16);
                    if (checkFile(icon)) icons.add(icon);
                }
                if (!proj.windowIcon32.isBlank()) {
                    String icon = getFileName(proj.windowIcon32);
                    if (checkFile(icon)) icons.add(icon);
                }
                if (!proj.windowIcon64.isBlank()) {
                    String icon = getFileName(proj.windowIcon64);
                    if (checkFile(icon)) icons.add(icon);
                }
                if (!proj.windowIcon128.isBlank()) {
                    String icon = getFileName(proj.windowIcon128);
                    if (checkFile(icon)) icons.add(icon);
                }

                configuration.setWindowIcon(icons.toArray(new String[0]));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // configuration.setWindowIcon(Files.FileType.Absolute, ); Window Icon
        return configuration;
    }
}
