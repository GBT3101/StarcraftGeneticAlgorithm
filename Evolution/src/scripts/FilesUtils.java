package scripts;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.*;
import java.util.List;

/**
 * Created by alexo on 17-Jun-16.
 */
public class FilesUtils {

    public static void deleteFiles(List<String> pathsToDelete) {

        for(String path : pathsToDelete){

            File file = new File(path);
            if (file.exists()){
                file.delete();
            }


        }

    }

    public static void copyFolder(String serverPath, String htmlTXTPath) throws IOException {
        File dir = new File(serverPath);
        FileFilter fileFilter = new WildcardFileFilter("html_*");
        File[] files = dir.listFiles(fileFilter);
        int maxFileNumber = getMaxFileNumber(files);
        File htmlDirFile = new File(htmlTXTPath);
        File copyHtmlDirFile = new File (htmlTXTPath + "_" + String.valueOf(maxFileNumber));
        copyHtmlDirFile.mkdir();
        FileUtils.copyDirectory(htmlDirFile, copyHtmlDirFile );

    }

    private static int getMaxFileNumber(File[] files) {
        int max = 0;
        if (files != null){
            for (File file : files){
                String filename = file.getName();
                int numbering = Integer.parseInt(filename.substring(filename.indexOf('_') + 1));
                if (numbering > max){
                    max = numbering;
                }
            }
        }
        max += 1;
        return max;
    }

    public static void writeToFile(String text, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(text);
        bufferedWriter.close();
    }

    public static String readText(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String message = bufferedReader.readLine();
        bufferedReader.close();
        return message;
    }
}
