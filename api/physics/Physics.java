package com.gameengine.api.physics;

import com.gameengine.api.Component;
import com.gameengine.api.GameObject;
import com.gameengine.api.components.Collider;
import com.gameengine.api.math.Vector2;

public class Physics {

 private static Vector2 gravity = new Vector2(0, -9.81f);
 private static int velocityIterations = 6;
 private static int positionIterations = 2;

 public static void start() {}

 private interface OnCollisionFunction {}



 public static void stop() {}

 public static void step(float timeStep) {}



 public static int getVelocityIterations() {return 0;}

 
public static void setVelocityIterations(int velocityIterations) {}

 public static int getPositionIterations() {return 0;}

 
public static void setPositionIterations(int positionIterations) {}
}
