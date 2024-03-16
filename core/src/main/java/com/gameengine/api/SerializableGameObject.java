package com.gameengine.api;

import com.gameengine.api.components.*;
import com.gameengine.utils.Utils;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.gameengine.Statics.allGameObjects;
import static com.gameengine.Statics.engine;

@NoArgsConstructor
public class SerializableGameObject {

    public String name;
    public long ID;

    public ArrayList<String> components = new ArrayList<>();

    public HashMap<String, HashMap<String, Object>> componentFields = new HashMap<>();

    public ArrayList<SerializableGameObject> children = new ArrayList<>();

    boolean isBuiltin(Class<? extends Component> component) {
        return component.getModule().equals(Transform.class.getModule());
    }

    public SerializableGameObject(GameObject gameObject) {
        this.name = gameObject.name;
        this.ID = gameObject.ID;
        for (Component component : gameObject.components) {
            String componentName = "user." + component.getClass().getSimpleName();

            if (isBuiltin(component.getClass()))
                componentName = "builtin." + component.getClass().getSimpleName();

            components.add(componentName);

            HashMap<String, Object> map = new HashMap<>();
            for (Field field : component.fields) {
                try {
                    Object obj = field.get(component);
                    if (obj instanceof Component) {
                        Component cmp = (Component) obj;
                        map.put(field.getName(), "cmp." + cmp.gameObject.ID + "." + cmp.className);
                    } else {
                        map.put(field.getName(), obj);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            componentFields.put(componentName, map);
        }
        for (GameObject go : gameObject.children) {
            children.add(new SerializableGameObject(go));
        }
    }

    public GameObject createGameObject(GameObject parent) {
        GameObject gameObject = new GameObject();
        gameObject.name = name;
        gameObject.ID = ID;
        gameObject.parent = parent;
        for (SerializableGameObject child : children) {
            GameObject o = child.createGameObject(gameObject);
            allGameObjects.add(o);
            gameObject.children.add(o);
        }
        for (String componentName : components) {
            if (componentName.startsWith("builtin.")) {
                try {
                    Class<? extends Component> cls = (Class<? extends Component>) Class.forName("com.gameengine.api.components." + componentName.substring(8));

                    Component cmp = cls.getConstructor().newInstance();

                    cmp.className = cmp.getClass().getSimpleName();
                    gameObject.components.add(cmp);
                    engine.initComponent(cmp);
                    cmp.gameObject = gameObject;


                    HashMap<String, Object> map = componentFields.get(componentName);

                    for (String fieldName : map.keySet()) {
                        for (Field f : cmp.fields) {
                            if (f.getName().equals(fieldName)) {
                                f.set(cmp, map.get(fieldName));
                                break;
                            }
                        }
                    }

                } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                         NoSuchMethodException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else if (componentName.startsWith("user.")) {
                try {
                    Component cmp = Utils.getComponent(componentName.substring(5));

                    HashMap<String, Object> map = componentFields.get(componentName);

                    for (String fieldName : map.keySet()) {
                        for (Field f : cmp.fields) {
                            if (f.getName().equals(fieldName)) {
                                f.set(cmp, map.get(fieldName));
                                break;
                            }
                        }
                    }

                    gameObject.addComponent(cmp);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (gameObject.hasComponent(Transform.class)) {
            gameObject.transform = gameObject.getComponent(Transform.class);
        }
        return gameObject;
    }

}
