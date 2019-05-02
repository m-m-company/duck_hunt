package com.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	
	private Sound shot;
	private Sound background;
	private Sound death;
	private Sound quack;
	
	public SoundManager() {
		background = Gdx.audio.newSound(Gdx.files.internal("sound/background.mp3"));
		background.loop();
		shot = Gdx.audio.newSound(Gdx.files.internal("sound/shot.wav"));
		death = Gdx.audio.newSound(Gdx.files.internal("sound/death.mp3"));
		quack = Gdx.audio.newSound(Gdx.files.internal("sound/quack.mp3"));
	}
	
	public void playShot() {
		shot.play();
	}
	
	public void playDeath() {
		death.play();
	}
	
	public void playQuack() {
		quack.play(2);
	}
	
	public void dispose() {
		background.dispose();
		shot.dispose();
		death.dispose();
		quack.dispose();
	}
	
}
