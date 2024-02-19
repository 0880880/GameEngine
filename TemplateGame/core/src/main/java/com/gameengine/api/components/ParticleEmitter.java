package com.gameengine.api.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gameengine.api.Component;
import com.gameengine.api.Curve;
import com.gameengine.api.Renderer;
import com.gameengine.api.Time;
import com.gameengine.api.graphics.Color;
import com.gameengine.api.graphics.Texture;
import com.gameengine.api.math.Vector2;

public class ParticleEmitter extends Component {

    private com.badlogic.gdx.graphics.g2d.ParticleEmitter createEmitter() {

        com.badlogic.gdx.graphics.g2d.ParticleEmitter emitter = new com.badlogic.gdx.graphics.g2d.ParticleEmitter();

        emitter.setName(particleEffect.name);
        if (particleEffect.delayActive) {
            emitter.getDelay().setActive(particleEffect.delayActive);
            emitter.getDelay().setLow(particleEffect.delayLowMin, particleEffect.delayLowMax);
        }
        emitter.getDuration().setActive(true);
        emitter.getDuration().setLow(particleEffect.durationLowMin, particleEffect.durationLowMax);
        emitter.setMinParticleCount(particleEffect.countMin);
        emitter.setMaxParticleCount(particleEffect.countMax);
        if (particleEffect.emissionActive) {
            emitter.getEmission().setActive(true);
            emitter.getEmission().setLow(particleEffect.emissionLowMin, particleEffect.emissionLowMax);
            emitter.getEmission().setHigh(particleEffect.emissionHighMin, particleEffect.emissionHighMax);
            float[] scaling = new float[particleEffect.emission.size()];
            float[] timeline = new float[particleEffect.emission.size()];
            for (int i = 0; i < particleEffect.emission.size(); i++) {
                scaling[i] = particleEffect.emission.getPoint(i).y;
                timeline[i] = particleEffect.emission.getPoint(i).x;
            }
            emitter.getEmission().setScaling(scaling);
            emitter.getEmission().setTimeline(timeline);
        }
        {
            emitter.getLife().setActive(true);
            emitter.getLife().setLow(particleEffect.lifeLowMin, particleEffect.lifeLowMax);
            emitter.getLife().setHigh(particleEffect.lifeHighMin, particleEffect.lifeHighMax);
            emitter.getLife().setRelative(particleEffect.lifeRelative);
            float[] scaling = new float[particleEffect.lifeScaling.size()];
            float[] timeline = new float[particleEffect.lifeScaling.size()];
            for (int i = 0; i < particleEffect.lifeScaling.size(); i++) {
                scaling[i] = particleEffect.lifeScaling.getPoint(i).y;
                timeline[i] = particleEffect.lifeScaling.getPoint(i).x;
            }
            emitter.getLife().setScaling(scaling);
            emitter.getLife().setTimeline(timeline);
        }
        if (particleEffect.lifeOffsetActive) {
            emitter.getLifeOffset().setActive(particleEffect.lifeOffsetActive);
            emitter.getLifeOffset().setLow(particleEffect.lifeOffsetLowMin, particleEffect.lifeOffsetLowMax);
            emitter.getLifeOffset().setHigh(particleEffect.lifeOffsetHighMin, particleEffect.lifeOffsetHighMax);
            float[] scaling = new float[particleEffect.lifeOffset.size()];
            float[] timeline = new float[particleEffect.lifeOffset.size()];
            for (int i = 0; i < particleEffect.lifeOffset.size(); i++) {
                scaling[i] = particleEffect.lifeOffset.getPoint(i).y;
                timeline[i] = particleEffect.lifeOffset.getPoint(i).x;
            }
            emitter.getLifeOffset().setScaling(scaling);
            emitter.getLifeOffset().setTimeline(timeline);
        }
        if (particleEffect.xOffsetActive) {
            emitter.getXOffsetValue().setActive(particleEffect.xOffsetActive);
            emitter.getXOffsetValue().setLow(particleEffect.xOffsetLowMin, particleEffect.xOffsetLowMax);
        }
        if (particleEffect.yOffsetActive) {
            emitter.getYOffsetValue().setActive(particleEffect.yOffsetActive);
            emitter.getYOffsetValue().setLow(particleEffect.yOffsetLowMin, particleEffect.yOffsetLowMax);
        }
        emitter.getSpawnShape().setShape(ParticleEffect.getGdxSpawnShape(particleEffect.spawnShape));
        if (particleEffect.spawnShape != ParticleEffect.SpawnShape.POINT) {

            emitter.getSpawnWidth().setLow(particleEffect.spawnWidthLowMin, particleEffect.spawnWidthLowMax);
            emitter.getSpawnWidth().setHigh(particleEffect.spawnWidthHighMin, particleEffect.spawnWidthHighMax);
            float[] scaling = new float[particleEffect.spawnWidth.size()];
            float[] timeline = new float[particleEffect.spawnWidth.size()];
            for (int i = 0; i < particleEffect.spawnWidth.size(); i++) {
                scaling[i] = particleEffect.spawnWidth.getPoint(i).y;
                timeline[i] = particleEffect.spawnWidth.getPoint(i).x;
            }
            emitter.getSpawnWidth().setScaling(scaling);
            emitter.getSpawnWidth().setTimeline(timeline);

            emitter.getSpawnHeight().setLow(particleEffect.spawnHeightLowMin, particleEffect.spawnHeightLowMax);
            emitter.getSpawnHeight().setHigh(particleEffect.spawnHeightHighMin, particleEffect.spawnHeightHighMax);
            scaling = new float[particleEffect.spawnHeight.size()];
            timeline = new float[particleEffect.spawnHeight.size()];
            for (int i = 0; i < particleEffect.spawnHeight.size(); i++) {
                scaling[i] = particleEffect.spawnHeight.getPoint(i).y;
                timeline[i] = particleEffect.spawnHeight.getPoint(i).x;
            }
            emitter.getSpawnHeight().setScaling(scaling);
            emitter.getSpawnHeight().setTimeline(timeline);

            if (particleEffect.spawnShape == ParticleEffect.SpawnShape.ELLIPSE) {
                emitter.getSpawnShape().setEdges(particleEffect.spawnEdges);
                emitter.getSpawnShape().setSide(ParticleEffect.getGdxSpawnEllipseSide(particleEffect.spawnEllipseSide));
            }

        }
        {
            emitter.getXScale().setLow(particleEffect.xScaleLowMin, particleEffect.xScaleLowMax);
            emitter.getXScale().setHigh(particleEffect.xScaleHighMin, particleEffect.xScaleHighMax);
            float[] scaling = new float[particleEffect.xScale.size()];
            float[] timeline = new float[particleEffect.xScale.size()];
            for (int i = 0; i < particleEffect.xScale.size(); i++) {
                scaling[i] = particleEffect.xScale.getPoint(i).y;
                timeline[i] = particleEffect.xScale.getPoint(i).x;
            }
            emitter.getXScale().setScaling(scaling);
            emitter.getXScale().setTimeline(timeline);
        }
        {
            emitter.getYScale().setLow(particleEffect.yScaleLowMin, particleEffect.yScaleLowMax);
            emitter.getYScale().setHigh(particleEffect.yScaleHighMin, particleEffect.yScaleHighMax);
            float[] scaling = new float[particleEffect.yScale.size()];
            float[] timeline = new float[particleEffect.yScale.size()];
            for (int i = 0; i < particleEffect.yScale.size(); i++) {
                scaling[i] = particleEffect.yScale.getPoint(i).y;
                timeline[i] = particleEffect.yScale.getPoint(i).x;
            }
            emitter.getYScale().setScaling(scaling);
            emitter.getYScale().setTimeline(timeline);
        }
        if (particleEffect.velocityActive) {
            emitter.getVelocity().setActive(particleEffect.velocityActive);
            emitter.getVelocity().setLow(particleEffect.velocityLowMin, particleEffect.velocityLowMax);
            emitter.getVelocity().setHigh(particleEffect.velocityHighMin, particleEffect.velocityHighMax);
            float[] scaling = new float[particleEffect.velocity.size()];
            float[] timeline = new float[particleEffect.velocity.size()];
            for (int i = 0; i < particleEffect.velocity.size(); i++) {
                scaling[i] = particleEffect.velocity.getPoint(i).y;
                timeline[i] = particleEffect.velocity.getPoint(i).x;
            }
            emitter.getVelocity().setScaling(scaling);
            emitter.getVelocity().setTimeline(timeline);
        }
        if (particleEffect.angleActive) {
            emitter.getAngle().setActive(particleEffect.angleActive);
            emitter.getAngle().setLow(particleEffect.angleLowMin, particleEffect.angleLowMax);
            emitter.getAngle().setHigh(particleEffect.angleHighMin, particleEffect.angleHighMax);
            float[] scaling = new float[particleEffect.angle.size()];
            float[] timeline = new float[particleEffect.angle.size()];
            for (int i = 0; i < particleEffect.angle.size(); i++) {
                scaling[i] = particleEffect.angle.getPoint(i).y;
                timeline[i] = particleEffect.angle.getPoint(i).x;
            }
            emitter.getAngle().setScaling(scaling);
            emitter.getAngle().setTimeline(timeline);
        }
        if (particleEffect.rotationActive) {
            emitter.getRotation().setActive(particleEffect.rotationActive);
            emitter.getRotation().setLow(particleEffect.rotationLowMin, particleEffect.rotationLowMax);
            emitter.getRotation().setHigh(particleEffect.rotationHighMin, particleEffect.rotationHighMax);
            float[] scaling = new float[particleEffect.rotation.size()];
            float[] timeline = new float[particleEffect.rotation.size()];
            for (int i = 0; i < particleEffect.rotation.size(); i++) {
                scaling[i] = particleEffect.rotation.getPoint(i).y;
                timeline[i] = particleEffect.rotation.getPoint(i).x;
            }
            emitter.getRotation().setScaling(scaling);
            emitter.getRotation().setTimeline(timeline);
        }
        if (particleEffect.windActive) {
            emitter.getWind().setActive(particleEffect.windActive);
            emitter.getWind().setLow(particleEffect.windLowMin, particleEffect.windLowMax);
            emitter.getWind().setHigh(particleEffect.windHighMin, particleEffect.windHighMax);
            float[] scaling = new float[particleEffect.wind.size()];
            float[] timeline = new float[particleEffect.wind.size()];
            for (int i = 0; i < particleEffect.wind.size(); i++) {
                scaling[i] = particleEffect.wind.getPoint(i).y;
                timeline[i] = particleEffect.wind.getPoint(i).x;
            }
            emitter.getWind().setScaling(scaling);
            emitter.getWind().setTimeline(timeline);
        }
        if (particleEffect.gravityActive) {
            emitter.getGravity().setActive(particleEffect.gravityActive);
            emitter.getGravity().setLow(particleEffect.gravityLowMin, particleEffect.gravityLowMax);
            emitter.getGravity().setHigh(particleEffect.gravityHighMin, particleEffect.gravityHighMax);
            float[] scaling = new float[particleEffect.gravity.size()];
            float[] timeline = new float[particleEffect.gravity.size()];
            for (int i = 0; i < particleEffect.gravity.size(); i++) {
                scaling[i] = particleEffect.gravity.getPoint(i).y;
                timeline[i] = particleEffect.gravity.getPoint(i).x;
            }
            emitter.getGravity().setScaling(scaling);
            emitter.getGravity().setTimeline(timeline);
        }
        {
            emitter.getTint().setActive(true);
            float[] scaling = new float[particleEffect.tintColors.size() * 3];
            float[] timeline = new float[particleEffect.tintColors.size()];
            for (int i = 0; i < particleEffect.tintColors.size(); i++) {
                Color c = particleEffect.tintColors.getColor(i);
                scaling[i * 3] = c.r;
                scaling[i * 3 + 1] = c.g;
                scaling[i * 3 + 2] = c.b;
                timeline[i] = particleEffect.tintColors.getTime(i);
            }
            emitter.getGravity().setScaling(scaling);
            emitter.getGravity().setTimeline(timeline);
        }
        {
            emitter.getTransparency().setActive(true);
            emitter.getTransparency().setLow(0, 0);
            emitter.getTransparency().setHigh(1, 1);
            float[] scaling = new float[particleEffect.transparency.size()];
            float[] timeline = new float[particleEffect.transparency.size()];
            for (int i = 0; i < particleEffect.transparency.size(); i++) {
                scaling[i] = particleEffect.transparency.getPoint(i).y;
                timeline[i] = particleEffect.transparency.getPoint(i).x;
            }
            emitter.getTransparency().setScaling(scaling);
            emitter.getTransparency().setTimeline(timeline);
        }
        emitter.setAttached(particleEffect.optionsAttached);
        emitter.setContinuous(particleEffect.optionsContinuous);
        emitter.setAligned(particleEffect.optionsAligned);
        emitter.setAdditive(particleEffect.optionsAdditive);
        emitter.setBehind(particleEffect.optionsBehind);
        emitter.setPremultipliedAlpha(particleEffect.optionsPremultipliedAlpha);
        emitter.setSpriteMode(ParticleEffect.getGdxSpriteMode(particleEffect.optionsSpriteMode));

        return emitter;

    }

