package com.gameengine.api.graphics;

import com.gameengine.api.Renderer;
import com.gameengine.api.ShaderLanguage;
import com.gameengine.api.math.Matrix3;
import com.gameengine.api.math.Matrix4;
import com.gameengine.api.math.Vector2;
import com.gameengine.api.math.Vector3;

import java.util.HashMap;
import java.util.HashSet;


public class Material
{

 public String materialFile;
 public String materialSource;

 
 public Shader shader;
 public String shaderFile;

 public HashMap<String, Object> variableValueMap = new HashMap<>();
 public HashSet<ShaderLanguage.Variable> variableSet = new HashSet<>();

 public Material() {}


 public void set(String name, int i) {}

 public void set(String name, float i) {}

 public void set(String name, Vector2 vec) {}

 public void set(String name, Vector3 vec) {}

 public void set(String name, Color col) {}

 public void set(String name, Matrix3 mat) {}

 public void set(String name, Matrix4 mat) {}

 public void set(String name, Texture tex) {}

 public String save() {return null;}

 
public void update() {}


 public void bind() {}



}
