package com.StarForce;

import java.util.HashMap;
import java.util.Map;

import com.StarForce.Hero.State;
import com.StarForce.Hero;
import com.badlogic.gdx.math.Vector2;





public class WorldController {
	private Hero hero;
	private static final float MAX_VEL = 2f;
    enum Keys {
        LEFT, RIGHT, CLIMBING, FIRE, W, S, rBLOCK, lBLOCK
}
	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
	        keys.put(Keys.LEFT, false);
	        keys.put(Keys.W, false);
	        keys.put(Keys.S, false);
	        keys.put(Keys.RIGHT, false);
	};
	public WorldController(WorldRenderer renderer, StarForce game) { //was World world!
this.hero=renderer.getHero();
}
	public void leftPressed() {
        keys.get(keys.put(Keys.LEFT, true));
        //left arrow key
}
	public void rightPressed() {
        keys.get(keys.put(Keys.RIGHT, true));
}
	public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
}

public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));

}
public void update(float delta) {
	processInput(delta);
	updateHero(delta);
}
public void updateHero(float delta){
	hero.update(delta);
	if (hero.getVelocity().x > MAX_VEL) {
		hero.getVelocity().x = MAX_VEL;
	}
	if (hero.getVelocity().x < -MAX_VEL) {
		hero.getVelocity().x = -MAX_VEL;
	}
}
private void processInput(float delta) {
	if (keys.get(Keys.LEFT)) {
		hero.getBody().applyForceToCenter(new Vector2(-10000, 0), true);
		hero.setFacingLeft(true);
        hero.setState(State.WALKING);
        hero.getVelocity().x = -Hero.SPEED;
	}
	if (keys.get(Keys.RIGHT)) {
		hero.getBody().applyForceToCenter(new Vector2(10000, 0), true);
		hero.setFacingLeft(false);
        hero.setState(State.WALKING);
        hero.getVelocity().x = Hero.SPEED;
	}
	if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
            (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
		 hero.setState(State.IDLE);
         // horizontal speed is 0
         hero.getVelocity().x = 0;
	}
}
}
