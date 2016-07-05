package evaluator;

import ec.Evaluator;
import ec.EvolutionState;
import ec.Individual;
import ec.coevolve.GroupedProblemForm;
import ec.util.Parameter;
import ec.util.SortComparator;

public class ThreeWayCompetitive extends Evaluator {
	private static final long serialVersionUID = 3623142199469369948L;
	
	public static final String P_GROUP_SIZE = "group-size";
    public int groupSize;

    public static final String P_OVER_EVAL = "over-eval";
    public boolean allowOverEvaluation;

    public void setup( final EvolutionState state, final Parameter base )
        {
	        super.setup( state, base );                      
	        groupSize = state.parameters.getInt( base.push( P_GROUP_SIZE ), null, 1 );
	        if( groupSize < 1 )
	        {
	                state.output.fatal( "Incorrect value for parameter", base.push( P_GROUP_SIZE ) );
	        }
	        allowOverEvaluation = state.parameters.getBoolean( base.push( P_OVER_EVAL ), null, false );
        }

    public boolean runComplete( final EvolutionState state )
        {
        return false;
        }

    public void randomizeOrder(final EvolutionState state, final Individual[] individuals)
        {
        // copy the inds into a new array, then dump them randomly into the
        // subpopulation again
        Individual[] queue = new Individual[individuals.length];
        int len = queue.length;
        System.arraycopy(individuals,0,queue,0,len);

        for(int x=len;x>0;x--)
            {
            int i = state.random[0].nextInt(x);
            individuals[x-1] = queue[i];
            // get rid of queue[i] by swapping the highest guy there and then
            // decreasing the highest value  :-)
            queue[i] = queue[x-1];
            }
        }

    /**
     * An evaluator that performs coevolutionary evaluation.  Like SimpleEvaluator,
     * it applies evolution pipelines, one per thread, to various subchunks of
     * a new population.
     */
    public void evaluatePopulation(final EvolutionState state)
        {
        int numinds[] = new int[state.evalthreads];
        int from[] = new int[state.evalthreads];
        boolean[] assessFitness = new boolean[state.population.subpops.length];
        for(int i = 0; i < assessFitness.length; i++)
            assessFitness[i] = true; // update everyone's fitness in preprocess and postprocess
        
        for (int y=0;y<state.evalthreads;y++)
            {
            // figure numinds
            if (y<state.evalthreads-1) // not last one
                numinds[y] = state.population.subpops[0].individuals.length/
                    state.evalthreads;
            else
                numinds[y] = 
                    state.population.subpops[0].individuals.length/
                    state.evalthreads +
                    
                    (state.population.subpops[0].individuals.length -
                        (state.population.subpops[0].individuals.length /
                        state.evalthreads)
                    *state.evalthreads);
            // figure from
            from[y] = (state.population.subpops[0].individuals.length/
                state.evalthreads) * y;
            }
        
        randomizeOrder( state, state.population.subpops[0].individuals );
        
        GroupedProblemForm prob = (GroupedProblemForm)(p_problem.clone());

        prob.preprocessPopulation(state,state.population, assessFitness, false);
                
        evalNRandomThreeWay( state, from, numinds, state.population.subpops[0].individuals, 0, prob );
    
        prob.postprocessPopulation(state, state.population, assessFitness, false);
        }
    
   
    public void evalNRandomThreeWay( final EvolutionState state,
        int[] from, int[] numinds,
        final Individual[] individuals, int subpop, 
        final GroupedProblemForm prob )
        {
        if (state.evalthreads==1){
            evalNRandomThreeWayPopChunk(state,from[0],numinds[0],0,individuals, subpop, prob);
        }
        else
            {
        	state.output.fatal("ThreeWay does not support more then one thread");
        }
    }
    
