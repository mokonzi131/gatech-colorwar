package environment;

public class Square {
	public boolean available; //is it available? 1 yes, 0 no
	public int agentScore=0; //is there an agent already on block? if so it will have agent #, 0 if not
	public boolean Color; //is there a color you can collect? 1 yes, 0 no
	public int agent=-1; //which agent is on it, -1 if no agent on square	
}
