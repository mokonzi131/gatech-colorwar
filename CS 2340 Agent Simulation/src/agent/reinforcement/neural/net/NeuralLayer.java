package agent.reinforcement.neural.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import agent.reinforcement.neural.function.Function;

public class NeuralLayer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<NeuralNode> n;
	private NeuralNode b;

	public NeuralLayer(int l) {
		n = new ArrayList<NeuralNode>(l);
		for (int i = 0; i < l; i++)
			n.add(new NeuralNode());
		b = new NeuralNode();
		b.setOutput(1);
		linkNode(b);
	}
	
	public void linkLayer(NeuralLayer l) {
		for (NeuralNode m : l.getNodeList()) 
			linkNode(m);
	}
	
	public void linkNode(NeuralNode m) {
		for (NeuralNode n0 : n) 
			n0.addLink(m);
	}

	public void propagate() {
		for (NeuralNode n0 : n) 
			n0.propagate();
	}
	
	public void backpropagate() {
		for (NeuralNode n0 : n) 
			n0.backpropogate();
	}
	
	public void addError(double[] e0) {
		if (e0.length != n.size())
			System.out.println("Neural Net Output Array Mismatch: "+e0.length+" "+n.size());
		for (int i = 0; i < e0.length; i++) 
			n.get(i).addError(e0[i]);
	}
	
	public void setOutput(double[] o0) {
		if (o0.length != n.size())
			System.out.println("Neural Net Output Array Mismatch: "+o0.length+" "+n.size());
		for (int i = 0; i < o0.length; i++) 
			n.get(i).setOutput(o0[i]);
	}
	
	public double[] getOutput() {
		double[] o = new double[n.size()];
		for (int i = 0; i < n.size(); i++) 
			o[i] = n.get(i).getOutput();
		return o;
	}

	public void setConfig(NeuralConfig c0) {
		for (NeuralNode n0 : n) 
			n0.setConfig(c0);
	}
	
	public void setFunction(Function f0) {
		for (NeuralNode n0 : n) 
			n0.setFunction(f0);
	}

	private List<NeuralNode> getNodeList() {
		return n;
	}

	public void reset() {
		for (NeuralNode n0 : n) 
			n0.reset();
	}
	
	@Override
	public String toString() {
		String s = "";
		for (NeuralNode n0 : n) 
			s += n0.toString() + "\n";
		return s;
	}

}
