package environment;

import java.awt.Color;

public class Astats {
	public int x;
	public int y;
	public int score;
	public int newScore;
	public boolean alive=true;
	
	public Color color(int scale) {
		float intensity = (float) score / scale * 255;
		return new Color(255, 0, 0, (int) intensity);
	}
}
