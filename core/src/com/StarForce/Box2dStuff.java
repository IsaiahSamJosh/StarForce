package com.StarForce;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Box2dStuff {
public Box2dStuff(){
	
}
public BodyDef getBodyRef(BodyDef bdef, int xpos, int ypos, BodyType type){
	bdef = new BodyDef();
	bdef.position.set(xpos/B2DVars.PPM,ypos/B2DVars.PPM);
	bdef.type = type;
	return bdef;
}
public PolygonShape getPolygonShape(PolygonShape shape, float sizeX, float sizeY){
	shape = new PolygonShape();
	shape.setAsBox(sizeX/B2DVars.PPM/2f, sizeY/B2DVars.PPM/2f);
	return shape;
}
public PolygonShape getPolygonShape(PolygonShape shape, float sizeX, float sizeY, Vector2 vt, float fl){
	shape = new PolygonShape();
	shape.setAsBox(sizeX/B2DVars.PPM/2f, sizeY/B2DVars.PPM/2f, vt, fl);
	return shape;
}
public FixtureDef getFixtureDef(FixtureDef fdef, PolygonShape shape, short cbits, short mbits){
	fdef = new FixtureDef();
	fdef.shape=shape;
	switch(cbits){
	case B2DVars.BIT_ENEMY:
		cbits = B2DVars.BIT_ENEMY;
		break;
	case B2DVars.BIT_GROUND:
		cbits = B2DVars.BIT_GROUND;
		break;
	case B2DVars.BIT_PLAYER:
		cbits = B2DVars.BIT_PLAYER;
		break;
	}
	switch(mbits){
	case B2DVars.BIT_GROUND:
		mbits = B2DVars.BIT_GROUND;
		break;
	}
	fdef.filter.categoryBits=cbits;
	fdef.filter.maskBits=mbits;
	return fdef;
}
}
