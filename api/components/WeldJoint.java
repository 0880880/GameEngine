package com.gameengine.api.components;

import com.gameengine.api.Component;
import com.gameengine.api.physics.Physics;
import com.gameengine.api.math.Vector2;

;

public class WeldJoint extends Component {

 public RigidBody otherBody;
 public Vector2 anchor = new Vector2();
 public float dampingRatio = 0;
 public float frequency = 0;


 private boolean initialize = true;

 public void fixedUpdate() {}

}
