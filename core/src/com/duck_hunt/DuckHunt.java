package com.duck_hunt;

import com.background.Background;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.duck.Duck;
import com.graphicManager.GraphicManager;
import com.sound.SoundManager;

public class DuckHunt extends ApplicationAdapter {
	
	GraphicManager graphic;
	SoundManager sound;
	Background background;
	Duck[] ducks = new Duck[4];
	
	@Override
	public void create () {
		graphic = new GraphicManager();
		sound = new SoundManager();
		background = new Background();
		for(int i = 0; i < 4; ++i) {
			ducks[i] = new Duck();
		}
	}

	@Override
	public void render () {
		graphic.clearDisplay();
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			sound.playShot();
			//graphic.drawDeathAnimation(duck);
		}
		for(Duck d : ducks) {
			if(d.isDead())
				d.fall();
			else
				d.move();
			graphic.drawDuck(d);
		}
		graphic.drawBack(background);
	}
	
	@Override
	public void dispose () {
		graphic.dispose();
		sound.dispose();
		background.dispose();
	}
}
