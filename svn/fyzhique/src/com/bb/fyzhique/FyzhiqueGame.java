package com.bb.fyzhique;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class FyzhiqueGame extends BasicGame{
	
	private World world;
	public RenderManager renderer;
	private Input input;
	
	private static final float DENSITY = 1.0f;
	private static final float FRICTION = 0.1f;
	
	private Body touchedBody;

	public FyzhiqueGame()  {
		super("Fyzhique");
		this.renderer = new RenderManager();
		initWithFactory();
//		initByHand();
	}
	
	private void initByHandWithSlope(){
 		
	}
	
	private void initByHand(){
		// creation de l'univers
		Vec2 gravity = new Vec2(0.0f, -10.0f);
		this.world = new World(gravity);
		BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyType.DYNAMIC;
	    bodyDef.position.set(0.60f, 0.60f);
	    Body body = world.createBody(bodyDef);
	    PolygonShape dynamicBox = new PolygonShape();
	    dynamicBox.setAsBox(0.10f, 0.10f);
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = dynamicBox;
	    fixtureDef.density = DENSITY;
	    fixtureDef.friction = FRICTION;
	    body.createFixture(fixtureDef);
	    bodyDef.position.set(0.60f, 0.30f);
	    Body body2 = world.createBody(bodyDef);
	    body2.createFixture(fixtureDef);
	    BodyDef groundDef = new BodyDef();
	    groundDef.type = BodyType.STATIC;
	    groundDef.position.set(0.0f, -30.0f);
	    Body groundBody = world.createBody(groundDef);
	    PolygonShape groundBox = new PolygonShape();
	    groundBox.setAsBox(50.0f, 1.0f);
	    groundBody.createFixture(groundBox, 0.0f);
	    // ajout graphique
//	    renderer.add(new RenderedSquare(new Color(256,100,100,150), body, 0.10f, 0.10f));
//	    renderer.add(new RenderedSquare(new Color(100,256,100,150), body2, 0.10f, 0.10f));
	    for (int j=0;j<10;j++){
	    	  for (int i=0;i<50;i++){
	  	    	addCircle(new Color(256,100,100,150), 0.2f, 10f+i, 0f-j*3f);
	  	    }
	    }
	    renderer.add(new RenderedSquare(new Color(100,100,256,150), groundBody, 50.0f, 1.0f));
		touchedBody = body;
	}
	
	
	private void addCircle(Color c, float radius, float x, float y){
		BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyType.DYNAMIC;
	    bodyDef.position.set(x,y);
	    Body body = world.createBody(bodyDef);
	    CircleShape shapeBox = new CircleShape();
	    shapeBox.setRadius(radius);
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shapeBox;
	    fixtureDef.density = DENSITY;
	    fixtureDef.friction = FRICTION;
	    body.createFixture(fixtureDef);
	    renderer.add(new RenderedCircle(c, body, radius));
	}
	
	private void initWithFactory(){
		  WorldFactory factory = WorldFactory.getWorldFactory("fyzhique/assets/world.txt");
		    this.world = factory.getWorld();
		    for (Renderable r: factory.getRenders()){
		    	  this.renderer.add(r);
		    }
		    touchedBody = world.getBodyList();
		    touchedBody.setUserData(new DestroyerOnContact());
		    world.setContactListener(new CustomDeleteContactListener(world));
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		renderer.render(g);
	}

	public void init(GameContainer gc) throws SlickException {
		this.input = gc.getInput();
		
	}

	public void update(GameContainer gc, int delay) throws SlickException {
		world.step(1.0f/60.0f, 4, 2);
		//actions
		
		float range = 500.0f;
		if (input.isKeyDown(Input.KEY_D)){
			touchedBody.applyLinearImpulse(new Vec2(range*1f, 0.0f), touchedBody.getWorldCenter());
		}
		if (input.isKeyDown(Input.KEY_Q)){
			touchedBody.applyLinearImpulse(new Vec2(range*-1f, 0.0f), touchedBody.getWorldCenter());
		}
		if (input.isKeyDown(Input.KEY_Z)){
			touchedBody.applyLinearImpulse(new Vec2(0.0f, range*1f), touchedBody.getWorldCenter());
		}
		if (input.isKeyDown(Input.KEY_S)){
			touchedBody.applyLinearImpulse(new Vec2(0.0f, -range*1f), touchedBody.getWorldCenter());
		}
		if (input.isKeyDown(Input.KEY_SPACE)){
			touchedBody.setTransform(new Vec2(range*100.0f, 0f),0f);
		}
		
		
	}

}
