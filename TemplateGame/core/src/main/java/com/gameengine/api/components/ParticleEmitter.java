package com.gameengine.api.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gameengine.api.Component;
import com.gameengine.api.Curve;
import com.gameengine.api.Renderer;
import com.gameengine.api.Time;
import com.gameengine.api.graphics.Color;
import com.gameengine.api.graphics.Texture;
import com.gameengine.api.math.MathUtils;
import com.gameengine.api.math.Vector2;

import java.util.ArrayList;

public class ParticleEmitter extends Component {

    private float[] getTimeline(Curve curve) {
        float[] scaling = new float[curve.size()];
        for (int i = 0; i < scaling.length; i++) {
            scaling[i] = curve.getPoint(i).x;
        }
        return scaling;
    }

    private float[] getTimeline(Curve curve, float[] timeline) {
        if (curve.size() != timeline.length) {
            timeline = new float[curve.size()];
        }
        for (int i = 0; i < timeline.length; i++) {
            timeline[i] = curve.getPoint(i).x;
        }
        return timeline;
    }

    private float[] getScaling(Curve curve) {
        float[] scaling = new float[curve.size()];
        for (int i = 0; i < scaling.length; i++) {
            scaling[i] = curve.getPoint(i).y;
        }
        return scaling;
    }

    private float[] getScaling(Curve curve, float[] scaling) {
        if (curve.size() != scaling.length) {
            scaling = new float[curve.size()];
        }
        for (int i = 0; i < scaling.length; i++) {
            scaling[i] = curve.getPoint(i).x;
        }
        return scaling;
    }

