package com.gameengine.api.components;

import com.gameengine.api.Component;
import com.gameengine.api.Renderer;
import com.gameengine.api.graphics.Color;
import com.gameengine.api.graphics.Material;
import com.gameengine.api.graphics.Texture;

public class Renderable extends Component {

 public Texture texture = new Texture();
 public Color tint = new Color(Color.WHITE);
 public Material material;

 public void update() {}

 public void debugUpdate() {}

}
