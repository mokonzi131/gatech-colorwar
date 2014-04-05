package environment.i;

import java.util.HashMap;

/**
 * Interface for retrieving symmetry encoded information about the game.  
 * @author John
 *
 */
public interface SymmetryBasedObserver extends StructuredObserver {
	
	/**
	 * Observes a structure that contains information on symmetry.  
	 * @return The symmetric mapping for each tile.  The type of symmetry is mapped to the observation
	 */
	public HashMap<Symmetry, Double[]>[][] observeSymmetry();
	
}
