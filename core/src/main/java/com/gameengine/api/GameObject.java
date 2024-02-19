package com.gameengine.api;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.gameengine.Statics;
import com.gameengine.api.components.Transform;
import com.gameengine.utils.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static com.gameengine.Statics.engine;

public class GameObject implements Cloneable
    , Json.Serializable
{

    public static long counter = 0;

    public String name;
    public long ID = counter++;

    public ArrayList<Component> components = new ArrayList<>();

    public ArrayList<GameObject> children = new ArrayList<>();

    public GameObject parent; // Ignore
    public int parentID;

    public Transform transform = new Transform(); // Ignore

    public transient boolean renameMode = false; // Ignore

    public GameObject() {

    }

    public GameObject(String name) {
        this.name = name;
        addComponent(transform);
    }

    void start() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).start();
        }
        for (int i = 0; i < children.size(); i++) {
            children.get(i).start();
        }
    }

    void update() {
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            if (Statics.isGameRunning)
                component.update();
            else
                component.debugUpdate();
        }
        for (int i = 0; i < children.size(); i++) {
            children.get(i).update();
        }
    }

    void fixedUpdate() {
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            if (Statics.isGameRunning) component.fixedUpdate();
        }
        for (int i = 0; i < children.size(); i++) {
            children.get(i).fixedUpdate();
        }
    }

    void lateUpdate() {
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            if (Statics.isGameRunning) component.lateUpdate();
        }
        for (int i = 0; i < children.size(); i++) {
            children.get(i).lateUpdate();
        }
    }

    public void addGameObject(GameObject child) {
        child.parent = this;
        children.add(child);
        Statics.allGameObjects.add(child);
    }

    public void addComponent(Component component) {
        component.className = component.getClass().getSimpleName();
        components.add(component);
        engine.initComponent(component);
        component.gameObject = this;
    }

    public void addComponent(Class<? extends Component> componentClass) {
        try {
            addComponent(componentClass.getDeclaredConstructor().newInstance());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public GameObject clone() {
        GameObject object;
        try {
            object = (GameObject) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        object.name = this.name;
        for (Component component : components) {

            boolean builtin = Component.isComponentBuiltin(component.getClass());

            Component cmp;

            if (builtin) {
                Class<?> c = Class.forName(Transform.class.getModule(), component.getClass().getSimpleName());
                try {
                    cmp = (Component) c.getConstructor().newInstance();
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            } else {
                cmp = Utils.getComponent(component.getClass().getSimpleName());
            }

            object.addComponent(cmp);

            for (Field f : component.fields) {
                try {
                    f.set(cmp, f.get(component));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        for (GameObject child : children) {
            object.addGameObject(child.clone());
        }
        object.transform = object.getComponent(Transform.class);
        this.parent.addGameObject(object);
        return object;
    }

    public boolean hasComponent(Class<? extends Component> componentClass) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).getClass() == componentClass)
                return true;
        }
        return false;
    }

    public <T> T getComponent(Class<T> componentClass) {
        for (Component component : components) {
            if (component.getClass() == componentClass)
                return (T) component;
        }
        return null;
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }

    @Override public void write(Json json) {
        json.writeValue("name", name);
        json.writeValue("id", ID);
        json.writeValue("components", components);
        json.writeValue("children", children);
        json.writeValue("parent_id", parentID);
    }

    @Override public void read(Json json, JsonValue jsonData) {
        name = json.readValue("name", String.class, jsonData);
        ID = json.readValue("id", Long.class, jsonData);
        components = json.readValue("components", ArrayList.class, jsonData);
        children = json.readValue("children", ArrayList.class, jsonData);
        parentID = json.readValue("parent_id", Integer.class, jsonData);
        parent = Utils.getGameObject(parentID);
    }

}
