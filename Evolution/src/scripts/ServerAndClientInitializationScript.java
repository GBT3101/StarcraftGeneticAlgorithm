package scripts;


import com.jcraft.jsch.*;

import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.*;

public class ServerAndClientInitializationScript {

    String serverPath;
    private String _myHostOne   ;
    private String _myHostTwo;
    private String _userOne;
    private String _userTwo;
    private String _passwordOne;
    private String _passwordTwo;

    public ServerAndClientInitializationScript(String s) throws IOException {
        serverPath = s;
        Properties props = new Properties();
        //InputStream inputStream = getClass().getClassLoader().getResourceAsStream("properties.ini");
        InputStream inputStream = new ReaderInputStream(new FileReader(new File("properties.ini")));
        if (inputStream != null){
            props.load(inputStream);
        }
        _myHostOne = props.getProperty("player_one_ip");
        _myHostTwo = props.getProperty("player_two_ip");
        _userOne = props.getProperty("player_one_username");
        _userTwo = props.getProperty("player_two_username");
        _passwordOne = props.getProperty("player_one_password");
        _passwordTwo = props.getProperty("player_two_password");
        
        

    }


    public void runScript (List<String> botnames) throws IOException, JSchException {

        String gamesTXTPath;
        String resultsTXTPath;
        //String htmlTXTPath;
        //String serverSettingsTXTPath;


        //delete old un-needed files
        gamesTXTPath = serverPath + "games.txt";
        resultsTXTPath = serverPath + "results.txt";
        List<String> pathsToDelete = new ArrayList<>();
        pathsToDelete.add(gamesTXTPath);
        pathsToDelete.add(resultsTXTPath);
        FilesUtils.deleteFiles(pathsToDelete);

        //copy old html dictionary
        //htmlTXTPath = serverPath + "html";
        //FilesUtils.copyFolder(serverPath, htmlTXTPath);

        //serverSettingsTXTPath = serverPath + "server_settings.ini";
        //editServerSettings(serverSettingsTXTPath,botnames);

        //create games.txt

        File gamesTxt = new File(gamesTXTPath);
        fillGamesTXT(gamesTxt, botnames);
        

        //run server script
        Runtime.getRuntime().exec("cmd /c start run_server_script.bat");
        System.out.println(" Awaiting 10 seconds for the server to load");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //connect to clients

        String hostOne = _myHostOne;
        String hostTwo = _myHostTwo;
        String userOne = _userOne;
        String userTwo = _userTwo;
        String passwordOne = _passwordOne;
        String passwordTwo = _passwordTwo;
        String commandOne = "PsExec.exe \\\\" + hostOne + " -i" + " -u " + userOne + " -p " + passwordOne + " cmd /c (cd C:\\starcraftAI\\TournamentManager\\client ^& run_client.bat)";
        String commandTwo = "PsExec.exe \\\\" + hostTwo + " -i" + " -u " + userTwo + " -p " + passwordTwo + " cmd /c (cd C:\\starcraftAI\\TournamentManager\\client ^& run_client.bat)";
        Process procOne = Runtime.getRuntime().exec("cmd /c start /wait " + commandOne);
        Process procTwo = Runtime.getRuntime().exec("cmd /c start /wait " + commandTwo);
        try {
            procOne.waitFor();
            procTwo.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
 
    private void fillGamesTXT(File gamesTxt, List<String> botNames) throws IOException {
        gamesTxt.createNewFile();
        List<Pair<String,String>> combs = new ArrayList<>();
        int p = botNames.size(); // Get the endpoint

        for(int i =0; i<p; i++){
            for(int j=i+1; j<p; j++) {
                combs.add(new MutablePair<>(botNames.get(i), botNames.get(j)));
            }
        }


        String space = " ";
        StringBuilder sb = new StringBuilder();

        for (int i = 0 ; i < combs.size() ; i++){
            sb.append(i).append(space).append("0").append(space).append(combs.get(i).getKey()).append(space).append(combs.get(i).getValue())
                    .append(space).append("(2)Benzene.scx").append("\n");
        }
        for (int i = 0 ; i < combs.size() ; i++){
            sb.append(i + 3).append(space).append("1").append(space).append(combs.get(i).getKey()).append(space).append(combs.get(i).getValue())
                    .append(space).append("(2)Destination.scx").append("\n");
        }
        FilesUtils.writeToFile(sb.toString(),gamesTxt);
    }

    private void editServerSettings(String serverSettingsTXTPath, List<String> botNames) throws IOException {

        String textToFind = "# 2015 AIIDE Competition Bot Settings";
        File file = new File(serverSettingsTXTPath);
        if (file.exists()){
            FileReader fis = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fis);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null){

                if (line.equals(textToFind)){
                    sb.append(line).append("\n");
                    sb.append(bufferedReader.readLine()).append("\n");;
                    addBotsToConfig(sb,botNames);
                    runTextIndex(sb,bufferedReader);// throw away all other string untill you see delimiter
                }
                else{
                    sb.append(line).append("\n");
                }

            }
            file.delete();
            file.createNewFile();
            FilesUtils.writeToFile(sb.toString(),file);
            bufferedReader.close();

        }

    }

    private void runTextIndex(StringBuilder sb, BufferedReader bufferedReader) throws IOException {

        String line;
        while(!(line = bufferedReader.readLine()).equals("#################################################################"));

        sb.append(line).append("\n");

    }

    private void addBotsToConfig(StringBuilder sb, List<String> botNames) {

        for (String botName : botNames){
            sb.append("Bot").append(" ").append(botName).append(" ").append("Protoss").append(" ").append("dll").append(" ").append("BWAPI_412").append(" ").append("\n");
        }
    }
}
