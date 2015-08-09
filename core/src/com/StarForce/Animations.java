package com.StarForce;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animations {
public Animations(){
	
}
public Texture getTexture(Texture tx,String st){
	return tx=new Texture(Gdx.files.internal(st));
}
public TextureAtlas getTextureAtlas(TextureAtlas ta, String st){
return ta = new TextureAtlas(Gdx.files.internal(st));	
}
public TextureRegion getTextureRegion(TextureAtlas ta,TextureRegion tr, String st){
return tr=ta.findRegion(st);	
}
public TextureRegion getTextureRegion(TextureRegion tr, TextureRegion tb){
	tr = new TextureRegion(tb);
	tr.flip(true, false);
	return tr;
}
public TextureRegion[] getTextureRegions(TextureAtlas ta,TextureRegion[] ts, int size,String st){
	ts = new TextureRegion[size];
	for(int i=0; i<size; i++){
	ts[i] =ta.findRegion(st + (i + 2));
	}
	return ts;
}
public TextureRegion[] getTextureRegions(TextureAtlas ta,TextureRegion[] tr, TextureRegion[] ts, int size){
	tr = new TextureRegion[size];
	for(int i=0; i<size; i++){
	tr[i] = new TextureRegion(ts[i]);
     tr[i].flip(true, false);
	}
	return tr;
}
//tricky method, has to work this way otherwise there are overwritten problems with the texture regions!
public Animation getAnimation(Animation ani, TextureRegion[] reg){
return ani= new Animation(.06f, reg); 
}
}
