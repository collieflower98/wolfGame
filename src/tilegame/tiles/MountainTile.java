package tilegame.tiles;

import tilegame.gfx.Assets;

public class MountainTile extends Tile {

	public MountainTile(int id) {
		super(Assets.mountain, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}

