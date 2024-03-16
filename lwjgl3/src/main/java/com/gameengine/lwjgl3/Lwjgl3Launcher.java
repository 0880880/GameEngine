package com.gameengine.lwjgl3;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowAdapter;
import com.badlogic.gdx.utils.Json;
import com.gameengine.Main;
import com.gameengine.api.SerializableProject;
import com.gameengine.utils.Utils;
import org.lwjgl.opengl.GL45C;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        try {
            createApplication();
        } catch (RuntimeException e) {
            Utils.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private static Lwjgl3Application createApplication() {
        Main main = new Main();
        return new Lwjgl3Application(main, getDefaultConfiguration(main));
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration(Main main) {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Game Engine");
        configuration.setWindowListener(new Lwjgl3WindowAdapter() {
            @Override
            public boolean closeRequested() {
                main.windowListener.close();
                return super.closeRequested();
            }
        });
        configuration.disableAudio(true);
        configuration.useVsync(true);
        configuration.setMaximized(true);

        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
        configuration.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.GL30, 3, 3);
        return configuration;
    }
}
