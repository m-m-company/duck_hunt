package com.duck_hunt;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Queue;

public class Duck {
	
	private final static int RIGHT = 0;
	private final static int LEFT = 1;
	private final static int TOP_LEFT = 2;
	private final static int TOP_RIGHT = 3;
	
	private String directions[] = {"right", "left", "topRight", "topLeft"};
	private Queue<Texture> animations;
	private Texture frame;
	private int direction;
	private float x;
	private float originY;
	private float y;
	private boolean isBorn;
	private boolean isDead;
	
	private void loadAnimations(int dir) {
		animations = new Queue<Texture>();
		for(int i = 0; i < 3; ++i)
			animations.addLast(new Texture("duck/" + directions[dir] + i + ".png"));
		frame = animations.first();
	}
	
	private void spawn() {
		isBorn = true;
		originY = Background.MINIMUM_Y;
		Random r = new Random();
		x = r.nextInt(Background.MAXIMUM_X);
		y = r.nextInt(Background.MAXIMUM_Y);//fix the y
		while(y < Background.MINIMUM_Y)
			y = r.nextInt(Background.MAXIMUM_Y);
		direction = r.nextInt(directions.length);
		loadAnimations(direction);
	}
	
	public Duck() {
		isDead = false;
		spawn();
	}
	
	public Texture getFrame() {
		return frame;
	}
	
	private void switchAnimation() {
		frame = animations.removeFirst();
		animations.addLast(frame);
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
		if(!isBorn) {
			switch(direction) {
				case Duck.RIGHT:
					x += Gdx.graphics.getDeltaTime();
					break;
				case Duck.LEFT:
					x -= Gdx.graphics.getDeltaTime();
					break;
				case Duck.TOP_LEFT:
					x -= Gdx.graphics.getDeltaTime();
					y += Gdx.graphics.getDeltaTime();
					break;
				case Duck.TOP_RIGHT:
					x += Gdx.graphics.getDeltaTime();
					y += Gdx.graphics.getDeltaTime();
					break;
				default:
					System.out.println("ERROR");
					break;
			}
			switchAnimation();
		}
		else {
			if(originY < y) {
				originY += Gdx.graphics.getDeltaTime()*200;
				System.out.println(originY + " " + y);
			}
			else
				isBorn = false;
		}
	}
	
	public Texture getDeathAnimation() {
		isDead = true;
		return new Texture("duck/hitted.png");
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public boolean fall() {//restituisce true se deve ancora cadere
		y -= Gdx.graphics.getDeltaTime();
		if(y > Background.MAXIMUM_Y)
			return false;
		return true;
	}
	
	public Texture getFallAnimation() {
		if(direction == Duck.LEFT || direction == Duck.TOP_LEFT)
			return new Texture("duck/fallLeft.png");
		else
			return new Texture("duck/fallRight.png");
	}

}
