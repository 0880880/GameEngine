package com.gameengine.api.components;

import com.gameengine.api.AudioManager;
import com.gameengine.api.Component;
import com.gameengine.api.audio.AudioClip;
import de.pottgames.tuningfork.BufferedSoundSource;
import de.pottgames.tuningfork.SoundSource;
import de.pottgames.tuningfork.StreamedSoundSource;

public class StreamedAudioSource extends Component {

    public AudioClip audioClip;
    public boolean playOnStart = false;
    public boolean loop = false;

    public float volume;
    public float pitch;

    private StreamedSoundSource source;

    public void start() {

        source = audioClip.getStreamedSource();

        AudioManager.sources.add(source);

        if (playOnStart) source.play();

    }

    public void update() {

        source.setLooping(loop);
        source.setVolume(volume);
        source.setPitch(pitch);

    }

    public void play() {
        source.play();
    }

    public void stop() {
        source.stop();
    }

    public void pause() {
        source.pause();
    }

    public float getPlaybackPosition() {
        return source.getPlaybackPosition();
    }

    public float getDuration() {
        return source.getDuration();
    }

}
