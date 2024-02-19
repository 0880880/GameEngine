package com.gameengine.api;

import com.gameengine.api.components.Transform;
import com.gameengine.api.physics.Collision;

import java.lang.reflect.Field;

public abstract class Component {

 String className;
 Engine engine;

 public GameObject gameObject;


 public static boolean isComponentBuiltin(Class<?> componentClass) {return false;}

 
void initialize(Engine engine) {}

 public void start() {}

 public void awake() {}

 public void update() {}

 public void debugUpdate() {}

 public void fixedUpdate() {}

 public void lateUpdate() {}

 public void onEnterCollision(Collision collision) {}

 public void onLeaveCollision(Collision collision) {}

}
