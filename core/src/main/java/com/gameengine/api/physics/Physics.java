package com.gameengine.api.physics;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gameengine.api.Component;
import com.gameengine.api.GameObject;
import com.gameengine.api.components.Collider;
import com.gameengine.api.math.Vector2;
import com.gameengine.utils.Utils;

public class Physics {

    private static Vector2 gravity = new Vector2(0, -9.81f);
    private static World world;
    private static int velocityIterations = 6;
    private static int positionIterations = 2;

    public static void start() {
        world = new World(new com.badlogic.gdx.math.Vector2(gravity.x, gravity.y), true);
    }

    private interface OnCollisionFunction {
        void onCollision(Collision collision);
    }

    private static void enter(Fixture fixture) {
        int ID = (int) fixture.getBody().getUserData();
        GameObject gameObject = Utils.getGameObject(ID);
        Collider collider = null;
        for (Component component : gameObject.components) {
            if (component instanceof Collider) {
                Collider c = (Collider) component;
                if (c.getFixture().equals(fixture)) {
                    collider = c;
                    break;
                }
            }
        }
        for (Component component : gameObject.components) {
            component.onEnterCollision(new Collision(collider));
        }
    }

    private static void leave(Fixture fixture) {
        int ID = (int) fixture.getBody().getUserData();
        GameObject gameObject = Utils.getGameObject(ID);
        Collider collider = null;
        for (Component component : gameObject.components) {
            if (component instanceof Collider) {
                Collider c = (Collider) component;
                if (c.getFixture().equals(fixture)) {
                    collider = c;
                    break;
                }
            }
        }
        for (Component component : gameObject.components) {
            component.onLeaveCollision(new Collision(collider));
        }
    }

    public static void stop() {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            world.destroyBody(body);
        }
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                enter(contact.getFixtureA());
                enter(contact.getFixtureB());
            }

            @Override
            public void endContact(Contact contact) {
                leave(contact.getFixtureA());
                leave(contact.getFixtureB());
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) { }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) { }
        });
        bodies.clear();
        world.dispose();
    }

    public static void step(float timeStep) {
        world.step(timeStep, velocityIterations, positionIterations);
    }

    public static Body createBody(BodyDef bodyDef) {
        return world.createBody(bodyDef);
    }

    public static Joint createJoint(JointDef jointDef) {
        return world.createJoint(jointDef);
    }

    public static int getVelocityIterations() {
        return velocityIterations;
    }

    public static void setVelocityIterations(int velocityIterations) {
        Physics.velocityIterations = velocityIterations;
    }

    public static int getPositionIterations() {
        return positionIterations;
    }

    public static void setPositionIterations(int positionIterations) {
        Physics.positionIterations = positionIterations;
    }
}
