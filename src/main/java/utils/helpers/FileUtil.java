package utils.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.cucumber.java.Scenario;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class FileUtil {

    public List<String> getFileUnderDirectory(String filePaths) {
        List<String> results = new ArrayList<>();

        try {

            try (Stream<Path> filePathStream = Files.walk(Paths.get(filePaths))) {
                filePathStream.forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {
                        results.add(filePath.getFileName().toString());
                    }
                });
            }
        } catch (Exception e) {
            return null;
        }
        return results;
    }

    public void createDirectoryAtProjectPath(String dirName, File sourceScreenShot , Scenario scenario) throws IOException {
        File directory = new File(System.getProperty("user.dir")+"/"+dirName+"/");
        if(!directory.exists())
        {
            directory.mkdir();
        }
        Date d = new Date();
        String FileName = d.toString().replace(":", "_").replace(" ", "_") + ".png";
        FileHandler.copy(sourceScreenShot, new File(directory +"/"+ scenario.getName().replace(" ","_")+FileName));

    }


    public void createDirectoryAtProjectPath(String dirName) {
        File directory = new File(System.getProperty("user.dir") + "/" + dirName);
        if (!directory.exists())
            directory.mkdir();
    }

}
