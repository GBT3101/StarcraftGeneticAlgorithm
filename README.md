# StarcraftGeneticAlgorithm
Using genetic algorithms Starcraft learns the best build order it can use. (Protoss)

**Architecture:**
![image](https://www.dropbox.com/s/ph7dpsmhb5mhu6a/SCarc.png?dl=0)

**Flow Overview:**
![image](https://www.dropbox.com/s/pxxwkhbh5twtmzk/SCoverview.PNG?dl=0)

**You can enjoy a demonstration video here:** (press to watch)

[![screenshot](https://www.dropbox.com/s/1crqf05v35lbnz3/SCvid.PNG?dl=0)](https://www.dropbox.com/s/gre2j2zk6j1hvn4/SC.flv?dl=0)

**Minimal Requirements to run:**

	•	CPU:		Intel CPU Pentium III.

	•	GPU:		Geforce 6200 LE / Radeon Xpress 1200 Series.

	•	RAM:		8GB

	•	HDD Space: 	64GB

**Installs you have to do before you start:**

	-	JAVA JDK 8th edition – in both main PC and the Virtual Machines.
	
	-	Oracle Virtual Box (and open 2 Virtual Machines).
	
	-	Windows 7 (or above) for both Virtual Machines.
	
	-	Starcraft: Brood War version 1.16.1 in both Virtual Machines.
	
	-	BWAPI version 4.1.2, download here - https://github.com/bwapi/bwapi/releases/tag/v4.1.2
	
	-	BWTA version 2.2, instructions here - https://bitbucket.org/auriarte/bwta2/downloads
	
	-	Tournament Manager, instructions to install are here - http://webdocs.cs.ualberta.ca/~cdavid/starcraftaicomp/tm.shtml
	
	Important – compile the source code and check that you have both server.jar and client.jar
	Put the client folder inside both Virtual Machines in the StarcraftAI/tournamentManager folder.

**How to install:**
Main PC:

	•	Organize your directory library like that:
	
	o	C:/StarcraftAI/
	
		BWAPI 

		BWTAlib_2.2

		TournamentManager

	•	Set these environment variables:

	o	BWAPI_DIR = “C:\StarcraftAI\BWAPI”

	o	BWTA_DIR = “C:\starcraftAI\BWTAlib_2.2”

	o	Add to your path:

		“; C:\starcraftAI\TournamentManager\server”

	•	Server Settings:

	o	Unzip bots.zip into the server\bots directory.

	o	Replace server.jar with the one we provided, since we had to make some changes to the way the server works.

	o	Replace Server_settings.ini with the one we provided.

	•	Edit Properties.ini

	o	Player_one_ip – enter the ip of the #1 Virtual Machine.

	o	Player_two_ip – enter the ip of the #2 Virtual Machine.

	o	Player_one_username – enter the username of the #1 Virtual Machine.

	o	Player_two_username – enter the username of the #2 Virtual Machine.

	o	Player_one_password – enter the windows password of the #1 Virtual Machine.

	o	Player_two_password – enter the windows password of the #2 Virtual Machine.

Virtual Machines:

	•	Install StarCraft: Brood War version 1.16.1, installation path should be C:\Starcraft\

	•	Run BWAPI/ChaosLauncher/chaoslauncher.exe, in the settings tab change “installpath” to “C:\Starcraft\”.

	•	Put “libgmp-10.dll” and “libmpfr-4.dll” in your windows folder, you will find these DLL files in your BWTAlib2.2/windows folder.

	•	Edit C:\StarcraftAI\TournamentManager\Client\Client_Settings.ini:

	o	Server Address (line 33) – type your Main PC IP address.

	•	Install BitVise Server – instructions here - https://www.bitvise.com/ssh-server-download

**How To Run:**

After all the installations and configurations have been made, open up both of the virtual machines and make sure BitVise server is up and running.
Now open command prompt as an administrator, and in the main directory execute this command:
java –jar evolution.jar -file "%Project_Path%/src/starcract.params"



**Have Fun!**

![image](https://photos-3.dropbox.com/t/2/AAD4PQF9I7UH1-OoJRGmS0rHfpKAG_nxgp2H_X9eSk7N6w/12/96757029/jpeg/32x32/1/_/1/2/SCdemo.jpg)
