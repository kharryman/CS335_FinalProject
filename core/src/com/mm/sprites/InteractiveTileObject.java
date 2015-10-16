package com.mm.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mm.screens.PlayScreen;

public abstract class InteractiveTileObject {
	
	protected World world;
	protected Fixture fixture;
	protected TiledMap map;
	

public InteractiveTileObject(PlayScreen screen, Rectangle bounds){
	this.world = screen.getWorld();
	this.map = screen.getMap();
}

public abstract void onHit();

}