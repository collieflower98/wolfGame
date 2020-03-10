package tilegame.worlds;

import java.awt.Graphics;

import creatures.Player;
import creatures.Slime;
import entities.EntityManager;
import tilegame.Handler;
import tilegame.ID;
import tilegame.entities.statics.Rock;
import tilegame.entities.statics.Apple;
import tilegame.items.ItemManager;
import tilegame.tiles.Tile;
import tilegame.utils.Utils;

public class World {

	private Handler handler;
	private ID id;
	private int width, height;
	private int spawnX, spawnY, spawnX2, spawnY2, spawnX3, spawnY3, spawnX4, spawnY4, spawnX5, spawnY5;
	private int[][] tiles;
	//Entities
	private EntityManager entityManager;
	
	//Items
	private ItemManager itemManager;
	
	public World(Handler handler, String path){
		this.handler = handler;
		entityManager = new EntityManager(handler, id, new Player(handler, id, 100, 100), new Slime(handler, id,  100,100),new Slime(handler, id, 100,100),
						new Slime(handler, id, 100,100), new Slime(handler,id,100,100));
		itemManager = new ItemManager(handler);
		// Temporary entity code!
		
		entityManager.addEntity(new Apple(handler, id, 300, 350));
		entityManager.addEntity(new Apple(handler, id,900, 300));
		entityManager.addEntity(new Apple(handler, id,1800, 250));
		entityManager.addEntity(new Apple(handler, id, 2700, 700));
		entityManager.addEntity(new Apple(handler, id, 3600, 1350));
		entityManager.addEntity(new Apple(handler, id, 300, 1350));
		entityManager.addEntity(new Apple(handler, id, 900, 1300));
		entityManager.addEntity(new Apple(handler, id, 1800, 2250));
		entityManager.addEntity(new Apple(handler, id, 2700, 3700));
		entityManager.addEntity(new Apple(handler, id, 3600, 2350));
		entityManager.addEntity(new Rock(handler, id, 600, 600));
		entityManager.addEntity(new Rock(handler, id ,1600, 600));
		entityManager.addEntity(new Rock(handler, id, 2600, 1600));
		entityManager.addEntity(new Rock(handler, id ,3600, 2600));
		entityManager.addEntity(new Rock(handler, id , 1200, 3600));
		
		
		loadWorld(path);
		
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
		entityManager.getSlime().setX(spawnX2);
		entityManager.getSlime().setY(spawnY2);
		entityManager.getSlime2().setX(spawnX3);
		entityManager.getSlime2().setY(spawnY3);
		entityManager.getSlime3().setX(spawnX4);
		entityManager.getSlime3().setY(spawnY4);
		entityManager.getSlime4().setX(spawnX5);
		entityManager.getSlime4().setY(spawnY5);
	}
	
	public void tick(){
		itemManager.tick();
		entityManager.tick();
	}
	
	public void render(Graphics g){
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);
		
		for(int y = yStart;y < yEnd;y++){
			for(int x = xStart;x < xEnd;x++){
				getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()),
						(int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		
		//Items
		itemManager.render(g);
		//Entities
		entityManager.render(g);
	}
	
	
	
	

	public Tile getTile(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.grassTile;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if(t == null)
			return Tile.dirtTile;
		return t;
	}
	
	private void loadWorld(String path){
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		spawnX2 = Utils.parseInt(tokens[4]);
		spawnY2 = Utils.parseInt(tokens[5]);
		spawnX3 = Utils.parseInt(tokens[6]);
		spawnY3 = Utils.parseInt(tokens[7]);
		spawnX4 = Utils.parseInt(tokens[8]);
		spawnY4 = Utils.parseInt(tokens[9]);
		spawnX5 = Utils.parseInt(tokens[10]);
		spawnY5 = Utils.parseInt(tokens[11]);
		
		
		tiles = new int[width][height];
		for(int y = 0;y < height;y++){
			for(int x = 0;x < width;x++){
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 12]);
			}
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
}








