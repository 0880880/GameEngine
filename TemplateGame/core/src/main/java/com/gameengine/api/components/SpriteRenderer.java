package com.gameengine.api.components;

import com.gameengine.api.Component;
import com.gameengine.api.Renderer;
import com.gameengine.api.graphics.Color;
import com.gameengine.api.graphics.Material;
import com.gameengine.api.graphics.Texture;

public class SpriteRenderer extends Component {

    public Texture texture = new Texture();
    public Color tint = new Color(Color.WHITE);
    public Material material;

    public void update() {
        Transform transform = gameObject.transform;
        if (transform != null) {
            Renderer.drawTexture(transform.position.x, transform.position.y, transform.scale.x, transform.scale.y, transform.rotation, texture, tint, material);
        }
    }

    public void debugUpdate() {
        update();
    }

}

