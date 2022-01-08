package tfip.modserver.addressbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Contacts {

    String dir;

    public Contacts(String dir){
        this.dir = dir;
    }

    public void createFile(String filename){
        Path pFileName = Path.of(dir+filename); //need to add the relative path inside
        try{
            Files.createFile(pFileName);
        }
        catch(IOException e){
            System.out.println(filename + " cannot be created");
        }
    }

    public void writeFile(String filename, List<String> userInfo){
        try{
            Path pFilename = Path.of(dir + filename);
            Files.write(pFilename,userInfo);
        }
        catch (IOException e) {
            System.out.println("cannot write to file " + filename);
        }
    }

    public List<String> readFile(String filename) throws IOException, FileNotFoundException{
        List<String> fileContent; 
        Path pFilename = Path.of(dir + filename);
        fileContent =  Files.readAllLines(pFilename);
        return fileContent;
        }
}
