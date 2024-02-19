package com.gameengine.api;

import com.gameengine.api.components.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;


@NoArgsConstructor
public class SerializableGameObject {

 public String name;
 public long ID;


 public HashMap<String, HashMap<String, Object>> componentFields = new HashMap<>();


 boolean isBuiltin(Class<? extends Component> component) {}

 public SerializableGameObject(GameObject gameObject) {}

 public GameObject createGameObject(GameObject parent) {return null;}


}
