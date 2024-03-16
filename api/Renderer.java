package com.gameengine.api;

import com.gameengine.api.graphics.Material;
import com.gameengine.api.graphics.Shader;
import com.gameengine.api.graphics.Texture;
import com.gameengine.api.graphics.TextureAsset;
import com.gameengine.api.math.MathUtils;
import com.gameengine.api.math.Matrix4;

public class Renderer {

 static Texture defaultTexture;
 static CameraHolder cameraHolder;
 static Shader lastShader;
 static Material defaultMaterial;


 static void update() {}

 public static void drawTexture(float x, float y, float width, float height, float rotation, Texture texture, com.gameengine.api.graphics.Color tint, Material material) {}



 public static Texture getDefaultTexture() {return null;}

 
public static void drawFilledRect(float x, float y, float width, float height, float rotation, com.gameengine.api.graphics.Color color) {}

 public static void drawRect(float x, float y, float width, float height, float rotation, com.gameengine.api.graphics.Color color, float strokeWidth) {}

 public static void drawCircle(float x, float y, float radius, com.gameengine.api.graphics.Color color, float strokeWidth) {}

 public static void drawFilledCircle(float x, float y, float radius, com.gameengine.api.graphics.Color color) {}

 public static void drawPolygon(float[] vertices, com.gameengine.api.graphics.Color color, float strokeWidth) {}

}
