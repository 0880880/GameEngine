package com.gameengine.api.components;

import com.gameengine.api.AudioManager;
import com.gameengine.api.Component;
import com.gameengine.api.audio.AudioClip;

public class AudioSource extends Component {

 public AudioClip audioClip;
 public boolean playOnStart = false;
 public boolean loop = false;

 public float volume;
 public float pitch;


 public void start() {}

 public void update() {}

 public void play() {}

 public void stop() {}

 public void pause() {}

 public float getPlaybackPosition() {return 0.0f;}

 
public float getDuration() {return 0.0f;}


}
