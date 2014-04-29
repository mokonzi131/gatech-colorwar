package agent.reinforcement.neural.net;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

import agent.reinforcement.neural.function.Function;
import agent.reinforcement.neural.function.LogisticSigmoidFunction;

public class NeuralNode implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<NeuralLink> l = new ArrayList<NeuralLink>();

	private double i = 0, o = 0, e = 0;
	
	private Function f = new LogisticSigmoidFunction();
	
	public void addLink(NeuralNode n) {
		l.add(new NeuralLink(n));
	}

	public double getOutput() {
		return o;
	}
	
	public void setOutput(double o0) {
		o = o0;
	}

	public void propagate() {
		i = 0;
		for (NeuralLink l0 : l)
			i += l0.output();
		o = f.g(i);
		e = 0;
	}

	public void backpropogate() {
		double d = e * f.dg(i);
		for (NeuralLink l0 : l)
			l0.backpropagate(d);
	}

	public void addError(double d) {
		e += d;
	}

	public void setErr(double e0) {
		e += e0 - o;
	}

	public void setConfig(NeuralConfig c0) {
		for (NeuralLink l0 : l)
			l0.setConfig(c0);
	}
	
	public void setFunction(Function f0) {
		f = f0;
	}

	public void reset() {
		i = 0;
		o = 0;
		e = 0;
	}

	@Override
	public String toString() {
//		String s = String.format("%1$,.4f, %2$,.4f, %3$,.4f | ", i, o, e);
		String s = i + ", " + o + ", " + e + " | ";
		for (NeuralLink l0 : l)
			s += l0.toString() + ", ";
		return s;
	}

	public void rootTransfer(NeuralNode n0) {
		List<NeuralLink> l0 = n0.getLinkList();
		for (int i = 0; i < l.size(); i++) 
			l.get(i).rootTransfer(l0.get(i));
	}

	private List<NeuralLink> getLinkList() {
		return l;
	}

}
