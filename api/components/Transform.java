package com.gameengine.api.components;

import com.gameengine.api.Component;
import com.gameengine.api.math.Vector2;

public class Transform extends Component {

 public Vector2 position = new Vector2();
 public Vector2 localPosition = new Vector2();
 public Vector2 scale = new Vector2();
 public float rotation = 0;

}
