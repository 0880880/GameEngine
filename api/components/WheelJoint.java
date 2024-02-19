package com.gameengine.api.components;

import com.gameengine.api.Component;
import com.gameengine.api.physics.Physics;
import com.gameengine.api.math.Vector2;

public class WheelJoint extends Component {

 public RigidBody otherBody;
 public Vector2 anchor = new Vector2();
 public Vector2 axis = new Vector2();
 public float dampingRatio = 0;
 public float frequency = 0;
 public boolean enableMotor = false;
 public float maxMotorTorque = 0;
 public float motorSpeed = 0;


 private boolean initialize = true;

 public void fixedUpdate() {}

}
