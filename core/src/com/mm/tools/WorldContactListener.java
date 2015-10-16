package com.mm.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mm.sprites.InteractiveTileObject;
import com.mm.sprites.MegaMan;
import com.mm.sprites.enemies.Enemy;

public class WorldContactListener implements ContactListener{

	@Override
	public void beginContact(Contact contact) {		
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureA();
		
		//cdef=Collision Definition:
		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		Fixture mm;
		Fixture object;
		if (fixA.getUserData() == "enemy" || fixB.getUserData() == "enemy"){
			mm = fixA.getUserData() == "mm" ? fixA : fixB;
			object = mm == fixA ? fixB : fixA;
			Gdx.app.log("Begin contact" + object.getFilterData(), "");
			if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
				((InteractiveTileObject) object.getUserData()).onHit();
			}
		}
		
		switch (cDef){
		case MegaMan.MEGAMAN_BIT | MegaMan.ENEMY_BIT:
			Gdx.app.log("HIT ENEMY!!", "DIED");
			if (fixA.getFilterData().categoryBits == MegaMan.ENEMY_HEAD_BIT)
				((Enemy)fixA.getUserData()).onHit();
			else
				((Enemy)fixB.getUserData()).onHit();
			break;
		case MegaMan.ENEMY_BIT | MegaMan.OBJECT_BIT:
			if (fixA.getFilterData().categoryBits == MegaMan.ENEMY_BIT)
				((Enemy)fixA.getUserData()).reverseVelocity(true, false);
			else
				((Enemy)fixB.getUserData()).reverseVelocity(true,  false);
			break;		
			
		}
	}

	@Override
	public void endContact(Contact contact) {
		Gdx.app.log("End contact", "");
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
		
	}

}
