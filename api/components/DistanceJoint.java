package com.gameengine.api.components;

import com.gameengine.api.Component;
import com.gameengine.api.physics.Physics;
import com.gameengine.api.math.Vector2;

public class DistanceJoint extends Component {

 public RigidBody otherBody;
 public Vector2 anchorA = new Vector2();
 public Vector2 anchorB = new Vector2();
 public float length = 1;
 public float dampingRatio = 0;
 public float frequency = 0;


 private boolean initialize = true;

 public void fixedUpdate() {}

}
