package com.gameengine.api;

import com.gameengine.api.graphics.Color;
import com.gameengine.api.math.MathUtils;
import com.gameengine.api.math.Vector2;

import java.util.List;

public class ColorRange {


 public ColorRange() {}

 public ColorRange(float[] timeline, Color... colors) {}

 public void add(float t, Color color) {}

 public void remove(int index) {}

 public void insert(int index, float time, Color color) {}

 public float getTime(int index) {return 0.0f;}

 
public void setTime(int index, float time) {}

 public Color getColor(int index) {return null;}

 
public Color valueAt(Color color, float t) {return null;}

 
public int size() {return 0;}



}
