package entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import creatures.Player;
import creatures.Slime;
import tilegame.Handler;
import tilegame.ID;

public class EntityManager {
	
	private Handler handler;
	private ID id;
	private Player player;
	private Slime slime, slime2, slime3, slime4;
	private ArrayList<Entity> entities;
	private Comparator<Entity> renderSorter = new Comparator<Entity>(){
		@Override
		public int compare(Entity a, Entity b) {
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight())
				return -1;
			return 1;
		}
	};
	
	public EntityManager(Handler handler, ID id,  Player player, Slime slime, Slime slime2, Slime slime3, Slime slime4){
		this.handler = handler;
		this.id = id;
		this.player = player;
		this.slime = slime;
		this.slime2 = slime2;
		this.slime3 = slime3;
		this.slime4 = slime4;
		
		entities = new ArrayList<Entity>();
		addEntity(player);
		addEntity(slime);
		addEntity(slime2);
		addEntity(slime3);
		addEntity(slime4);
		
	}
	
	

	public void tick(){
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()){
			Entity e = it.next();
			e.tick();
			if(!e.isActive())
				it.remove();
		}
		entities.sort(renderSorter);
	}
	
	

	public void render(Graphics g){
		for(Entity e : entities){
			e.render(g);
		}
		player.postRender(g);
	}
	
	
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	//GETTERS SETTERS
	public Slime getSlime() {
		return slime;
	}

	public void setSlime(Slime slime) {
		this.slime = slime;
	}

	public Slime getSlime2() {
		return slime2;
	}

	public void setSlime2(Slime slime2) {
		this.slime2 = slime2;
	}

	public Slime getSlime3() {
		return slime3;
	}

	public void setSlime3(Slime slime3) {
		this.slime3 = slime3;
	}
	
	public Slime getSlime4() {
		return slime4;
	}

	public void setSlime4(Slime slime4) {
		this.slime4 = slime4;
	}
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

}