    private void setProperties() {
        gdxEmitter.getDelay().setActive(particleEffect.delayActive);
        gdxEmitter.getDelay().setLow(particleEffect.delayLowMin, particleEffect.delayLowMax);

        gdxEmitter.getDuration().setLow(particleEffect.durationLowMin, particleEffect.durationLowMax);

        gdxEmitter.setMinParticleCount(particleEffect.countMin);
        gdxEmitter.setMaxParticleCount(particleEffect.countMax);

        gdxEmitter.getEmission().setActive(particleEffect.emissionActive);
        gdxEmitter.getEmission().setLow(particleEffect.emissionLowMin, particleEffect.emissionLowMax);
        gdxEmitter.getEmission().setHigh(particleEffect.emissionHighMin, particleEffect.emissionHighMax);

        gdxEmitter.getLife().setLow(particleEffect.lifeLowMin, particleEffect.lifeLowMax);
        gdxEmitter.getLife().setHigh(particleEffect.lifeHighMin, particleEffect.lifeHighMax);
        gdxEmitter.getLife().setTimeline(getTimeline(particleEffect.life, gdxEmitter.getLife().getTimeline()));
        gdxEmitter.getLife().setScaling(getScaling(particleEffect.life, gdxEmitter.getLife().getScaling()));

        gdxEmitter.getLifeOffset().setActive(particleEffect.lifeOffsetActive);
        gdxEmitter.getLifeOffset().setLow(particleEffect.lifeOffsetLowMin, particleEffect.lifeOffsetLowMax);
        gdxEmitter.getLifeOffset().setHigh(particleEffect.lifeOffsetHighMin, particleEffect.lifeOffsetHighMax);
        gdxEmitter.getLifeOffset().setTimeline(getTimeline(particleEffect.lifeOffset, gdxEmitter.getLifeOffset().getTimeline()));
        gdxEmitter.getLifeOffset().setScaling(getScaling(particleEffect.lifeOffset, gdxEmitter.getLifeOffset().getScaling()));

        gdxEmitter.getXOffsetValue().setActive(particleEffect.xOffsetActive);
        gdxEmitter.getXOffsetValue().setLow(particleEffect.xOffsetLowMin, particleEffect.xOffsetLowMax);

        gdxEmitter.getYOffsetValue().setActive(particleEffect.yOffsetActive);
        gdxEmitter.getYOffsetValue().setLow(particleEffect.yOffsetLowMin, particleEffect.yOffsetLowMax);

        gdxEmitter.getSpawnShape().setShape(ParticleEffect.getGdxSpawnShape(particleEffect.spawnShape));
        if (particleEffect.spawnShape == ParticleEffect.SpawnShape.ELLIPSE) {
            gdxEmitter.getSpawnShape().setEdges(particleEffect.spawnEdges);
            gdxEmitter.getSpawnShape().setSide(ParticleEffect.getGdxSpawnEllipseSide(particleEffect.spawnEllipseSide));
        }

        gdxEmitter.getSpawnWidth().setLow(particleEffect.spawnWidthLowMin, particleEffect.spawnWidthLowMax);
        gdxEmitter.getSpawnWidth().setHigh(particleEffect.spawnWidthHighMin, particleEffect.spawnWidthHighMax);
        gdxEmitter.getSpawnWidth().setTimeline(getTimeline(particleEffect.spawnWidth, gdxEmitter.getSpawnWidth().getTimeline()));
        gdxEmitter.getSpawnWidth().setScaling(getScaling(particleEffect.spawnWidth, gdxEmitter.getSpawnWidth().getScaling()));

        gdxEmitter.getSpawnHeight().setLow(particleEffect.spawnHeightLowMin, particleEffect.spawnHeightLowMax);
        gdxEmitter.getSpawnHeight().setHigh(particleEffect.spawnHeightHighMin, particleEffect.spawnHeightHighMax);
        gdxEmitter.getSpawnHeight().setTimeline(getTimeline(particleEffect.spawnHeight, gdxEmitter.getSpawnHeight().getTimeline()));
        gdxEmitter.getSpawnHeight().setScaling(getScaling(particleEffect.spawnHeight, gdxEmitter.getSpawnHeight().getScaling()));

        gdxEmitter.getXScale().setLow(particleEffect.xScaleLowMin, particleEffect.xScaleLowMax);
        gdxEmitter.getXScale().setHigh(particleEffect.xScaleHighMin, particleEffect.xScaleHighMax);
        gdxEmitter.getXScale().setTimeline(getTimeline(particleEffect.xScale, gdxEmitter.getXScale().getTimeline()));
        gdxEmitter.getXScale().setScaling(getScaling(particleEffect.xScale, gdxEmitter.getXScale().getScaling()));

        gdxEmitter.getYScale().setLow(particleEffect.yScaleLowMin, particleEffect.yScaleLowMax);
        gdxEmitter.getYScale().setHigh(particleEffect.yScaleHighMin, particleEffect.yScaleHighMax);
        gdxEmitter.getYScale().setTimeline(getTimeline(particleEffect.yScale, gdxEmitter.getYScale().getTimeline()));
        gdxEmitter.getYScale().setScaling(getScaling(particleEffect.yScale, gdxEmitter.getYScale().getScaling()));

        gdxEmitter.getVelocity().setLow(particleEffect.velocityLowMin, particleEffect.velocityLowMax);
        gdxEmitter.getVelocity().setHigh(particleEffect.velocityHighMin, particleEffect.velocityHighMax);
        gdxEmitter.getVelocity().setTimeline(getTimeline(particleEffect.velocity, gdxEmitter.getVelocity().getTimeline()));
        gdxEmitter.getVelocity().setScaling(getScaling(particleEffect.velocity, gdxEmitter.getVelocity().getScaling()));

        gdxEmitter.getAngle().setLow(particleEffect.angleLowMin, particleEffect.angleLowMax);
        gdxEmitter.getAngle().setHigh(particleEffect.angleHighMin, particleEffect.angleHighMax);
        gdxEmitter.getAngle().setTimeline(getTimeline(particleEffect.angle, gdxEmitter.getAngle().getTimeline()));
        gdxEmitter.getAngle().setScaling(getScaling(particleEffect.angle, gdxEmitter.getAngle().getScaling()));

        gdxEmitter.getRotation().setLow(particleEffect.rotationLowMin, particleEffect.rotationLowMax);
        gdxEmitter.getRotation().setHigh(particleEffect.rotationHighMin, particleEffect.rotationHighMax);
        gdxEmitter.getRotation().setTimeline(getTimeline(particleEffect.rotation, gdxEmitter.getRotation().getTimeline()));
        gdxEmitter.getRotation().setScaling(getScaling(particleEffect.rotation, gdxEmitter.getRotation().getScaling()));

        gdxEmitter.getWind().setLow(particleEffect.windLowMin, particleEffect.windLowMax);
        gdxEmitter.getWind().setHigh(particleEffect.windHighMin, particleEffect.windHighMax);
        gdxEmitter.getWind().setTimeline(getTimeline(particleEffect.wind, gdxEmitter.getWind().getTimeline()));
        gdxEmitter.getWind().setScaling(getScaling(particleEffect.wind, gdxEmitter.getWind().getScaling()));

        gdxEmitter.getGravity().setLow(particleEffect.gravityLowMin, particleEffect.gravityLowMax);
        gdxEmitter.getGravity().setHigh(particleEffect.gravityHighMin, particleEffect.gravityHighMax);
        gdxEmitter.getGravity().setTimeline(getTimeline(particleEffect.gravity, gdxEmitter.getGravity().getTimeline()));
        gdxEmitter.getGravity().setScaling(getScaling(particleEffect.gravity, gdxEmitter.getGravity().getScaling()));

        float[] scaling = gdxEmitter.getTint().getColors();
        float[] timeline = gdxEmitter.getTint().getTimeline();
        if (particleEffect.tintColors.size() != timeline.length) {
            timeline = new float[particleEffect.tintColors.size()];
            scaling = new float[particleEffect.tintColors.size() * 3];
        }
        for (int i = 0; i < particleEffect.tintColors.size(); i++) {
            Color c = particleEffect.tintColors.getColor(i);
            scaling[i * 3] = c.r;
            scaling[i * 3 + 1] = c.g;
            scaling[i * 3 + 2] = c.b;
            timeline[i] = particleEffect.tintColors.getTime(i);
        }
        gdxEmitter.getTint().setColors(scaling);
        gdxEmitter.getTint().setTimeline(timeline);

        gdxEmitter.getTransparency().setTimeline(getTimeline(particleEffect.transparency, gdxEmitter.getTransparency().getTimeline()));
        gdxEmitter.getTransparency().setScaling(getScaling(particleEffect.transparency, gdxEmitter.getTransparency().getScaling()));

        gdxEmitter.setAttached(particleEffect.optionsAttached);
        gdxEmitter.setContinuous(particleEffect.optionsContinuous);
        gdxEmitter.setAligned(particleEffect.optionsAligned);
        gdxEmitter.setAdditive(particleEffect.optionsAdditive);
        gdxEmitter.setBehind(particleEffect.optionsBehind);
        gdxEmitter.setPremultipliedAlpha(particleEffect.optionsPremultipliedAlpha);
        gdxEmitter.setSpriteMode(ParticleEffect.getGdxSpriteMode(particleEffect.optionsSpriteMode));

    }

