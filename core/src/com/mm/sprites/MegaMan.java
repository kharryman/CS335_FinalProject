package com.mm.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mm.main.MegaManGame;

public class MegaMan extends Sprite{
	public World world; //world mega man will live in
	public Body b2body;
	private int MAP_BOX_SIZE = 16;
	private float ppm = MegaManGame.PPM;
	
	public MegaMan(World world, int startX, int startY){
		this.world = world;
		defineMegaMan(startX, startY);
	}
	
	public void defineMegaMan(int startX, int startY){
		BodyDef bdef = new BodyDef();
		bdef.position.set((startX)/ppm, (startY)/ppm); //start position on map
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape(); //mega mans hit box shape
		shape.setRadius(5/ppm);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
		if(this.b2body.getLinearVelocity().y > 2){
			this.b2body.setLinearVelocity(0, .01f);
		}
		
		
	}
	

}