    private com.badlogic.gdx.graphics.g2d.ParticleEmitter gdxEmitter;

    public ParticleEffect particleEffect = new ParticleEffect();
    public Vector2 position = new Vector2();
    public boolean startOnAwake;

    public void start() {
        gdxEmitter = createEmitter();
        for (int i = 0; i < particleEffect.sprites.size(); i++) {
            Sprite s;
            Texture tex = particleEffect.sprites.get(i);
            if (tex != null) {
                s = new com.badlogic.gdx.graphics.g2d.Sprite(tex.textureAsset.getGdxTexture());
                s.setRegion(tex.uv0.x, tex.uv0.y, tex.uv1.x, tex.uv1.y);
            } else {
                s = new com.badlogic.gdx.graphics.g2d.Sprite(Renderer.getDefaultTexture().textureAsset.getGdxTexture());
            }
            gdxEmitter.getSprites().add(s);
        }
        if (startOnAwake) gdxEmitter.start();
    }

    public void update() {
        gdxEmitter.setPosition(position.x, position.y);
        gdxEmitter.update(Time.getDeltaTime());
        Renderer.drawGdxEmitter(gdxEmitter);
    }

    public void emitterStart() {
        gdxEmitter.start();
    }

    public void emitterReset() {
        gdxEmitter.reset();
    }

