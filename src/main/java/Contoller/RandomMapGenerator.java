package Contoller;

import java.util.ArrayList;
import java.util.Random;

import Models.Game;
import Models.Tile.Terrain;
import Models.Tile.TerrainFeature;
import Models.Tile.Tile;

public class RandomMapGenerator {
	private static RandomMapGenerator instance;
	public static RandomMapGenerator getInstance(){
		if (instance==null) instance = new RandomMapGenerator();
		return instance;
	}

	public void generateRandomMap(Game game, Tile[][] tiles, Random random){
		Terrain[] terrains=Terrain.values();
		tiles = new Tile[game.WIDTH][game.HEIGHT];
		for (int i=0; i<game.WIDTH; i++){
			for (int j=0; j<game.HEIGHT; j++){
				tiles[i][j]=new Tile(i, j, terrains[random.nextInt(terrains.length)]);
			}
		}
		setAdjTiles(game, tiles);
		setRivers(game, tiles, random, 20); // 1/20 of all borders
		setTerrainFeatures(game, tiles, random, 3); // 1/3 of tiles have feature
	}
	private void setAdjTiles(Game game, Tile[][] tiles){
		for (int i=0; i<game.WIDTH; i++){
			for (int j=0; j<game.HEIGHT; j++){
				if (j>0){
					tiles[i][j].setAdjTile(0, tiles[i][j-1]);
					tiles[i][j-1].setAdjTile(3, tiles[i][j]);
				}
				if (i%2==1) continue ;
				if (i+1<game.WIDTH){
					tiles[i][j].setAdjTile(1, tiles[i+1][j]);
					tiles[i+1][j].setAdjTile(4, tiles[i][j]);
				}
				if (i+1<game.WIDTH && j+1<game.HEIGHT){
					tiles[i][j].setAdjTile(2, tiles[i+1][j+1]);
					tiles[i+1][j+1].setAdjTile(5, tiles[i][j]);
				}
				if (i>0 && j+1<game.HEIGHT){
					tiles[i][j].setAdjTile(4, tiles[i-1][j+1]);
					tiles[i-1][j+1].setAdjTile(1, tiles[i][j]);
				}
				if (i>0){
					tiles[i][j].setAdjTile(5, tiles[i-1][j]);
					tiles[i-1][j].setAdjTile(2, tiles[i][j]);
				}
			}
		}
	}

	private void setRivers(Game game, Tile[][] tiles, Random random, int prob){
		for (int i=0; i<game.WIDTH; i++){
			for (int j=0; j<game.HEIGHT; j++){
				for (int d=0; d<3; d++){
					Tile x=tiles[i][j];
					Tile y=x.getAdjTile(d);
					if (y==null) continue ;
					if (random.nextInt(prob)==0){
						x.setRiver(d, true);
						y.setRiver(d+3, true);
					}
				}
			}
		}
	}

	private void setTerrainFeatures(Game game, Tile[][] tiles, Random random, int prob){
		for (int i=0; i<game.WIDTH; i++){
			for (int j=0; j<game.HEIGHT; j++){
				ArrayList<TerrainFeature> possibleTerrainFeatures = new ArrayList<>();
				for (TerrainFeature terrainFeature : TerrainFeature.values()){
					if (tiles[i][j].isTerrainFeatureCompatible(terrainFeature))
						possibleTerrainFeatures.add(terrainFeature);
				}
				if (possibleTerrainFeatures.isEmpty()) continue ;
				if (random.nextInt(prob)>0) continue ;
				tiles[i][j].setTerrainFeature(possibleTerrainFeatures.get(random.nextInt(possibleTerrainFeatures.size())));
			}
		}
	}
	


}
