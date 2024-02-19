package com.gameengine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gameengine.api.CameraHolder;
import com.gameengine.api.Engine;
import com.gameengine.api.GameObject;
import com.gameengine.api.Project;
import com.gameengine.api.components.Camera;
import de.pottgames.tuningfork.Audio;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Statics {

    public static Game main;

    public static SpriteBatch batch;
    public static ShapeDrawer drawer;
    public static Audio audio;
    public static Gui gui;
    public static Preferences preferences;

    public static Engine engine;
    public static Array<GameObject> allGameObjects = new Array<>();

    public static Json json = new Json();

    public static CameraHolder cameraHolder = new CameraHolder();

    public static FrameBuffer fbo;

    public static Project currentProject;
    public static GameObject selectedGameObject;
    public static Object selection;

    public static JavaComponentLoader javaComponentLoader;

    public static boolean isGameRunning = false;

    public static int editMode;

    public static com.badlogic.gdx.math.Vector2 mouse = new com.badlogic.gdx.math.Vector2();

    public static ExtendViewport editorViewport = new ExtendViewport(19.2f,10.8f);
    public static OrthographicCamera editorCamera;
    public static Camera gameCamera;;

}