    public void setDelayActive(boolean active) {
        gdxEmitter.getDelay().setActive(active);
    }

    public void setDelayLow(float min, float max) {
        gdxEmitter.getDelay().setLow(min, max);
    }

    public void setEmissionActive(boolean active) {
        gdxEmitter.getEmission().setActive(active);
    }

    public void setEmissionLow(float min, float max) {
        gdxEmitter.getEmission().setLow(min, max);
    }

    public void setEmissionHigh(float min, float max) {
        gdxEmitter.getEmission().setHigh(min, max);
    }

    public void setEmissionCurve(Curve curve) {
        float[] scaling = new float[curve.size()];
        float[] timeline = new float[curve.size()];
        for (int i = 0; i < curve.size(); i++) {
            scaling[i] = curve.getPoint(i).y;
            timeline[i] = curve.getPoint(i).x;
        }
        gdxEmitter.getEmission().setScaling(scaling);
        gdxEmitter.getEmission().setTimeline(timeline);
    }

    public void setLifeLow(float min, float max) {
        gdxEmitter.getLife().setLow(min, max);
    }

    public void setLifeHigh(float min, float max) {
        gdxEmitter.getLife().setHigh(min, max);
    }

    public void setLifeCurve(Curve curve) {
        float[] scaling = new float[curve.size()];
        float[] timeline = new float[curve.size()];
        for (int i = 0; i < curve.size(); i++) {
            scaling[i] = curve.getPoint(i).y;
            timeline[i] = curve.getPoint(i).x;
        }
        gdxEmitter.getLife().setScaling(scaling);
        gdxEmitter.getLife().setTimeline(timeline);
    }

