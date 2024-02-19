package com.game;

import com.gameengine.api.Component;
import java.lang.reflect.InvocationTargetException;
import com.gameengine.api.GameObject;
import static com.game.Statics.gameObjectManager;

public class Utils {

    public static Component getComponent(String componentName) {
        Component component;
        Class<? extends Component> cls;
        try {
            cls = (Class<? extends Component>) Class.forName("com.game.scripts." + componentName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            component = cls.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return component;
    }

    public static Class<?> getClass(String componentName) {
        return getComponent(componentName).getClass();
    }

    public static void compile() {
        System.out.println("COMPILED");
    }

    private static GameObject search(GameObject parent, int ID) {
        for (GameObject gameObject : parent.children) {
            if (gameObject.ID == ID) {
                return gameObject;
            } else {
                return search(gameObject, ID);
            }
        }
        return null;
    }

    public static GameObject getGameObject(int ID) {
        return search(gameObjectManager.main, ID);
    }

}
