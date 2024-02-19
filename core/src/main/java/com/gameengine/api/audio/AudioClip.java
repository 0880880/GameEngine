package com.gameengine.api.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.gameengine.api.AudioManager;
import com.gameengine.Statics;
import de.pottgames.tuningfork.*;

public class AudioClip {

    public String soundFile;
    private FileHandle file;

    private SoundBuffer buffer;

    public void init() {
        if (soundFile != null) file = Gdx.files.absolute(soundFile);
    }

    public BufferedSoundSource getSource() {
        if (soundFile != null && buffer == null) {
            buffer = SoundLoader.load(file);
            AudioManager.buffers.add(buffer);
        }
        return Statics.audio.obtainSource(buffer);
    }

    public StreamedSoundSource getStreamedSource() {
        return new StreamedSoundSource(file);
    }

}
