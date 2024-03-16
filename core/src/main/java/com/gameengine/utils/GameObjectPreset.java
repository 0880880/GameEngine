package com.gameengine.utils;

import com.gameengine.api.GameObject;

public interface GameObjectPreset {

    GameObject create(String name, GameObject parent);

}
