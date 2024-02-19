package com.game;

import com.badlogic.gdx.Game;
import de.pottgames.tuningfork.Audio;

public class Main extends Game {
    @Override
    public void create() {
        Statics.audio = Audio.init();
        setScreen(new MainScreen());
    }
}
