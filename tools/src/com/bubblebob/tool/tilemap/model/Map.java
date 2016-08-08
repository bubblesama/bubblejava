package com.bubblebob.tool.tilemap.model;

import java.util.List;

public interface Map {

		public Tile getTile(int x, int y);
		public List<Tile> getNeighbours(int x, int y);
		public int getWidth();
		public int getHeight();

}
