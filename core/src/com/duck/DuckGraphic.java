package com.duck;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Queue;

//le animazioni vengono gestite all'interno di un array
public class DuckGraphic {

	private String directions[] = {"right", "left", "topLeft", "topRight", "lowerLeft", "lowerRight"};
	private Queue<Texture> animations;
	private Texture frame;
	private Texture fallLeft;
	private Texture fallRight;
	private Texture hitted;
	
	public DuckGraphic() {
		animations = null;
		frame = null;
		fallLeft = new Texture("duck/fallLeft.png");
		fallRight = new Texture("duck/fallRight.png");
		hitted = new Texture("duck/hitted.png");
	}
	
	public void switchAnimation() {
		frame = animations.removeFirst();
		animations.addLast(frame);
	}
	
	public Texture getFrame() {
		return frame;
	}
	
	public void setAnimations(int direction) {
		animations = new Queue<Texture>();
		for(int i = 0; i < 3; ++i)
			animations.addLast(new Texture("duck/" + directions[direction] + i + ".png"));
		frame = animations.first();
	}
	
	public int getMaximumDirections() {
		return directions.length;
	}
	
	public Texture fallAnimation(int direction) {
		if(direction == Duck.LEFT || direction == Duck.TOP_LEFT || direction == Duck.LOWER_LEFT)
			return fallLeft;
		else
			return fallRight;
	}
	
	public Texture getHitted() {
		return hitted;
	}
	
}
