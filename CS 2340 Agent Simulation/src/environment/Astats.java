package environment;

import java.awt.Color;

public class Astats {
	public int x;
	public int y;
	public int x0;
	public int y0;
	public int score;
	public int newScore;
	public boolean alive=true;
	
	public Color color(int scale) {
		int intensity = (score+1) * 255 / scale;
		if (intensity > 255)
			intensity = 255;
		return new Color(255, 0, 0, intensity);
	}
}
