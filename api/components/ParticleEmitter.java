package com.gameengine.api.components;

import com.gameengine.api.*;
import com.gameengine.api.graphics.Color;
import com.gameengine.api.graphics.Texture;
import com.gameengine.api.math.MathUtils;
import com.gameengine.api.math.Vector2;


public class ParticleEmitter extends Component {

 private float[] getTimeline(Curve curve) {return null;}

 
private float[] getTimeline(Curve curve, float[] timeline) {return null;}

 
private float[] getScaling(Curve curve) {return null;}

 
private float[] getScaling(Curve curve, float[] scaling) {return null;}



 
public ParticleEffect particleEffect = new ParticleEffect();
 public Vector2 position = new Vector2();
 public float rotation = 0;
 public boolean startOnAwake;

 public void start() {}

 public void update() {}

 public void startEmitter() {}

 public void resetEmitter() {}

}
