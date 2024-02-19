package com.gameengine.api.components;

import com.gameengine.api.Component;
import com.gameengine.api.physics.Physics;
import com.gameengine.api.math.Vector2;

;

public class RopeJoint extends Component {

 public RigidBody otherBody;
 public Vector2 anchorA = new Vector2();
 public Vector2 anchorB = new Vector2();
 public float maxLength = 1;

 private boolean initialize = true;

 public void fixedUpdate() {}

}
