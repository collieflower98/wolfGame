package tilegame.entities.statics;

import entities.Entity;
import tilegame.Handler;
import tilegame.ID;

public abstract class StaticEntity extends Entity {
	
	public StaticEntity(Handler handler, ID id, float x, float y, int width, int height){
		super(handler, id, x, y, width, height);
	}

}
