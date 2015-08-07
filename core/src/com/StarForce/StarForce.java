package com.StarForce;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;

public class StarForce extends Game {
	FPSLogger fps;
	
	@Override
	public void create () {
  setScreen(new MainMenuScreen(this));
  fps = new FPSLogger();
	}

	@Override
	public void render () {
        super.render();
        //fps.log();
	}
	
	public void dispose(){
		super.dispose();
		
		getScreen().dispose();
	}
	
	public void resize(int width, int height){
		super.resize(width,height);
	}
	
	public void pause(){
		super.pause();
	}
	
	public void resume(){
		super.resume();
	}
}