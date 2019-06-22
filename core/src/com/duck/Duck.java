package com.duck;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.manager.GraphicManager;

public class Duck {
	
	final static int MAXIMUM_Y = GraphicManager.MAXIMUM_Y - 95;
	final static int RIGHT = 0;
	final static int LEFT = 1;
	final static int START_VELOCITY = 50;
	final static int TOP_LEFT = 2;
	final static int TOP_RIGHT = 3;
	final static int LOWER_LEFT = 4;
	final static int LOWER_RIGHT = 5;
	
	private DuckGraphic g;
	private int direction;
	private float x;
	private float originY;
	private float y;
	private boolean isBorn;
	private boolean isDead;
	private boolean isOut;
	private boolean selectedLastDirection;
	private double delayAnimation;
	private double delayChangeAnimation;
	private int quack;
	private int velocity;
	public double delayQuack = 0.0d;
	public double hit = 0.0d;
	private double timeRemaining;
	
	public Duck(int difficulty) {
		quack = 1;
		selectedLastDirection = false;
		velocity = Duck.START_VELOCITY + difficulty;
		delayAnimation = 0.0;
		delayChangeAnimation = 0.0;
		timeRemaining = 10.0d;
		g = new DuckGraphic();
		isDead = false;
		spawn();
	}
	
	private void selectDirection(Random r) {
		direction = r.nextInt(g.getMaximumDirections());
	}
	
	private void spawn() {
		isBorn = true;
		isOut = false;
		originY = GraphicManager.MINIMUM_Y;
		Random r = new Random();
		x = r.nextInt(GraphicManager.MAXIMUM_X);
		y = r.nextInt(GraphicManager.MAXIMUM_Y - 100);
		while(y < GraphicManager.MINIMUM_Y)
			y = r.nextInt(GraphicManager.MAXIMUM_Y - 100);
		direction = r.nextInt(4);
		while(direction < 2)
			direction = r.nextInt(4);
		g.setAnimations(direction);
	}
	
	public Texture getFrame() {
		return g.getFrame();
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		if(isBorn)
			return originY;
		return y;
	}

	public void move() {
		timeRemaining -= Gdx.graphics.getDeltaTime();
		if(timeRemaining <= 0.0d && !selectedLastDirection) {
			selectedLastDirection = true;
			Random r = new Random();
			direction = r.nextInt(2) + 2;
			g.setAnimations(direction);
			delayAnimation = 0.0;
		}
		if(!isBorn) {
			delayChangeAnimation += (double)velocity/200;
			if(direction == Duck.RIGHT) {
				x += Gdx.graphics.getDeltaTime()*velocity;
				if(x >= GraphicManager.MAXIMUM_X && delayChangeAnimation > 4) {
					selectDirection(new Random());
					g.setAnimations(direction);
					delayChangeAnimation = 0.0;
				}
			}
			if(direction == Duck.LEFT) {
				x -= Gdx.graphics.getDeltaTime()*velocity;
				if(x <= GraphicManager.MINIMUM_X && delayChangeAnimation > 4) {
					selectDirection(new Random());
					g.setAnimations(direction);
					delayChangeAnimation = 0.0;
				}
			}
			if(direction == Duck.TOP_LEFT) {
				x -= Gdx.graphics.getDeltaTime()*velocity;
				y += Gdx.graphics.getDeltaTime()*velocity;
				if((x <= GraphicManager.MINIMUM_X || y >= Duck.MAXIMUM_Y) && delayChangeAnimation > 4 && timeRemaining > 0.0d) {
					selectDirection(new Random());
					g.setAnimations(direction);
					delayChangeAnimation = 0.0;
				}
				else if((x < 0 || y > Gdx.graphics.getHeight()) && timeRemaining <= 0.0d)
					isOut = true;
			}
			if(direction == Duck.TOP_RIGHT) {
				x += Gdx.graphics.getDeltaTime()*velocity;
				y += Gdx.graphics.getDeltaTime()*velocity;
				if((x >= GraphicManager.MAXIMUM_X || y >= Duck.MAXIMUM_Y) && delayChangeAnimation > 4 && timeRemaining > 0.0d) {
					selectDirection(new Random());
					g.setAnimations(direction);
					delayChangeAnimation = 0.0;
				}
				else if((x < 0 || y > Gdx.graphics.getHeight()) && timeRemaining <= 0.0d)
					isOut = true;
			}
			if(direction == Duck.LOWER_LEFT) {
				x -= Gdx.graphics.getDeltaTime()*velocity;
				y -= Gdx.graphics.getDeltaTime()*velocity;
				if((x <= GraphicManager.MINIMUM_X || y <= GraphicManager.MINIMUM_Y + 20) && delayChangeAnimation > 4) {
					selectDirection(new Random());
					g.setAnimations(direction);
					delayChangeAnimation = 0.0;
				}
			}
			if(direction == Duck.LOWER_RIGHT) {
				x += Gdx.graphics.getDeltaTime()*velocity;
				y -= Gdx.graphics.getDeltaTime()*velocity;
				if((x >= GraphicManager.MAXIMUM_X || y <= GraphicManager.MINIMUM_Y) && delayChangeAnimation > 4) {
					selectDirection(new Random());
					g.setAnimations(direction);
					delayChangeAnimation = 0.0;
				}
			}
		}
		else {
			if(originY < y) {
				originY += Gdx.graphics.getDeltaTime()*velocity;
			}
			else
				isBorn = false;
		}
		delayAnimation += (double)velocity/200;
		if(delayAnimation > 5.0f) {
			if(++quack > 3)
				quack = 1;
			g.switchAnimation();
			delayAnimation = 0.0f;
		}
	}
	
	public Texture getHittedAnimation() {
		return g.getHitted();
	}
	
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public boolean isQuacking() {
		return quack == 2;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public boolean fall() {//restituisce true se deve ancora cadere
		quack = 0;
		if(isBorn) {
			originY -= Gdx.graphics.getDeltaTime()*velocity*2;
			if(originY > GraphicManager.MINIMUM_Y - 40)
				return false;
			return true;
		}
		y -= Gdx.graphics.getDeltaTime()*velocity*2;
		if(y > GraphicManager.MINIMUM_Y - 40)
			return false;
		return true;
	}
	
	public boolean isOut() {
		return isOut;
	}
	
	public Texture fallAnimation() {
		return g.fallAnimation(direction);
	}
	
	public boolean collide(float x, float y) {
		if(isBorn)
			return x >= this.x && x <= this.x+75 && y >= this.originY && y <= this.originY+75;
		return x >= this.x && x <= this.x+75 && y >= this.y && y <= this.y+75;
	}

}
