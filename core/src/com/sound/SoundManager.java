package com.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	
	private Sound shot;
	
	public SoundManager() {
		shot = Gdx.audio.newSound(Gdx.files.internal("sound/shot.wav"));
	}
	
	public void playShot() {
		shot.play();
	}
	
	public void dispose() {
		shot.dispose();
	}
	
}