    private com.badlogic.gdx.graphics.g2d.ParticleEmitter gdxEmitter;

    public ParticleEffect particleEffect = new ParticleEffect();
    public Vector2 position = new Vector2();
    public boolean startOnAwake;

    public void start() {
        gdxEmitter = new com.badlogic.gdx.graphics.g2d.ParticleEmitter();
        setProperties();
        for (int i = 0; i < particleEffect.sprites.size(); i++) {
            Sprite s;
            Texture tex = particleEffect.sprites.get(i);
            s = new com.badlogic.gdx.graphics.g2d.Sprite(Renderer.getDefaultTexture().textureAsset.getGdxTexture());
            s.setRegion(tex.uv0.x, tex.uv0.y, tex.uv1.x, tex.uv1.y);
            if (tex.textureAsset != null)
                s.setTexture(tex.textureAsset.getGdxTexture());
            gdxEmitter.getSprites().add(s);
        }
        if (startOnAwake) gdxEmitter.start();
    }

    public void update() {

        gdxEmitter.setPosition(
            gameObject.transform.position.x + (MathUtils.cosDeg(gameObject.transform.rotation) * position.x -
                MathUtils.sinDeg(gameObject.transform.rotation) * position.y),
            gameObject.transform.position.y + (MathUtils.sinDeg(gameObject.transform.rotation) * position.x +
                MathUtils.cosDeg(gameObject.transform.rotation) * position.y)
        );
        gdxEmitter.update(Time.getDeltaTime());
        Renderer.drawGdxEmitter(gdxEmitter);

    }

    public void startEmitter() {
        setProperties();
        gdxEmitter.start();
    }

    public void resetEmitter() {
        setProperties();
        gdxEmitter.reset();
    }

}

