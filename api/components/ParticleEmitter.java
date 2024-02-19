package com.gameengine.api.components;

import com.gameengine.api.Component;
import com.gameengine.api.Curve;
import com.gameengine.api.Renderer;
import com.gameengine.api.Time;
import com.gameengine.api.graphics.Color;
import com.gameengine.api.graphics.Texture;
import com.gameengine.api.math.Vector2;

public class ParticleEmitter extends Component {



 public ParticleEffect particleEffect = new ParticleEffect();
 public Vector2 position = new Vector2();
 public boolean startOnAwake;

 public void start() {}

 public void update() {}

 public void emitterStart() {}

 public void emitterReset() {}

 public void setDelayActive(boolean active) {}

 public void setDelayLow(float min, float max) {}

 public void setEmissionActive(boolean active) {}

 public void setEmissionLow(float min, float max) {}

 public void setEmissionHigh(float min, float max) {}

 public void setEmissionCurve(Curve curve) {}

 public void setLifeLow(float min, float max) {}

 public void setLifeHigh(float min, float max) {}

 public void setLifeCurve(Curve curve) {}

 public void setLifeOffsetActive(boolean active) {}

 public void setLifeOffsetLow(float min, float max) {}

 public void setLifeOffsetHigh(float min, float max) {}

 public void setLifeOffsetCurve(Curve curve) {}

 public void setXOffsetActive(boolean active) {}

 public void setXOffsetLow(float min, float max) {}

 public void setYOffsetActive(boolean active) {}

 public void setYOffsetLow(float min, float max) {}

 public void setSpawnShape(ParticleEffect.SpawnShape spawnShape) {}

 public void setSpawnEllipseSide(ParticleEffect.SpawnEllipseSide spawnEllipseSide) {}

 public void setSpriteMode(ParticleEffect.SpriteMode spriteMode) {}

}
