package environment.colorwar;

public class Cell {
	float m_posx, m_posy;
	boolean m_resource;
	
	public Cell(float x, float y, boolean resource) {
		m_posx = x;
		m_posy = y;
		m_resource = resource;
	}
	
	public float[] coords() { return new float[]{m_posx, m_posy}; }
	public boolean containsResource() { return m_resource; }
}
