package tilegame.entities.statics;

import java.awt.Graphics;


import tilegame.Handler;
import tilegame.ID;
import tilegame.gfx.Assets;
import tilegame.items.Item;
import tilegame.tiles.Tile;

public class Apple extends StaticEntity {

	public Apple(Handler handler, ID id,  float x, float y) {
		super(handler, id, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);
		
		bounds.x = 10;
		bounds.y = (int) (height / 1.5f);
		bounds.width = width - 20;
		bounds.height = (int) (height - height / 1.5f);
		
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void die() {
		
		System.out.println("Tree Killed");
		handler.getWorld().getItemManager().addItem(Item.appleItem.createNew((int)x,(int) y));
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

}
