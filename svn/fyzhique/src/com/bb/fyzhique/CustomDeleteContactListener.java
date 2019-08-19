package com.bb.fyzhique;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public class CustomDeleteContactListener implements ContactListener{
	
	
	public CustomDeleteContactListener(World world) {
		super();
		this.world = world;
	}

	private World world;

	public void beginContact(Contact contact) {
//		System.out.println("Contact");
		if (contact.getFixtureA().getBody().getUserData() instanceof DestroyerOnContact){
			world.destroyBody(contact.getFixtureB().getBody());
		}
	}

	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

	public void preSolve(Contact contact, Manifold manifold) {
		// TODO Auto-generated method stub
		
	}

}
