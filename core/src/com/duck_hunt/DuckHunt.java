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
	Duck duck;
	double rechargeTime;
	
	@Override
	public void create () {
		graphic = new GraphicManager();
		sound = new SoundManager();
		background = new Background();
		duck = new Duck();
		rechargeTime = 0.0d;
	}

	@Override
	public void render () {
		graphic.clearDisplay();
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		rechargeTime += Gdx.graphics.getDeltaTime();
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && rechargeTime > 2.1d) {//do I like this sound shot?
			sound.playShot();
			rechargeTime = 0.0d;
			//graphic.drawDeathAnimation(duck);
		}
		if(duck.isDead())
			duck.fall();
		else
			duck.move();
		graphic.drawDuck(duck);
		graphic.drawBack(background);
	}
	
	@Override
	public void dispose () {
		graphic.dispose();
		sound.dispose();
		background.dispose();
	}
}
