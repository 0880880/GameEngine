package com.gameengine.api.components;

import com.gameengine.api.Button;
import com.gameengine.api.Component;
import com.gameengine.api.Debug;
import com.gameengine.api.Renderer;
import com.gameengine.api.graphics.Color;
import com.gameengine.api.math.Vector2;
import com.gameengine.api.physics.Physics;

import java.util.List;

public class PolygonCollider extends Component implements Collider {

 public float density = 1;
 public float friction = .5f;
 public float restitution = 0;
 public boolean isSensor = false;
 public PhysicsFilter filter = new PhysicsFilter();


 public int ID;

 public void start() {}



 private float[] verticesArr = null;

 public void debugUpdate() {}

 @Override
 public int hashCode() {return 0;}

}
