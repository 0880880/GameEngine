package com.gameengine.api;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gameengine.api.graphics.Material;
import com.gameengine.api.graphics.Shader;
import com.gameengine.api.graphics.Texture;
import com.gameengine.api.graphics.TextureAsset;
import com.gameengine.api.math.MathUtils;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Renderer {

    static SpriteBatch batch;
    static Sprite sprite;
    static ShapeDrawer drawer;
    static Texture defaultTexture;
    static CameraHolder cameraHolder;
    static Shader lastShader;
    static Material defaultMaterial;
    public transient static float debugScale = 0;

    static void init(SpriteBatch b, ShapeDrawer d, Sprite s, TextureAsset t, CameraHolder c, Material m) {
        batch = b;
        sprite = s;
        drawer = d;
        defaultTexture = new Texture(t);
        cameraHolder = c;
        defaultMaterial = m;
    }

    static void update() {
        if (cameraHolder.camera != null)
            Input.update(cameraHolder.camera);
        lastShader = null;
    }

    public static void drawTexture(float x, float y, float width, float height, float rotation, Texture texture, com.gameengine.api.graphics.Color tint, Material material) {
        Texture tex = texture.textureAsset != null ? texture : defaultTexture;

        sprite.setSize(width, height);
        sprite.setRotation(rotation);
        sprite.setColor(tint.r, tint.g, tint.b, tint.a);
        sprite.setPosition(x - width / 2f, y - height / 2f);
        sprite.setTexture(tex.textureAsset.getGdxTexture());
        if (sprite.getTexture() != null) sprite.setRegion(tex.uv0.x, tex.uv0.y, tex.uv1.x, tex.uv1.y);

        if (material != null && material.shader != null) {
            material.bind();
            try {
                batch.setShader(material.shader.getShaderProgram());
            } catch (IllegalArgumentException | IllegalStateException e) {
                Debug.error(e.getMessage());
            }
            lastShader = material.shader;
        } else {
            defaultMaterial.bind();
            try {
                batch.setShader(defaultMaterial.shader.getShaderProgram());
            } catch (IllegalArgumentException | IllegalStateException e) {
                Debug.error(e.getMessage());
            }
            lastShader = defaultMaterial.shader;
        }

        sprite.setOriginCenter();
        if (sprite.getTexture() != null) sprite.draw(batch);
    }

    public static void drawGdxEmitter(com.badlogic.gdx.graphics.g2d.ParticleEmitter particleEmitter) { particleEmitter.draw(batch); }

    public static Texture getDefaultTexture() {
        return defaultTexture;
    }

    public static void drawFilledRect(float x, float y, float width, float height, float rotation, com.gameengine.api.graphics.Color color) {
        drawer.setColor(color.r, color.g, color.b, color.a);
        drawer.filledRectangle(x - width / 2f, y - height / 2f, width, height, rotation * MathUtils.degToRad);
    }

    public static void drawRect(float x, float y, float width, float height, float rotation, com.gameengine.api.graphics.Color color, float strokeWidth) {
        drawer.setColor(color.r, color.g, color.b, color.a);
        drawer.rectangle(x - width / 2f, y - height / 2f, width, height, strokeWidth, rotation * MathUtils.degToRad);
        drawer.setColor(1, 1, 1, 1);
    }

    public static void drawDebugRect(float x, float y, float width, float height, float rotation, com.gameengine.api.graphics.Color color, float strokeWidth) {
        drawer.setColor(color.r, color.g, color.b, color.a);
        drawer.rectangle(x - width / 2f, y - height / 2f, width, height, debugScale * strokeWidth, rotation * MathUtils.degToRad);
        drawer.setColor(1, 1, 1, 1);
    }

    public static void drawCircle(float x, float y, float radius, com.gameengine.api.graphics.Color color, float strokeWidth) {
        drawer.setColor(color.r, color.g, color.b, color.a);
        drawer.circle(x, y, radius, strokeWidth);
        drawer.setColor(1, 1, 1, 1);
    }

    public static void drawFilledCircle(float x, float y, float radius, com.gameengine.api.graphics.Color color) {
        drawer.setColor(color.r, color.g, color.b, color.a);
        drawer.filledCircle(x, y, radius);
        drawer.setColor(1, 1, 1, 1);
    }

    public static void drawDebugCircle(float x, float y, float radius, com.gameengine.api.graphics.Color color, float strokeWidth) {
        drawer.setColor(color.r, color.g, color.b, color.a);
        drawer.circle(x, y, radius, debugScale * strokeWidth);
        drawer.setColor(1, 1, 1, 1);
    }

    public static void drawPolygon(float[] vertices, com.gameengine.api.graphics.Color color, float strokeWidth) {
        drawer.setColor(color.r, color.g, color.b, color.a);
        drawer.polygon(vertices, strokeWidth, JoinType.SMOOTH);
        drawer.setColor(1, 1, 1, 1);
    }

    public static void drawDebugPolygon(float[] vertices, com.gameengine.api.graphics.Color color, float strokeWidth) {
        drawer.setColor(color.r, color.g, color.b, color.a);
        drawer.polygon(vertices, debugScale * strokeWidth, JoinType.SMOOTH);
        drawer.setColor(1, 1, 1, 1);
    }

}

