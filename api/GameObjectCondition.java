package com.gameengine.api;

import com.gameengine.api.math.Vector2;

public class GameObjectCondition {

 public GameObject gameObject;

 public Vector2 position = new Vector2();
 public Vector2 scale = new Vector2();
 public float rotation;

 public GameObjectCondition(GameObject gameObject) {}

 public void apply() {}

}
