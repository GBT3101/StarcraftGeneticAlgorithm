package problem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ResultsAnalyzer.Reader;
import ResultsAnalyzer.Results;

import com.jcraft.jsch.JSchException;

import scripts.ServerAndClientInitializationScript;
import utils.Defs;
import utils.Utils;
import ec.*;
import ec.simple.*;
import ec.util.Parameter;
import ec.vector.*;
import ec.coevolve.*;
public class StarCraftBuildOrderProblem extends Problem implements GroupedProblemForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7129330144417951274L;
	public int existingZelots;
	public static final String P_DATA = "data";

	public void setup(final EvolutionState state,
			final Parameter base)
	{
		// very important, remember this
		super.setup(state,base);	
	}

	public void preprocessPopulation(EvolutionState state, Population pop,
			boolean[] prepareForAssessment, boolean countVictoriesOnly) {
		for(int i = 0; i < pop.subpops.length; i++)
			if (prepareForAssessment[i]){
				for(int j = 0; j < pop.subpops[i].individuals.length; j++) {
					SimpleFitness fit = (SimpleFitness)(pop.subpops[i].individuals[j].fitness);
					fit.trials = new ArrayList<Double>();
				}
			}
	}
	public void evaluate(EvolutionState state, Individual[] ind, boolean[] updateFitness,
			boolean countVictoriesOnly, int[] subpops, int threadnum) {
		int[] genome1 = ((IntegerVectorIndividual)ind[0]).genome;
		int[] genome2 = ((IntegerVectorIndividual)ind[1]).genome;
		int[] genome3 = ((IntegerVectorIndividual)ind[2]).genome;
		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		String Strategy1 = "Protoss_P1";
		String Strategy2 = "Protoss_P2";
		String Strategy3 = "Protoss_P3";
		sb1.append("\"" + Strategy1
				+ "\" : { \"Race\" : \"Protoss\", \"OpeningBuildOrder\": ["+Utils.getBuildOrderFromGenome(genome1)+"]},");
		sb2.append("\"" + Strategy2
				+ "\" : { \"Race\" : \"Protoss\", \"OpeningBuildOrder\": ["+Utils.getBuildOrderFromGenome(genome2)+"]},");
		sb3.append("\"" + Strategy3
				+ "\" : { \"Race\" : \"Protoss\", \"OpeningBuildOrder\": ["+Utils.getBuildOrderFromGenome(genome3)+"]},");
		SimpleFitness fit1 = (SimpleFitness)(ind[0].fitness);
		SimpleFitness fit2 = (SimpleFitness)(ind[1].fitness);
		SimpleFitness fit3 = (SimpleFitness)(ind[2].fitness);
		String content;
		try {
			content = new Scanner(new File("template.txt")).useDelimiter("\\Z").next();
			String file1Content = content.replaceAll("REPLACE1", Strategy1).replaceAll("REPLACE2", sb1.toString());
			String file2Content = content.replaceAll("REPLACE1", Strategy2).replaceAll("REPLACE2", sb2.toString());
			String file3Content = content.replaceAll("REPLACE1", Strategy3).replaceAll("REPLACE2", sb3.toString());
			File file1 = new File(Defs.PATH_START + "/P1/"+Defs.PATH_END);
			File file2 = new File(Defs.PATH_START + "/P2/"+Defs.PATH_END);
			File file3 = new File(Defs.PATH_START + "/P3/"+Defs.PATH_END);
			FileWriter fileWriter = new FileWriter(file1);
			fileWriter.write(file1Content);
			fileWriter.flush();
			fileWriter.close();
			fileWriter = new FileWriter(file2);
			fileWriter.write(file2Content);
			fileWriter.flush();
			fileWriter.close();
			fileWriter = new FileWriter(file3);
			fileWriter.write(file3Content);
			fileWriter.flush();
			fileWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ServerAndClientInitializationScript script = new ServerAndClientInitializationScript(Defs.SERVER_PATH);
			List<String> list = new ArrayList<>();
	        list.add("P1");
	        list.add("P2");
	        list.add("P3");
	        script.runScript(list);
		} catch (IOException | JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Results res = null;
		
		try {
			res = Reader.readFile(Defs.SERVER_PATH+ "results.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		double p1Fitness = (double)res.getP1wins()*10000/(double)res.getP1time();
		double p2Fitness = (double)res.getP2wins()*10000/(double)res.getP2time();
		double p3Fitness = (double)res.getP3wins()*10000/(double)res.getP3time();
			
		if (updateFitness[0]) {
			fit1.trials.add(new Double(p1Fitness));
			// set the fitness in case we’re using Single Elimination Tournament
			fit1.setFitness(state, fit1.fitness() + p1Fitness, false);
		}
		if (updateFitness[1]) {
			fit2.trials.add(new Double(p2Fitness));
			// set the fitness in case we’re using Single Elimination Tournament
			fit2.setFitness(state, fit2.fitness() + p2Fitness, false);
		}
		if (updateFitness[2]) {
			fit3.trials.add(new Double(p3Fitness));
			// set the fitness in case we’re using Single Elimination Tournament
			fit3.setFitness(state, fit3.fitness() + p3Fitness, false);
		}
		
	}
	public void postprocessPopulation(EvolutionState state, Population pop,
			boolean[] assessFitness, boolean countVictoriesOnly) {
		for(int i = 0; i < pop.subpops.length; i++)
			if (assessFitness[i])
				for(int j = 0; j < pop.subpops[i].individuals.length; j++) {
					SimpleFitness fit = (SimpleFitness)(pop.subpops[i].individuals[j].fitness);
					// Let’s set the fitness to the average of the trials
					int len = fit.trials.size();
					double sum = 0;
					for(int l = 0; l < len; l++){
						sum += ((Double)(fit.trials.get(l))).doubleValue();
					}
					fit.setFitness(state, sum / len, false);
					pop.subpops[i].individuals[j].evaluated = true;
				}
	}

}