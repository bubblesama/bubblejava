package com.bb.fyzhique;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;

public class WorldFactory {

	// DYNAMIC x10 y20 w10 h20 r100 g100 b100
	// STATIC  x10 y20 w10 h20 r200 g100 b100

	private static final String DYNAMIC = "DYNAMIC";
	private static final String STATIC = "STATIC";


	private World world;
	private String worldDescFileName;
	private List<Renderable> renders;

	public static WorldFactory getWorldFactory(String worldDescFileName){
		return new WorldFactory(worldDescFileName);
	}

	private WorldFactory(String worldDescFileName) {
		super();
		this.worldDescFileName = worldDescFileName;
	}

	public World getWorld(){
		if (world == null){
			createWorld();
		}
		return world;
	}

	public List<Renderable> getRenders(){
		if (world == null){
			createWorld();
		}
		return renders;
	}

	private void createWorld(){
		Vec2 gravity = new Vec2(0f,-10f);
		world = new World(gravity);
		renders = new ArrayList<Renderable>();
		try{
			InputStream ips=new FileInputStream(worldDescFileName); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String line;
			while ((line=br.readLine())!=null){
				System.out.println(line);
				processLine(line);
			}
			br.close(); 
		}		
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private void processLine(String line){
		float scale = 1.0f;
		String[] params = line.split(" ");
		float x = scale*Float.parseFloat(params[1].substring(1));
		float y = scale*Float.parseFloat(params[2].substring(1));
		float w = scale*Float.parseFloat(params[3].substring(1));
		float h = scale*Float.parseFloat(params[4].substring(1));
		int r = Integer.parseInt(params[5].substring(1));
		int g = Integer.parseInt(params[6].substring(1));
		int b = Integer.parseInt(params[7].substring(1));
		BodyDef bodyDef = new BodyDef();
		Shape box = null;
		if (params[0].equals(DYNAMIC)){
			bodyDef.type = BodyType.DYNAMIC;
			CircleShape circleBox = new CircleShape();
			circleBox.setRadius(w);
			box = circleBox;
		}else{
			bodyDef.type = BodyType.STATIC;
			PolygonShape polyBox = new PolygonShape();
			polyBox.setAsBox(w, h);
			box = polyBox;
		}
		bodyDef.position.set(x, y);
		bodyDef.linearDamping = 0.1f;
		int number = 1;
		if (params.length > 8){
			number =  Integer.parseInt(params[8].substring(1));
		}
		for (int i=0;i<number;i++){
			Body body = world.createBody(bodyDef);
			if (params[0].equals(DYNAMIC)){
				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.shape = box;
				fixtureDef.density = 5.0f;
				fixtureDef.friction = 0.1f;
				body.createFixture(fixtureDef);
			}else{
				body.createFixture(box,0.0f);
			}
			if (params[0].equals(DYNAMIC)){
				renders.add(new RenderedCircle(new Color(r, g, b), body, w));
			}else{
				renders.add(new RenderedSquare(new Color(r, g, b), body, w, h));
			}

		}
	}


}
