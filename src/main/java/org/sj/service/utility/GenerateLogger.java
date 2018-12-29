package org.sj.service.utility;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class GenerateLogger 
{
	public void readF(String fType) throws IOException 
    {
		String oFile="E:\\Boot\\course_API\\log\\"+fType+".log";
		Stream<String> lines = Files.lines(Paths.get("E:\\Boot\\course_API\\log\\temp.log"));
        new GenerateLogger().parse(lines, oFile, fType);
    }
     
    private void parse(Stream<String> lines, String output,String oType) throws IOException 
    {
        final FileWriter fw = new FileWriter(output);
         
        //@formatter:off
        lines.filter(line -> line.contains(oType))
                .map(line -> line.split("                          "))
                    .map(arr -> arr[arr.length - 1])
                        .forEach(packageT -> writeToFile(fw, packageT));
        //@formatter:on
        fw.close();
        lines.close();
    }
 
    private void writeToFile(FileWriter fw, String packageT) 
    {
        try 
        {
            fw.write(String.format("%s%n", packageT));
        }
        catch (IOException e) 
        {
            throw new RuntimeException(e);
        }
    }
}
