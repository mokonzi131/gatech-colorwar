package agent.reinforcement.neural.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import agent.reinforcement.neural.function.Function;

public class NeuralNet implements Serializable {
	
	List<NeuralLayer> l;

	public NeuralNet(int[] h) {
		l = new ArrayList<NeuralLayer>(h.length);
		for (int i = 0; i < h.length; i++) 
			l.add(new NeuralLayer(h[i]));
	}
	
	public void linkLayers() {
		for (int i = 1; i < l.size(); i++) {
			l.get(i).linkLayer(l.get(i - 1));
		}
	}
	
	public void propagate() {
		for (int i = 1; i < l.size(); i++) 
			l.get(i).propagate();
	}
	
	public void backpropagate() {
		for (int i = l.size() - 1; i > 0; i--) 
			l.get(i).backpropagate();
	}
	
	public void setInput(double[] i0) {
		l.get(0).setOutput(i0);
	}
	
	public void addError(double[] e0) {
		l.get(l.size() - 1).addError(e0);
	}
	
	public double[] getOutput() {
		return l.get(l.size() - 1).getOutput();
	}
	
	public void setConfig(NeuralConfig c0) {
		for (NeuralLayer l0 : l)
			l0.setConfig(c0);
	}
	
	public void setOutputFunction(Function f0) {
		l.get(l.size() - 1).setFunction(f0);
	}

	public void reset() {
		for (NeuralLayer l0 : l)
			l0.reset();
	}
	
	@Override
	public String toString() {
		String s = "";
		for (NeuralLayer l0 : l) {
			s += l0.toString() + "\n";
		}
		return s;
	}

}
