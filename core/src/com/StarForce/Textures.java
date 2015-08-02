package com.StarForce;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Textures {
	private static final float RUNNING_FRAME_DURATION = 0.06f;
	
	private TextureRegion heroIdleLeft;
	TextureRegion[] walkRightFrames;
	TextureRegion[] walkLeftFrames;
	private TextureRegion heroIdleRight;
	private TextureRegion heroFrame;
	private TextureRegion heroJumpLeft;
	private TextureRegion heroFallLeft;
	private TextureRegion heroJumpRight;
	private TextureRegion heroFallRight;
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;
	TextureAtlas atlas;
	public Textures(){
		
	}
public void loadTextures(){
	heroIdleLeft = atlas.findRegion("Ntransparent01");
	heroIdleRight = new TextureRegion(heroIdleLeft);
	heroIdleRight.flip(true, false);
	heroJumpLeft = atlas.findRegion("Ntransparent01");
	heroJumpRight = new TextureRegion(heroJumpLeft);
	heroJumpRight.flip(true, false);
	heroFallLeft = atlas.findRegion("Ntransparent01");
	heroFallRight = new TextureRegion(heroFallLeft);
	heroFallRight.flip(true, false); //first param flips x, second param flips y
}
public void loadTextureRegions(){
	walkLeftFrames = new TextureRegion[5];
	for (int i = 0; i < 5; i++) {
		walkLeftFrames[i] = atlas.findRegion("Ntransparent0" + (i + 2));
	}
	walkRightFrames = new TextureRegion[5];

	for (int i = 0; i < 5; i++) {
		walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
		walkRightFrames[i].flip(true, false); //first param flips x, second param flips y
	}
}
public void loadTextureAtlas(){
	atlas = new TextureAtlas(Gdx.files.internal("PlayerSprite.txt"));	
	
}
public void loadAnimation(){
	walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);
	walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
}
public TextureRegion getHeroFrame(){
	return heroFrame;
}
public TextureRegion getheroIdleRight(){
	return heroIdleRight;
}
public TextureRegion getheroIdleLeft(){
	return heroIdleLeft;
}
public Animation getwalkLeftAnimation(){
	return walkLeftAnimation;
}
public Animation getwalkRightAnimation(){
	return walkRightAnimation;
}
public TextureAtlas getAtlas(){
	return atlas;
}
}