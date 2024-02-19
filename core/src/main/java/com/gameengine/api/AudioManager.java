package com.gameengine.api;

import com.badlogic.gdx.utils.Array;
import de.pottgames.tuningfork.BufferedSoundSource;
import de.pottgames.tuningfork.SoundBuffer;
import de.pottgames.tuningfork.StreamedSoundSource;
import de.pottgames.tuningfork.jukebox.song.SongSource;

public class AudioManager {

    public static Array<SongSource> sources = new Array<>();
    public static Array<SoundBuffer> buffers = new Array<>();

    public static void destroySources() {
        for (int i = 0; i < sources.size; i++) {
            SongSource source = sources.get(i);
            source.stop();
            if (source instanceof StreamedSoundSource)
                ((StreamedSoundSource) source).dispose();
            if (source instanceof BufferedSoundSource)
                ((BufferedSoundSource) source).free();
            sources.removeIndex(i);
        }
        sources.clear();
    }

    public static void dispose() {
        destroySources();
        for (int i = 0; i < buffers.size; i++) {
            SoundBuffer buffer = buffers.get(i);
            buffer.dispose();
        }
        sources.clear();
        buffers.clear();
    }

}
