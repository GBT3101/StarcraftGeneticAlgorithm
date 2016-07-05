package utils;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Utils {
	
	public static String getBuildOrderFromGenome(int[] genome){
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		List<String> units = new ArrayList<String>();
		List<String> results = new ArrayList<String>();
		results.add("Probe");
		results.add("Probe");
		results.add("Probe");
		results.add("Probe");
		results.add("Pylon");
		
		for(int x=0; x<genome.length; x++){
			units.add(Defs.units[Math.abs(genome[x])%Defs.units.length]);
		}
		for (int i = 0; i< units.size();i++){
			List<String> dependencies = getDependencies(units.get(i));
			Collections.reverse(dependencies);
			for (int j = 0; j< dependencies.size();j++){
				if (map.get(dependencies.get(j)) == null){
					results.add(dependencies.get(j));
					map.put(dependencies.get(j), 1);
				}
				if ((dependencies.get(j).equals("Dark_Templar") || dependencies.get(j).equals("High_Templar")) && map.get(dependencies.get(j)) < 2){
					results.add(dependencies.get(j));
					map.put(dependencies.get(j), 2);
				}
			}
			results.add(units.get(i));
			Integer currentValue = map.get(units.get(i));
			if (currentValue == null){
				map.put(units.get(i), 1);
			}
			else{
				map.put(units.get(i), currentValue+1);
			}
		}
		return putInFormat(results);
		
		
	}
	private static List<String> getDependencies(String currentUnit) {
		return getDependencies(currentUnit,new ArrayList<String>());
	}

	private static List<String> getDependencies(String currentUnit,List<String> dependencies) {
		switch (currentUnit){
		case "Zealot":
			dependencies.add("Gateway");
			getDependencies("Gateway",dependencies);
			break;
		case "Templar_Archives":
			dependencies.add("Citadel_of_Adun");
			getDependencies("Citadel_of_Adun",dependencies);
			break;
		case "High_Templar":
			dependencies.add("Templar_Archives");
			getDependencies("Templar_Archives",dependencies);
			break;
		case "Citadel_of_Adun":
			dependencies.add("Cybernetics_Core");
			getDependencies("Cybernetics_Core",dependencies);
			break;
		case "Dark_Templar":
			dependencies.add("Templar_Archives");
			getDependencies("Citadel_of_Adun",dependencies);
			break;
		case "Archon":
			dependencies.add("High_Templar");
			dependencies.add("High_Templar");
			getDependencies("High_Templar",dependencies);
			getDependencies("High_Templar",dependencies);
			break;

		case "Dark_Archon":
			dependencies.add("Dark_Templar");
			dependencies.add("Dark_Templar");
			getDependencies("Dark_Templar",dependencies);
			getDependencies("Dark_Templar",dependencies);
			break;

		case "Cybernetics_Core":
			dependencies.add("Gateway");
			dependencies.add("Assimilator");
			getDependencies("Gateway",dependencies);
			break;
		case "Dragoon":
			dependencies.add("Cybernetics_Core");
			getDependencies("Cybernetics_Core",dependencies);
			break;
		case "Robotics Facility":
			dependencies.add("Cybernetics_Core");
			dependencies.add("Assimilator");
			getDependencies("Cybernetics_Core",dependencies);
			break;
		case "Shuttle":
			dependencies.add("Robotics Facility");
			getDependencies("Robotics Facility",dependencies);
			break;
		case "Observatory":
			dependencies.add("Robotics Facility");
			getDependencies("Robotics Facility",dependencies);
			break;
		case "Observer":
			dependencies.add("Observatory");
			getDependencies("Observatory",dependencies);
			break;
		case "Robotics_Support_Bay":
			dependencies.add("Robotics Facility");
			getDependencies("Robotics Facility",dependencies);
			break;
		case "Reaver":
			dependencies.add("Robotics_Support_Bay");
			getDependencies("Robotics_Support_Bay",dependencies);
			break;
		case "Scarab":
			dependencies.add("Reaver");
			getDependencies("Reaver",dependencies);
			break;
		case "Stargate":
			dependencies.add("Cybernetics_Core");
			getDependencies("Cybernetics_Core",dependencies);
			break;
		case "Scout":
			dependencies.add("Stargate");
			getDependencies("Stargate",dependencies);
			break;
		case "Corsair":
			dependencies.add("Stargate");
			getDependencies("Stargate",dependencies);
			break;
		case "Arbiter_Tribunal":
			dependencies.add("Stargate");
			dependencies.add("Templar_Archives");
			getDependencies("Stargate",dependencies);
			getDependencies("Templar_Archives",dependencies);
			break;
		case "Arbiter":
			dependencies.add("Arbiter_Tribunal");
			getDependencies("Arbiter_Tribunal",dependencies);
			break;
		case "Fleet_Beacon":
			dependencies.add("Stargate");
			getDependencies("Stargate",dependencies);
			break;
		case "Carrier":
			dependencies.add("Fleet_Beacon");
			getDependencies("Fleet_Beacon",dependencies);
			break;
		case "Interceptor":
			dependencies.add("Carrier");
			getDependencies("Carrier",dependencies);
			break;
			/*
		case "Probe":
			dependencies.add("Nexus");
			getDependencies("Nexus",dependencies);
			break;
			*/
		case "Shield_Battery":
			dependencies.add("Gateway");
			getDependencies("Gateway",dependencies);
			break;
			
		case "Photon_Cannon":
			dependencies.add("Forge");
			getDependencies("Forge",dependencies);
			break;			
			
		}
		
		return dependencies;
		
	}

	private static String putInFormat(List<String> list){
		StringBuilder sb = new StringBuilder();
		for(int x=0; x<list.size(); x++){
			sb.append("\"");
			sb.append(list.get(x));
			sb.append("\"");
			if (x != list.size()-1){
				sb.append(", ");
			}
		}
		return sb.toString();
	}
}

	
	/*
	BuildOrder::getDependencies(BWAPI::UnitTypes currentUnit, std::vector<BWAPI::UnitTypes> &dependecies){
		switch (currentUnit){
		case BWAPI::UnitTypes::Protoss_Zealot:
			dependencies.add("Gateway);
			getDependencies(BWAPI::UnitTypes::Protoss_Gateway, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Templar_Archives:
			dependencies.add("Gateway);
			getDependencies(BWAPI::UnitTypes::Protoss_Gateway, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_High_Templar:
			dependencies.add("Templar_Archives);
			getDependencies(BWAPI::UnitTypes::Protoss_Templar_Archives, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Citadel_of_Adun:
			dependencies.add("Templar_Archives);
			getDependencies(BWAPI::UnitTypes::Protoss_Templar_Archives, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Dark_Templar:
			dependencies.add("Citadel_of_Adun);
			getDependencies(BWAPI::UnitTypes::Protoss_Citadel_of_Adun,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Archon:
			dependencies.add("High_Templar);
			dependencies.add("High_Templar);
			getDependencies(BWAPI::UnitTypes::Protoss_High_Templar,dependencies);
			getDependencies(BWAPI::UnitTypes::Protoss_High_Templar,dependencies);
			break;

		case BWAPI::UnitTypes::Protoss_Dark_Archon:
			dependencies.add("Dark_Templar);
			dependencies.add("Dark_Templar);
			getDependencies(BWAPI::UnitTypes::Protoss_Dark_Templar,dependencies);
			getDependencies(BWAPI::UnitTypes::Protoss_Dark_Templar,dependencies);
			break;

		case BWAPI::UnitTypes::Protoss_Cybernetics_Core:
			dependencies.add("Gateway);
			getDependencies(BWAPI::UnitTypes::Protoss_Gateway, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Dragoon:
			dependencies.add("Cybernetics_Core);
			getDependencies(BWAPI::UnitTypes::Protoss_Cybernetics_Core, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Robotics Facility:
			dependencies.add("Cybernetics_Core);
			getDependencies(BWAPI::UnitTypes::Protoss_Cybernetics_Core, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Shuttle:
			dependencies.add("Robotics Facility);
			getDependencies(BWAPI::UnitTypes::Protoss_Robotics Facility, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Observatory:
			dependencies.add("Robotics Facility);
			getDependencies(BWAPI::UnitTypes::Protoss_Robotics Facility, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Observer:
			dependencies.add("Observatory);
			getDependencies(BWAPI::UnitTypes::Protoss_Observatory, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Robotics_Support_Bay:
			dependencies.add("Robotics Facility);
			getDependencies(BWAPI::UnitTypes::Protoss_Robotics Facility, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Reaver:
			dependencies.add("Robotics_Support_Bay);
			getDependencies(BWAPI::UnitTypes::Protoss_Robotics_Support_Bay, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Scarab:
			dependencies.add("Reaver);
			getDependencies(BWAPI::UnitTypes::Protoss_Reaver, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Stargate:
			dependencies.add("Cybernetics_Core);
			getDependencies(BWAPI::UnitTypes::Protoss_Cybernetics_Core, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Scout:
			dependencies.add("Stargate);
			getDependencies(BWAPI::UnitTypes::Protoss_Stargate, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Corsair:
			dependencies.add("Stargate);
			getDependencies(BWAPI::UnitTypes::Protoss_Stargate, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Arbiter_Tribunal:
			dependencies.add("Stargate);
			getDependencies(BWAPI::UnitTypes::Protoss_Stargate, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Arbiter:
			dependencies.add("Arbiter_Tribunal);
			getDependencies(BWAPI::UnitTypes::Protoss_Arbiter_Tribunal, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Fleet_Beacon:
			dependencies.add("Arbiter_Tribunal);
			getDependencies(BWAPI::UnitTypes::Protoss_Arbiter_Tribunal, dependecies,dependencies);
			break;
		case BWAPI::UnitTypes::Protoss_Carrier:
			dependencies.add("Fleet_Beacon);
			getDependencies(BWAPI::UnitTypes::Protoss_Fleet_Beacon, dependecies,dependencies);
			break;
		}

*/
	


