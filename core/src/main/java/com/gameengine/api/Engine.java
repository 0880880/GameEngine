package com.gameengine.api;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.gameengine.Statics;
import com.gameengine.api.audio.AudioClip;
import com.gameengine.api.components.Transform;
import com.gameengine.api.graphics.Material;
import com.gameengine.api.graphics.Shader;
import com.gameengine.api.graphics.TextureAsset;
import com.gameengine.api.math.*;
import com.gameengine.api.physics.Physics;
import com.gameengine.utils.Utils;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class Engine {

    CpuSpriteBatch batch;
    ShapeDrawer drawer;
    ShaderProgram defaultShader;

    TextureAsset defaultTextureAsset;

    public HashMap<String, TextureAsset> loadedTextures = new HashMap<>();
    public HashMap<String, AudioClip> loadedAudioClips = new HashMap<>();
    public HashMap<String, Shader> loadedShaders = new HashMap<>();
    public HashMap<String, Material> loadedMaterials = new HashMap<>();

    public TextureAsset loadTexture(String name, FileHandle path) {
        if (!loadedTextures.containsKey(name)) loadedTextures.put(name, new TextureAsset(path.path(), path.name(), new com.badlogic.gdx.graphics.Texture(path)));
        return loadedTextures.get(name);
    }

    public Shader loadShader(String name, FileHandle path) {
        if (!loadedShaders.containsKey(name)) loadedShaders.put(name, new Shader(path, path.readString()));
        return loadedShaders.get(name);
    }

    public Shader loadShader(String name, String path) {
        if (!loadedShaders.containsKey(name)) loadedShaders.put(name, new Shader(Gdx.files.absolute(path), Gdx.files.absolute(path).readString()));
        return loadedShaders.get(name);
    }

    public Material loadMaterial(String name, FileHandle path) {
        if (!loadedMaterials.containsKey(name)) loadedMaterials.put(name, new Material(path, path.readString()));
        return loadedMaterials.get(name);
    }

    public AudioClip loadAudio(String name, FileHandle path) {
        if (!loadedAudioClips.containsKey(name)) {
            AudioClip clip = new AudioClip();
            clip.soundFile = path.path();
            clip.init();
            loadedAudioClips.put(name, clip);
        }
        return loadedAudioClips.get(name);
    }

    public Engine(Sprite sprite, ShapeDrawer drawer, CameraHolder cameraHolder) {
        Statics.allGameObjects.clear();
        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        defaultTextureAsset = new TextureAsset("internal", "NONAME", new com.badlogic.gdx.graphics.Texture(pixmap));
        batch = (CpuSpriteBatch) drawer.getBatch();
        this.drawer = drawer;

        Material defaultMaterial = new Material();
        defaultMaterial.shader = loadShader("internal/default.sha", Gdx.files.internal("default.sha"));
        defaultMaterial.update();

        Renderer.init(batch, drawer, sprite, defaultTextureAsset, cameraHolder, defaultMaterial);
        Input.init();
        defaultShader = new ShaderProgram(batch.getShader().getVertexShaderSource(), batch.getShader().getFragmentShaderSource());
        Debug.init(this);
    }

    ArrayList<GameObjectCondition> initialConditions;
    HashMap<Long, HashMap<Class<? extends Component>, HashMap<String, Object>>> gameObjectsComponentsFieldMap = new HashMap<>();

    public Object copyObject(Object object, Class<?> c) {
        if (c == Vector2.class)
            object = ((Vector2) object).cpy();
        else if (c == Vector3.class)
            object = ((Vector3) object).cpy();
        else if (c == Affine2.class)
            object = new Affine2((Affine2) object);
        else if (c == Matrix3.class)
            object = new Matrix3((Matrix3) object);
        else if (c == Matrix4.class)
            object = new Matrix4((Matrix4) object);
        else if (c == Quaternion.class)
            object = ((Quaternion) object).cpy();
        else if (c == Color.class)
            object = ((Color) object).cpy();
        return object;
    }

    void copyComponentsFields(HashMap<Long, HashMap<Class<? extends Component>, HashMap<String, Object>>> map, GameObject parent) {
        for (GameObject gameObject : parent.children) {
            HashMap<Class<? extends Component>, HashMap<String, Object>> components = new HashMap<>();

            gameObject.components.forEach(component -> {
                HashMap<String, Object> fieldMap = new HashMap<>();

                for (Field f : component.getClass().getFields()) {
                    try {
                        fieldMap.put(f.getName(), copyObject(f.get(component), f.getType()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                components.put(component.getClass(), fieldMap);
            });

            map.put(gameObject.ID, components);
            copyComponentsFields(map, gameObject);
        }
    }

    void addComponentsFields(HashMap<Long, HashMap<Class<? extends Component>, HashMap<String, Object>>> map, GameObject parent) {
        for (GameObject gameObject : parent.children) {
            HashMap<Class<? extends Component>, HashMap<String, Object>> components = map.get(gameObject.ID);

            for (Class<? extends Component> key : components.keySet()) {

                HashMap<String, Object> fieldMap = components.get(key);

                boolean exists = false;
                for (int i = 0; i < gameObject.components.size(); i++) {
                    if (gameObject.components.get(i).getClass() == key) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    if (key.getModule() == Transform.class.getModule()) {
                        try {
                            gameObject.addComponent(key.getDeclaredConstructor().newInstance());
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                 NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        gameObject.addComponent(Utils.getComponent(key.getSimpleName()));
                    }
                }

                Component component = gameObject.getComponent(key);

                for (String fieldName : fieldMap.keySet()) {
                    try {
                        Field field = component.getClass().getField(fieldName);
                        field.set(component, fieldMap.get(fieldName));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            addComponentsFields(map, gameObject);
        }
    }

    public void addChildrenToICs(GameObject gameObject) {
        copyComponentsFields(gameObjectsComponentsFieldMap, gameObject);
    }

    public void applyICsToChildren() {
        addComponentsFields(gameObjectsComponentsFieldMap, Statics.currentProject.rootGameObject);
    }

    public void start(GameObject main, boolean ic) {
        Physics.start();
        if (ic) {
            initialConditions = new ArrayList<>();
            initialConditions.add(new GameObjectCondition(Statics.currentProject.rootGameObject));
            addChildrenToICs(Statics.currentProject.rootGameObject);
        }
        main.start();
    }

    public void start() {
        start(Statics.currentProject.rootGameObject, true);
    }

    public void stop() {
        applyICsToChildren();
        AudioManager.destroySources();
        initialConditions.clear();
        Physics.stop();
    }

    private float accumulator = 0;
    private final float timeStep = 1/60f;


    public void update(GameObject main) {
        float frameTime = Math.min(Gdx.graphics.getDeltaTime(), 0.25f);
        accumulator += frameTime;
        while (accumulator >= timeStep) {
            if (Statics.isGameRunning) main.fixedUpdate();
            if (Statics.isGameRunning) Physics.step(timeStep);
            accumulator -= timeStep;
        }

        Renderer.update();
        main.update();
        if (Statics.isGameRunning) main.lateUpdate();
        batch.setShader(defaultShader);
    }

    public void update() {
        update(Statics.currentProject.rootGameObject);
    }

    public void initComponent(Component component) {
        component.initialize(this);
    }


    // TODO
    // WIP
    public static void setWindowedMode(int width, int height) {
        Gdx.graphics.setWindowedMode(width, height);
    }

    public static void setFullscreen() {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }

}
