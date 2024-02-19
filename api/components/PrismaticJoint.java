package com.gameengine.api.components;

import com.gameengine.api.Component;
import com.gameengine.api.physics.Physics;
import com.gameengine.api.math.Vector2;

public class PrismaticJoint extends Component {

 public RigidBody otherBody;
 public Vector2 anchor = new Vector2();
 public Vector2 axis = new Vector2(Vector2.Y);
 public boolean enableLimit = false;
 public float lowerTranslation = 0;
 public float upperTranslation = 1;
 public boolean enableMotor = false;
 public float maxMotorForce = 0;
 public float motorSpeed = 0;


 private boolean initialize = true;

 public void fixedUpdate() {}

}
