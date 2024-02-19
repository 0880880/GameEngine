package com.gameengine;

import com.badlogic.gdx.Game;
import com.gameengine.utils.Utils;

import static com.gameengine.Statics.preferences;

public class Main extends Game {

	public WindowListener windowListener;

	public Main() {
		windowListener = new WindowListener() {
            @Override
            public void close() {
                preferences.flush();
            }
        };
	}

	@Override
	public void create() {
		Statics.main = this;
		Utils.initialize();
		setScreen(new MainMenu());
	}
}
