package com.bb.flatworld.bg;

import java.util.ArrayList;
import java.util.List;

import com.bb.flatworld.RenderManager;
import com.bb.flatworld.Renderable;
import com.bb.flatworld.Updatable;
import com.bb.flatworld.UpdateManager;


public class BackGround {
	
	private List<Layer> layers;
	
	public BackGround(){
		this.layers = new ArrayList<Layer>();
		//INIT
		layers.add(new FlatGroundLayer(1));
		layers.add(new TreeLayer(2));
		layers.add(new CloudLayer(8));
	}
	
	public void registerOn(UpdateManager updater, RenderManager renderer){
		for (Layer l: layers){
			if (l instanceof Updatable){
				System.out.println("BackGround#registerOn updatable layer: "+l);
				Updatable u = (Updatable)l;
				updater.add(u);
			}
			if (l instanceof Renderable){
				System.out.println("BackGround#registerOn renderable layer: "+l);
				Renderable r = (Renderable)l;
				renderer.add(r);
			}
		}
	}
	
}
