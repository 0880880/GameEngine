package com.gameengine.api;

import com.badlogic.gdx.utils.Array;
import com.gameengine.api.components.Transform;
import com.gameengine.api.physics.Collision;

import java.lang.reflect.Field;

public abstract class Component {

    String className;
    Engine engine;

    public GameObject gameObject;

    public Array<Field> fields = new Array<>();

    public static boolean isComponentBuiltin(Class<?> componentClass) {
        return componentClass.getModule().equals(Transform.class.getModule());
    }

    void initialize(Engine engine) {
        this.engine = engine;
        for (Field field : this.getClass().getFields()) {
            if (!field.getName().equals("className") && !field.getName().equals("engine") && !field.getName().equals("gameObject") && !field.getName().equals("fields"))
                fields.add(field);
        }
    }

    public void start() {}

    public void awake() {}

    public void update() {}

    public void debugUpdate() {}

    public void fixedUpdate() {}

    public void lateUpdate() {}

    public void onEnterCollision(Collision collision) {}

    public void onLeaveCollision(Collision collision) {}

}

