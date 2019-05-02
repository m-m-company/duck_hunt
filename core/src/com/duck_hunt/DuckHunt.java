package com.duck_hunt;

import java.util.ArrayList;

import com.background.Background;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.duck.Duck;
import com.graphicManager.GraphicManager;
import com.sound.SoundManager;

public class DuckHunt extends ApplicationAdapter {
	
	final static int STAGE_1 = 100;
	final static int STAGE_2 = 300;
	final static int STAGE_3 = 600;
	final static int STAGE_4 = 1000;
	
	double rechargeTime;
	int score;
	int dead;
	GraphicManager graphic;
	SoundManager sound;
	Background background;
	ArrayList<Duck> ducks;
	
	@Override
	public void create () {
		rechargeTime = 0.0d;
		score = 0;
		dead = 0;
		graphic = new GraphicManager();
		sound = new SoundManager();
		background = new Background();
		ducks = new ArrayList<Duck>();
		createDucks();
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
			for(int i = 0; i < ducks.size(); ++i) {
				if(ducks.get(i).collide(Gdx.input.getX() + 32, Gdx.graphics.getHeight() - 32 - Gdx.input.getY())) {
					score += 20;
					graphic.drawDeathAnimation(ducks.get(i));
					sound.playDeath();
				}
			}
		}
		for(int i = 0; i < ducks.size(); ++i) {
			graphic.drawDuck(ducks.get(i));
			ducks.get(i).delayQuack += Gdx.graphics.getDeltaTime();
			if(ducks.get(i).isQuacking() && ducks.get(i).delayQuack > 0.5d) {
				sound.playQuack();
				ducks.get(i).delayQuack = 0.0d;
			}
			if(ducks.get(i).isDead()) {
				if(ducks.get(i).fall())
					ducks.remove(i--);
			}
			else
				ducks.get(i).move();
		}
		if(ducks.isEmpty())
			createDucks();
		graphic.drawBack(background, score);
	}
	
	public void createDucks() {
		if(score >= 0 && score <= DuckHunt.STAGE_1) {
			ducks.add(new Duck());
		}
		else if(score > DuckHunt.STAGE_1 && score <= DuckHunt.STAGE_2) {
			ducks.add(new Duck());
			ducks.add(new Duck());
		}
		else if(score > DuckHunt.STAGE_2 && score <= DuckHunt.STAGE_3) {
			ducks.add(new Duck());
			ducks.add(new Duck());
			ducks.add(new Duck());
		}
		else if(score > DuckHunt.STAGE_3 && score <= DuckHunt.STAGE_4) {
			ducks.add(new Duck());
			ducks.add(new Duck());
			ducks.add(new Duck());
			ducks.add(new Duck());
		}
	}
	
	@Override
	public void dispose () {
		graphic.dispose();
		sound.dispose();
		background.dispose();
	}
}
