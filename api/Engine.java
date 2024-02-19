package com.gameengine.api;

import com.gameengine.api.audio.AudioClip;
import com.gameengine.api.components.Transform;
import com.gameengine.api.graphics.Material;
import com.gameengine.api.graphics.Shader;
import com.gameengine.api.graphics.TextureAsset;
import com.gameengine.api.physics.Physics;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Consumer;

public class Engine {


 TextureAsset defaultTextureAsset;

 public HashMap<String, TextureAsset> loadedTextures = new HashMap<>();
 public HashMap<String, AudioClip> loadedAudioClips = new HashMap<>();
 public HashMap<String, Shader> loadedShaders = new HashMap<>();
 public HashMap<String, Material> loadedMaterials = new HashMap<>();



 public Shader loadShader(String name, String path) {return null;}




 
HashMap<Long, HashMap<Class<? extends Component>, HashMap<String, Object>>> gameObjectsComponentsFieldMap = new HashMap<>();

 void copyComponentsFields(HashMap<Long, HashMap<Class<? extends Component>, HashMap<String, Object>>> map, GameObject parent) {}

 void addComponentsFields(HashMap<Long, HashMap<Class<? extends Component>, HashMap<String, Object>>> map, GameObject parent) {}

 public void addChildrenToICs(GameObject gameObject) {}

 public void applyICsToChildren() {}

 public void start(GameObject main, boolean ic) {}

 public void start() {}

 public void stop() {}

 private float accumulator = 0;
 private  float timeStep = 1/60f;


 public void update(GameObject main) {}

 public void update() {}

 public void initComponent(Component component) {}


 
 
 public static void setWindowedMode(int width, int height) {}

 public static void setFullscreen() {}

}
