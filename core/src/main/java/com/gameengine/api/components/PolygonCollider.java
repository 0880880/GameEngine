package com.gameengine.api.components;

import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.physics.box2d.*;
import com.gameengine.api.Button;
import com.gameengine.api.Component;
import com.gameengine.api.Renderer;
import com.gameengine.api.graphics.Color;
import com.gameengine.api.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class PolygonCollider extends Component implements Collider {

    public float density = 1;
    public float friction = .5f;
    public float restitution = 0;
    public boolean isSensor = false;
    public List<Vector2> vertices = new ArrayList<>();
    public PhysicsFilter filter = new PhysicsFilter();

    private Fixture fixture;

    public Fixture createFixture(Body body) {

        PolygonShape shape = new PolygonShape();
        com.badlogic.gdx.math.Vector2[] verts = new com.badlogic.gdx.math.Vector2[vertices.size()];
        for (int i = 0; i < verts.length; i++) {
            verts[i] = new com.badlogic.gdx.math.Vector2(vertices.get(i).x, vertices.get(i).y);
        }
        shape.set(verts);

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

    private float[] verticesArr = null;

    public void debugUpdate() {
        if (vertices.size() >= 3) {
            if (verticesArr == null || verticesArr.length != vertices.size()) {
                verticesArr = new float[vertices.size() * 2];
            }
            Vector2 position = gameObject.transform.position;
            for (int i = 0; i < vertices.size(); i += 2) {
                verticesArr[i] = vertices.get(i).x + position.x;
                verticesArr[i + 1] = vertices.get(i).y + position.y;
            }
            Renderer.drawDebugPolygon(verticesArr, Color.GREEN, 10);
        }
    }

}
