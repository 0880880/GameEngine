package com.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import com.gameengine.api.Project;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gameengine.api.CameraHolder;
import com.gameengine.api.Engine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.gameengine.api.GameObject;
import com.gameengine.api.Project;
import com.gameengine.api.components.Camera;
import de.pottgames.tuningfork.Audio;

import java.security.SecureRandom;

public class Statics {

    public static Vector2 mouse = new Vector2();

    public static Project currentProject;

    public static Engine engine;

    public static OrthographicCamera editorCamera;

    public static Json json = new Json();

    public static Audio audio;
    public static Array<GameObject> allGameObjects = new Array<>();

    public static CameraHolder cameraHolder = new CameraHolder();

    public static boolean isGameRunning = true;

    public static SecureRandom random = new SecureRandom();

    public static GameObjectManager gameObjectManager;

}
