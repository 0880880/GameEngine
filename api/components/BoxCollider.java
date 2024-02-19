package com.gameengine.api.components;

import com.gameengine.api.Component;
import com.gameengine.api.Renderer;
import com.gameengine.api.graphics.Color;
import com.gameengine.api.math.Vector2;

public class BoxCollider extends Component implements Collider {

 public Vector2 size = new Vector2(1,1);
 public Vector2 center = new Vector2(0,0);
 public float angle = 0;
 public float density = 1;
 public float friction = .5f;
 public float restitution = 0;
 public boolean isSensor = false;
 public PhysicsFilter filter = new PhysicsFilter();




 public void debugUpdate() {}

}
