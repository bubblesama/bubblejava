package com.bb.fyzhique;

import javax.swing.JFrame;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.framework.TestList;
import org.jbox2d.testbed.framework.TestbedController.UpdateBehavior;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

public class Test {

	public static void main(String[] args) {
		System.out.println("Tets");
//		launchTestBed();
		testPhysic();
	}
	
	public static void testPhysic(){
		Vec2 gravity = new Vec2(0.0f, -10.0f);
		World world = new World(gravity);
	    BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyType.DYNAMIC; // dynamic means it is subject to forces
	    bodyDef.position.set(0.0f, 4.0f);
	    Body body = world.createBody(bodyDef);
	    PolygonShape dynamicBox = new PolygonShape();
	    dynamicBox.setAsBox(1.0f, 1.0f);
	    FixtureDef fixtureDef = new FixtureDef(); // fixture def that we load up with the following info:
	    fixtureDef.shape = dynamicBox; // ... its shape is the dynamic box (2x2 rectangle)
	    fixtureDef.density = 1.0f; // ... its density is 1 (default is zero)
	    fixtureDef.friction = 0.3f; // ... its surface has some friction coefficient
	    body.createFixture(fixtureDef); // bind the dense, friction-laden fixture to the body
	    world.setContactListener(new CustomDeleteContactListener(world));
	    BodyDef groundDef = new BodyDef();
	    groundDef.type = BodyType.STATIC;
	    groundDef.position.set(0.0f, -60.0f);
	    Body groundBody = world.createBody(groundDef);
	    PolygonShape groundBox = new PolygonShape();
	    groundBox.setAsBox(50.0f, 10.0f);
	    groundBody.createFixture(groundBox, 0.0f);
	    // Simulate the world
	    float timeStep = 1.0f / 60.f;
	    int velocityIterations = 6;
	    int positionIterations = 2;
	    for (int i = 0; i < 4*60; ++i) {
	      world.step(timeStep, velocityIterations, positionIterations);
	      Vec2 position = body.getPosition();
	      float angle = body.getAngle();
	      System.out.printf("%4.2f %4.2f %4.2f\n", position.x, position.y, angle);
	    }
	    body.applyLinearImpulse(new Vec2(0, 20), body.getWorldCenter());
	    for (int i = 0; i < 60; ++i) {
		      world.step(timeStep, velocityIterations, positionIterations);
		      Vec2 position = body.getPosition();
		      float angle = body.getAngle();
		      System.out.printf("%4.2f %4.2f %4.2f\n", position.x, position.y, angle);
		    }
//	    AABB aabb = new AA
//	    world.queryAABB(callback, aabb)
	}
	
	public static void launchTestBed(){
		TestbedModel model = new TestbedModel();         // create our model
		// add tests
		TestList.populateModel(model);                   // populate the provided testbed tests
//		model.addCategory("My Super Tests");             // add a category
//		model.addTest(new PyramidTest());                // add our test
		// add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
//		model.getSettings().addSetting(new TestbedSetting("My Range Setting", SettingType.ENGINE, 10, 0, 20));
		TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel
		JFrame testbed = new TestbedFrame(model, panel,UpdateBehavior.UPDATE_CALLED); // put both into our testbed frame
		// etc
		testbed.setVisible(true);
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