    public void setLifeOffsetActive(boolean active) {
        gdxEmitter.getLifeOffset().setActive(active);
    }

    public void setLifeOffsetLow(float min, float max) {
        gdxEmitter.getLifeOffset().setLow(min, max);
    }

    public void setLifeOffsetHigh(float min, float max) {
        gdxEmitter.getLifeOffset().setHigh(min, max);
    }

    public void setLifeOffsetCurve(Curve curve) {
        float[] scaling = new float[curve.size()];
        float[] timeline = new float[curve.size()];
        for (int i = 0; i < curve.size(); i++) {
            scaling[i] = curve.getPoint(i).y;
            timeline[i] = curve.getPoint(i).x;
        }
        gdxEmitter.getLifeOffset().setScaling(scaling);
        gdxEmitter.getLifeOffset().setTimeline(timeline);
    }

    public void setXOffsetActive(boolean active) {
        gdxEmitter.getXOffsetValue().setActive(active);
    }

    public void setXOffsetLow(float min, float max) {
        gdxEmitter.getXOffsetValue().setLow(min, max);
    }

    public void setYOffsetActive(boolean active) {
        gdxEmitter.getYOffsetValue().setActive(active);
    }

    public void setYOffsetLow(float min, float max) {
        gdxEmitter.getYOffsetValue().setLow(min, max);
    }

    public void setSpawnShape(ParticleEffect.SpawnShape spawnShape) {
        gdxEmitter.getSpawnShape().setShape(ParticleEffect.getGdxSpawnShape(spawnShape));
    }

    public void setSpawnEllipseSide(ParticleEffect.SpawnEllipseSide spawnEllipseSide) {
        gdxEmitter.getSpawnShape().setSide(ParticleEffect.getGdxSpawnEllipseSide(spawnEllipseSide));
    }

    public void setSpriteMode(ParticleEffect.SpriteMode spriteMode) {
        gdxEmitter.setSpriteMode(ParticleEffect.getGdxSpriteMode(spriteMode));
    }

}