    public void evalNRandomThreeWayPopChunk( final EvolutionState state,
        int from, int numinds, int threadnum,
        final Individual[] individuals,
        final int subpop,
        final GroupedProblemForm prob)
        {

        // the number of games played for each player
        EncapsulatedIndividual[] individualsOrdered = new EncapsulatedIndividual[individuals.length];
        EncapsulatedIndividual[] queue = new EncapsulatedIndividual[individuals.length];
        for( int i = 0 ; i < individuals.length ; i++ )
            individualsOrdered[i] = new EncapsulatedIndividual( individuals[i], 0 );

        Individual[] competition = new Individual[3];
        int[] subpops = new int[] { subpop, subpop }; 
        boolean[] updates = new boolean[3];
        updates[0] = true;
        int upperBound = from+numinds;
        
        for(int x=from;x<upperBound;x++)
            {
            System.arraycopy(individualsOrdered,0,queue,0,queue.length);
            competition[0] = queue[x].ind;

            // if the rest of individuals is not enough to fill
            // all games remaining for the current individual
            // (meaning that the current individual has left a
            // lot of games to play versus players with index
            // greater than his own), then it should play with
            // all. In the end, we should check that he finished
            // all the games he needs. If he did, everything is
            // ok, otherwise he should play with some other players
            // with index smaller than his own, but all these games
            // will count only for his fitness evaluation, and
            // not for the opponents' (unless allowOverEvaluations is set to true)

            // if true, it means that he has to play against all opponents with greater index
            if( individuals.length - x - 1 <= groupSize - queue[x].nOpponentsMet )
                {
            	/*
                for( int y = x+1 ; y < queue.length ; y++ )
                    {
                    competition[1] = queue[y].ind;
                    updates[1] = (queue[y].nOpponentsMet < groupSize) || allowOverEvaluation;
                    prob.evaluate( state, competition, updates, false, subpops, 0 );
                    queue[x].nOpponentsMet++;
                    if( updates[1] )
                        queue[y].nOpponentsMet++;
                    }
                */
	                for( int y = x+1 ; y < queue.length-1 ; y = y+2 )
	                {
		                competition[1] = queue[y].ind;
		                competition[2] = queue[y+1].ind;
		                updates[1] = (queue[y].nOpponentsMet < groupSize) || allowOverEvaluation;
		                updates[2] = (queue[y+1].nOpponentsMet < groupSize) || allowOverEvaluation;
		                prob.evaluate( state, competition, updates, false, subpops, 0 );
		                queue[x].nOpponentsMet= Math.min(queue[x].nOpponentsMet+2, groupSize);
		                queue[y].nOpponentsMet= Math.min(queue[y].nOpponentsMet+2, groupSize);
		                queue[y+1].nOpponentsMet= Math.min(queue[y+1].nOpponentsMet+2, groupSize);
	                }
                }
                
            else // here he has to play against a selection of the opponents with greater index
                {
                // we can use the queue structure because we'll just rearrange the indexes
                // but we should make sure we also rearrange the other vectors referring to the individuals

                for( int y = 0 ; groupSize > queue[x].nOpponentsMet ; y= y+2 )
                    {
	                    // swap to the end and remove from list
	                    int index = state.random[0].nextInt( queue.length - x - 1 - y )+x+1;
	                    competition[1] = queue[index].ind;	                    
	                    EncapsulatedIndividual temp = queue[index];
	                    queue[index] = queue[queue.length - y - 1];
	                    queue[queue.length - y - 1] = temp;
	                    
	                    int index2 = state.random[0].nextInt( queue.length - x - 1 - y -1 )+x+1;
	                    competition[2] = queue[index2].ind;
	                    EncapsulatedIndividual temp2 = queue[index2];
	                    queue[index2] = queue[queue.length - y - 2];
	                    queue[queue.length - y - 2] = temp2;
	                    
	                    updates[1] = (queue[queue.length - y - 1].nOpponentsMet < groupSize) || allowOverEvaluation;
		                updates[2] = (queue[queue.length - y - 2].nOpponentsMet < groupSize) || allowOverEvaluation;
	
	                    prob.evaluate( state, competition, updates, false, subpops, 0 );
	                    queue[x].nOpponentsMet= Math.min(queue[x].nOpponentsMet+2, groupSize);
	                    queue[queue.length - y - 1].nOpponentsMet= Math.min(queue[queue.length - y - 1].nOpponentsMet+2, groupSize);
	                    queue[queue.length - y - 2].nOpponentsMet= Math.min(queue[queue.length - y - 2].nOpponentsMet+2, groupSize);
                    }

                }

            // if true, it means that the current player needs to play some games with other players with lower indexes.
            // this is an unfortunate situation, since all those players have already had their groupSize games for the evaluation
            if( queue[x].nOpponentsMet < groupSize )
                {
                for( int y = queue[x].nOpponentsMet ; y < groupSize ; y++ )
                    {
                    // select a random opponent with smaller index (don't even care for duplicates)
                    int index;
                    int index2;
                    if( x > 0 ){ // if x is 0, then there are no players with smaller index, therefore pick a random one
                        index = state.random[0].nextInt( x );
                    	index2= state.random[0].nextInt( x );
                    }
                    else{
                        index = state.random[0].nextInt( queue.length-1 )+1;
                        index2 = state.random[0].nextInt( queue.length-1 )+1;
                    }
                    // use the opponent for the evaluation
                    competition[1] = queue[index].ind;
                    competition[2] = queue[index2].ind;
	                updates[1] = (queue[index].nOpponentsMet < groupSize) || allowOverEvaluation;
	                updates[2] = (queue[index2].nOpponentsMet < groupSize) || allowOverEvaluation;
                    prob.evaluate( state, competition, updates, false, subpops, 0 );
                    queue[x].nOpponentsMet++;
	                queue[x].nOpponentsMet= Math.min(queue[x].nOpponentsMet+2, groupSize);
	                queue[index].nOpponentsMet= Math.min(queue[index].nOpponentsMet+2, groupSize);
	                queue[index2].nOpponentsMet= Math.min(queue[index2].nOpponentsMet+2, groupSize);
                }
               }

            }
        }

    int nextPowerOfTwo( int N )
        {
        int i = 1;
        while( i < N )
            i *= 2;
        return i;
        }

    int whereToPutInformation;
    void fillPositions( int[] positions, int who, int totalPerDepth, int total )
        {
        if(totalPerDepth >= total - 1 )
            {
            positions[whereToPutInformation] = who;
            whereToPutInformation++;
            }
        else
            {
            fillPositions( positions, who, totalPerDepth*2+1, total );
            fillPositions( positions, totalPerDepth-who, totalPerDepth*2+1, total );
            }
        }

    }

// used by the K-Random-Opponents-One-Way and K-Random-Opponents-Two-Ways evaluations
class EncapsulatedIndividual
    {
    public Individual ind;
    public int nOpponentsMet;
    public EncapsulatedIndividual( Individual ind_, int value_ )
        {
        ind = ind_;
        nOpponentsMet = value_;
        }
    };

/*
// used by the Single-Elimination-Tournament, (Double-Elimination-Tournament and World-Cup) evaluations
class IndividualAndVictories
{
public Individual ind;
public int victories;
public IndividualAndVictories( Individual ind_, int value_ )
{
ind = ind_;
victories = value_;
}
};
*/

class IndComparator implements SortComparator
    {
    public boolean lt(Object a, Object b)
        { return ((Individual)a).fitness.betterThan(((Individual)b).fitness); }
    public boolean gt(Object a, Object b)
        { return ((Individual)b).fitness.betterThan(((Individual)a).fitness); }
    }