package com.mm.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mm.main.MegaManGame;
import com.mm.screens.PlayScreen;
import com.mm.sprites.enemies.Met;

public class B2WorldCreator {	

	private Array<Met> mets;
	public B2WorldCreator(PlayScreen screen){
		World world = screen.getWorld();
		TiledMap map = screen.getMap();
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		
		//create and add background
				for(MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)){
					Rectangle rect = ((RectangleMapObject) object).getRectangle();
					bdef.type = BodyDef.BodyType.StaticBody;
					bdef.position.set((rect.getX() + rect.getWidth() / 2)/(MegaManGame.PPM + MegaManGame.SCALE_ADJUST), (rect.getY() + rect.getHeight() / 2)/(MegaManGame.PPM + MegaManGame.SCALE_ADJUST));
					body = world.createBody(bdef);
					
					shape.setAsBox((rect.getWidth() / 2)/(MegaManGame.PPM + MegaManGame.SCALE_ADJUST), (rect.getHeight() / 2)/(MegaManGame.PPM + MegaManGame.SCALE_ADJUST));
					fdef.shape = shape;
					body.createFixture(fdef);
				}
		
		
		
		//create and add ground
		for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2)/(MegaManGame.PPM + MegaManGame.SCALE_ADJUST), (rect.getY() + rect.getHeight() / 2)/(MegaManGame.PPM + MegaManGame.SCALE_ADJUST));
			body = world.createBody(bdef);
			
			shape.setAsBox((rect.getWidth() / 2)/(MegaManGame.PPM + MegaManGame.SCALE_ADJUST), (rect.getHeight() / 2)/(MegaManGame.PPM + MegaManGame.SCALE_ADJUST));
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		//create and add enemies
		mets = new Array<Met>();
		for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			mets.add(new Met(screen, rect.getX() / MegaManGame.PPM, rect.getY() / MegaManGame.PPM));
			Gdx.app.log("CREATED A MET", "");
		}
		
	}
	
	public Array<Met> getMets() {
		return mets;
	}


}
