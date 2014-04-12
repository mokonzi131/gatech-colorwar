package environment;

public class Square {
	public double available; //is it available? 1 yes, 0 no
	public double agent; //is there an agent already on block? if so it will have agent #, 0 if not
	public double color; //is there a color you can collect? 1 yes, 0 no
	
	public void setAvailabiity(double a){
		available=a;
	}
	
	public void setAgent(double a){
		agent=a;
	}
	
	public void setColor(double a){
		color=a;
	}
	
	public double getAvailability(){
		return available;
	}
	
	public double getAgent(){
		return agent;
	}
	
	public double getColor(){
		return color;
	}
}
