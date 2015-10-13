package com.mm.sprites.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mm.main.MegaManGame;

public abstract class Enemy extends Sprite{
	public World world;
	public Body b2body;
	private int MAP_BOX_SIZE = 16;
	public float ppm = MegaManGame.PPM;
	public BodyDef bdef = new BodyDef();
	public int hitPoints;
	public int damageCaused;
	
	public Enemy(World world, int startX, int startY) {
		this.world = world;
		defineEnemy(startX, startY);
	}
	
	public abstract void defineEnemy(int startX, int startY);
	

	
}
