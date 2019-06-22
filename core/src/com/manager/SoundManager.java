package com.manager;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	
	private Sound shot;
	private Sound background;
	private Sound death;
	private Sound quack;
	private Sound reload;
	private Sound failedShot;
	
	public SoundManager() {
		background = Gdx.audio.newSound(Gdx.files.internal("sound" + File.separator + "background.mp3"));
		shot = Gdx.audio.newSound(Gdx.files.internal("sound" + File.separator + "shot.wav"));
		death = Gdx.audio.newSound(Gdx.files.internal("sound" + File.separator + "death.wav"));
		quack = Gdx.audio.newSound(Gdx.files.internal("sound" + File.separator + "quack.mp3"));
		reload = Gdx.audio.newSound(Gdx.files.internal("sound" + File.separator + "reload.wav"));
		failedShot = Gdx.audio.newSound(Gdx.files.internal("sound" + File.separator + "failedShot.mp3"));
	}
	
	public void restartBackground() {
		background.stop();
		background.loop();
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
	
	public void playReload() {
		reload.play();
	}
	
	public void playFailedShot() {
		failedShot.play();
	}
	
	public void dispose() {
		background.dispose();
		shot.dispose();
		death.dispose();
		quack.dispose();
		reload.dispose();
		failedShot.dispose();
	}
	
}
