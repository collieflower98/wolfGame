package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entities.Entity;
import tilegame.Handler;
import tilegame.ID;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.input.KeyManager;
import tilegame.inventory.Inventory;

public class Player extends Creature {
	
	//Animations
	private Animation animDown, animUp, animLeft, animRight, animAttack;
	//Attack timer
	private long lastAttackTimer, attackCooldown = 500, attackTimer = attackCooldown;
	//Inventory
	private Inventory inventory;
	private boolean attackReady=true;
	
	
	public Player(Handler handler,ID id, float x, float y) {
		super(handler, id, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		bounds.x = 22;
		bounds.y = 42;
		bounds.width = 80;
		bounds.height = 50;
		health = 10;
		
		
		//Animations
		animDown = new Animation(200, Assets.player_down);
		animUp = new Animation(200, Assets.player_up);
		animLeft = new Animation(200, Assets.player_left);
		animRight = new Animation(200, Assets.player_right);
		animAttack = new Animation(100, Assets.player_attack);
		inventory = new Inventory(handler);
	}

	@Override
	public void tick() {
		//Animations
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		animAttack.tick();
		//Movement
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
		
		//Attack
		checkAttacks();
		
		//Inventory
		inventory.tick();
		
	}
	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	
	
	private void checkAttacks() {
		attackTimer += System.currentTimeMillis()-lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer <attackCooldown) {
			attackReadyF();
			return;
		}
		
		Rectangle cb = getCollisionBounds(0,0);
		Rectangle ar = new Rectangle();
		int arSize = 100;
		ar.width = arSize;
		ar.height = arSize;
		
		
		
		if(handler.getKeyManager().attack && KeyManager.getCounter()==0) {
			ar.x= cb.x+cb.width/2-arSize/2;
			ar.y = cb.y - arSize;
		}else if (handler.getKeyManager().attack&&KeyManager.getCounter()==1) {
			ar.x = cb.x+cb.width/2-arSize/2;
			ar.y=cb.y+cb.height;
		}else if (handler.getKeyManager().attack&&KeyManager.getCounter()==2) {
			ar.x = cb.x - arSize;
			ar.y=cb.y+cb.height/2-arSize/2;
		}else if (handler.getKeyManager().attack&&KeyManager.getCounter()==3) {
			ar.x = cb.x+cb.width;
			ar.y=cb.y+cb.height/2-arSize/2;
		}else {
			return;
		}
		
		attackTimer = 0;
		
		for(Entity e: handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				e.hurt(1);
				return;
				
			}
		}
		
		attackReadyT();
	}
	public void attackReadyT() {
		attackReady = true;
	}
	
	public void attackReadyF() {
		attackReady = false;
	}
	
	
	
	@Override
	public void die() {
	
		System.out.println("Game Over");
	}
	
	private void getInput(){
		xMove = 0;
		yMove = 0;
		
		if(inventory.isActive())
			return;
		
		if(handler.getKeyManager().up)
			yMove = -speed;
		if(handler.getKeyManager().down)
			yMove = speed;
		if(handler.getKeyManager().left)
			xMove = -speed;
		if(handler.getKeyManager().right)
			xMove = speed;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		g.setColor(Color.gray);
		g.fillRect(100, 50, 500, 50);
		g.setColor(Color.green);
		g.fillRect(100, 50, health*50, 50);
		
		/*g.setColor(Color.red);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()),
				bounds.width, bounds.height);*/
	}
	public void postRender(Graphics g) {
		inventory.render(g);
	}
	
	private BufferedImage getCurrentAnimationFrame(){
		if(handler.getKeyManager().attack&& attackReady==false) {
			return animAttack.getCurrentFrame();
		}else if(handler.getKeyManager().left){
			return animLeft.getCurrentFrame();
		}else if(handler.getKeyManager().right){
			return animRight.getCurrentFrame();
		}else if(handler.getKeyManager().up){
			return animUp.getCurrentFrame();
		}else if (handler.getKeyManager().down){
			return animDown.getCurrentFrame();
		}else {
			return animRight.getCurrentFrame();
		}
	}

}
