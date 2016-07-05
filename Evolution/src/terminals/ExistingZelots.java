package terminals;

import problem.StarCraftBuildOrderProblem;
import ec.*;
import ec.gp.*;
import ec.util.*;
import gPDataTypes.IntData;

public class ExistingZelots extends GPNode
{
public String toString() { return "ExistingZelot"; }

public int expectedChildren() { return 0; }

public void eval(final EvolutionState state,
                 final int thread,
                 final GPData input,
                 final ADFStack stack,
                 final GPIndividual individual,
                 final Problem problem)
    {
	IntData rd = ((IntData)(input));
    rd.x = ((StarCraftBuildOrderProblem)problem).existingZelots;
    }
}
