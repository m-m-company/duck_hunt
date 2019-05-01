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
	double delayQuack;
	
	@Override
	public void create () {
		graphic = new GraphicManager();
		sound = new SoundManager();
		background = new Background();
		duck = new Duck();
		rechargeTime = 0.0d;
		delayQuack = 0.0d;
	}

	@Override
	public void render () {
		graphic.clearDisplay();
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		rechargeTime += Gdx.graphics.getDeltaTime();
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && rechargeTime > 2.1d) {//do I like this sound shot?
			System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
			System.out.println(duck.getX() + " " + duck.getY());
			sound.playShot();
			rechargeTime = 0.0d;
			if(duck.collide(Gdx.input.getX() + 32, Gdx.graphics.getHeight() - 32 - Gdx.input.getY())) {
				graphic.drawDeathAnimation(duck);
				sound.playDeath();
			}
		}
		if(duck.isDead())
			duck.fall();
		else
			duck.move();
		graphic.drawDuck(duck);
		delayQuack += Gdx.graphics.getDeltaTime();
		if(delayQuack > 1.2d) {
			delayQuack = 0.0d;
			sound.playQuack();
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
