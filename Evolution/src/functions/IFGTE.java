package functions;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import gPDataTypes.IntData;

public class IFGTE extends GPNode{
	public String toString() { return "ifgte"; }

	public int expectedChildren() { return 4; }

	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	{
		int firstValue;
		int secondValue;
		IntData rd = ((IntData)(input));

		children[0].eval(state,thread,input,stack,individual,problem);
		firstValue = rd.x;

		children[1].eval(state,thread,input,stack,individual,problem);
		secondValue = rd.x;
		if (firstValue>=secondValue){
			children[3].eval(state,thread,input,stack,individual,problem);	
		}
		else{
			children[4].eval(state,thread,input,stack,individual,problem);
		}
	}
}
