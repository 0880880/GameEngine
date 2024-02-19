package com.gameengine.api.components;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public interface Collider {

    Fixture createFixture(Body body);

    Fixture getFixture();

    public class PhysicsFilter {

        public short categoryBits = 1;
        public short maskBits = -1;
        public short groupIndex;

    }
}

