package creatures;

import java.awt.Color;
import java.awt.Graphics;

import tilegame.Handler;
import tilegame.ID;

import java.awt.Graphics;
	import java.awt.Rectangle;
	import java.awt.image.BufferedImage;
import java.util.Random;

import entities.Entity;
	import tilegame.Handler;
	import tilegame.gfx.Animation;
	import tilegame.gfx.Assets;
import tilegame.input.KeyManager;
	

	public class Slime extends Creature {
		
		//Animations
		private Animation animDown, animUp, animLeft, animRight;
		//Attack timer
		private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
		private long lastMoveTimer, moveCooldown = 800, moveTimer = moveCooldown;
	
		
		
		public Slime(Handler handler, ID id, float x, float y) {
			super(handler, id, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
			
			bounds.x = 30;
			bounds.y = 30;
			bounds.width = 80;
			bounds.height = 60;
			speed = 4;
			health = 2;
			
			//Animations
			animDown = new Animation(300, Assets.player_down);
			animUp = new Animation(300, Assets.player_up);
			animLeft = new Animation(300, Assets.player_left);
			animRight = new Animation(300, Assets.player_right);
			
			
		}

		@Override
		public void tick() {
			//Animations
			animDown.tick();
			animUp.tick();
			animRight.tick();
			animLeft.tick();
			
			//Movement
			
			getInput();
			move();
			
			
			//Attack
			checkAttacks();
			
			
		}
		

		private void checkAttacks() {
			attackTimer += System.currentTimeMillis()-lastAttackTimer;
			lastAttackTimer = System.currentTimeMillis();
			if(attackTimer <attackCooldown)
				return;
			
			
			Rectangle cb = getCollisionBounds(0,0);
			Rectangle ar = new Rectangle();
			int arSize = 80;
			ar.width = arSize;
			ar.height = arSize;

			
			if(yMove<0) {
				ar.x= cb.x+cb.width/2-arSize/2;
				ar.y = cb.y - arSize;
			}else if (yMove>0) {
				ar.x = cb.x+cb.width/2-arSize/2;
				ar.y=cb.y+cb.height;
			}else if (xMove<0) {
				ar.x = cb.x - arSize;
				ar.y=cb.y+cb.height/2-arSize/2;
			}else if (xMove>0) {
				ar.x = cb.x+cb.width;
				ar.y=cb.y+cb.height/2-arSize/2;
			}else {
				return;
			}
			
			attackTimer = 0;
			
			for(Entity player: handler.getWorld().getEntityManager().getEntities()) {
				if(player.equals(player))
					continue;
				if(player.getCollisionBounds(0, 0).intersects(ar)) {
					player.hurt(1);
					return;
				}
			}
			
		}
		
		@Override
		public void die() {
		
			System.out.println("Slime killed");
		}
		
		
		private void getInput(){
			moveTimer += System.currentTimeMillis()-lastMoveTimer;
			lastMoveTimer = System.currentTimeMillis();
			if(moveTimer <moveCooldown)
				return;
			Random rand = new Random();
			int moveCounter = rand.nextInt(4);
			xMove = 0;
			yMove = 0;
			
			if(moveCounter==0)
				yMove = -speed;
			else if(moveCounter==1)
				yMove =speed;
			else if(moveCounter==2)
				xMove = -speed;
			else if(moveCounter==3)
				xMove = speed;
			
			moveTimer = 0;
			}
			
		

		@Override
		public void render(Graphics g) {
			g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			g.setColor(Color.GRAY);
			g.fillRect((int) (x - handler.getGameCamera().getxOffset()+5), (int) (y - handler.getGameCamera().getyOffset()-20), 100, 10);
			g.setColor(Color.GREEN);
			g.fillRect((int) (x - handler.getGameCamera().getxOffset()+5), (int) (y - handler.getGameCamera().getyOffset()-20), health*50, 10);
		}
		
		private BufferedImage getCurrentAnimationFrame(){
			if(xMove < 0){
				return animLeft.getCurrentFrame();
			}else if(xMove > 0){
				return animRight.getCurrentFrame();
			}else if(yMove < 0){
				return animUp.getCurrentFrame();
			}else if (yMove >0){
				return animDown.getCurrentFrame();
			}else {
				return animRight.getCurrentFrame();
			}
		}

	}
