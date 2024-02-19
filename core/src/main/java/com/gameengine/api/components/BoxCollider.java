package com.gameengine.api.components;

import com.badlogic.gdx.physics.box2d.*;
import com.gameengine.api.Component;
import com.gameengine.api.Renderer;
import com.gameengine.api.graphics.Color;
import com.gameengine.api.math.Vector2;

public class BoxCollider extends Component implements Collider {

    public Vector2 size = new Vector2(1,1);
    public Vector2 center = new Vector2(0,0);
    public float angle = 0;
    public float density = 1;
    public float friction = .5f;
    public float restitution = 0;
    public boolean isSensor = false;
    public PhysicsFilter filter = new PhysicsFilter();

    private Fixture fixture;

    public Fixture createFixture(Body body) {

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 2f, size.y / 2f, new com.badlogic.gdx.math.Vector2(center.x, center.y), angle);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.isSensor = isSensor;
        fixtureDef.filter.categoryBits = filter.categoryBits;
        fixtureDef.filter.maskBits = filter.maskBits;
        fixtureDef.filter.groupIndex = filter.groupIndex;

        fixture = body.createFixture(fixtureDef);

        shape.dispose();

        return fixture;

    }

    public Fixture getFixture() {
        return fixture;
    }

    public void debugUpdate() {
        Vector2 position = gameObject.transform.position;
        Renderer.drawDebugRect(position.x + center.x, position.y + center.y, size.x, size.y, gameObject.transform.rotation + angle, Color.GREEN, 10);
    }

}
