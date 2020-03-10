package tilegame.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 150, height = 150;
	public static Font font28;
	
	public static BufferedImage dirt, grass, stone, tree, rock, mountain, apple;
	public static BufferedImage[] player_down, player_up, player_left, player_right, player_attack;
	
	public static BufferedImage[] btn_start;
	public static BufferedImage inventoryScreen;

	public static void init(){
		
		//Load Images
		font28 = FontLoader.loadFont("res/fonts/slkscr.ttf", 28);
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
		inventoryScreen = ImageLoader.loadImage("/textures/inventoryScreen.png");
		
		
		//start Button
		btn_start = new BufferedImage[2];
		btn_start[0] = sheet.crop(0 , 2*height, width, height);
		btn_start[1] = sheet.crop(width, 2*height, width, height);
		
		
		//Player Movement
		player_down = new BufferedImage[2];
		player_up = new BufferedImage[2];
		player_left = new BufferedImage[3];
		player_right = new BufferedImage[3];
		player_attack = new BufferedImage[4];
		
		player_attack[0]= sheet.crop(5*width, 0, width, height);
		player_attack[1]= sheet.crop(6*width, 0, width, height);
		player_attack[2]= sheet.crop(5*width, 1*height, width, height);
		player_attack[3]= sheet.crop(6*width, 1*height, width, height);
		
		player_down[0] = sheet.crop(0, 0, width, height);
		player_down[1] = sheet.crop(0, 3*height, width, height);
		
		
		player_up[0] = sheet.crop(width, 0, width, height);
		player_up[1] = sheet.crop(2*width, 0, width, height);		
		
		
		player_right[0] = sheet.crop(width, height, width, height);
		player_right[1] = sheet.crop(2*width , height, width, height);
		player_right[2] = sheet.crop(2*width , 3*height, width, height);
		
		player_left[0] = sheet.crop(0, height, width, height);
		player_left[1] = sheet.crop(width, 3*height, width, height);
		player_left[2] = sheet.crop(3*width, 0, width, height);
		
		
		
		//Tiles
		dirt = sheet.crop(2*width,2*height, width,height);
		grass = sheet.crop(4*width,2*height, width,height);
		rock = sheet.crop(3*width,height, width,height);
		tree = sheet.crop(4*width,height, width,height);
		stone = sheet.crop(3*width,3*height, width,height);
		mountain = sheet.crop(3*width,2*height, width,height);
		apple = sheet.crop(6*width, 3*height, width, height);
	}
	
}
