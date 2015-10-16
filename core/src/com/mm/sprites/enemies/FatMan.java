package com.mm.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mm.main.MegaManGame;
import com.mm.screens.PlayScreen;
import com.mm.sprites.MegaMan;


public class FatMan extends Enemy{	
    private PlayScreen ps;
	private World myWorld;
	private TextureRegion body, eats1, eats2, eats3, runs1, runs2, jumps1, jumps2, falls1, falls2;	
	private MegaMan mm;
	
	private TextureRegion[] eats, runs, jumps, falls;	
	private Random rands;
	private int rannum;
    private boolean done_hit;
	private boolean done_ate, done_ran, done_jumped, done_fell;
	private int inc, ind;	
	private int level_progress = 0;

	
	public FatMan(PlayScreen screen,int startX, int startY) {
		super(screen,startX, startY);		
		rands = new Random();
		done_ate = false;
		done_ran = false;
		done_jumped = false;
		done_fell = false;
		done_hit=false;
	}

	
	@Override
	protected void defineEnemy(float x, float y) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onHit() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}	

}
