package io.github.aquerr.json2xml;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class json2xml
{
    public static final Path INPUT_DIR_PATH = Paths.get(new File("").getAbsolutePath()).resolve("input");
    public static final Path OUTPUT_DIR_PATH = Paths.get(new File("").getAbsolutePath()).resolve("output");

    public static void main(String[] args)
    {
        try
        {
            if (!Files.exists(INPUT_DIR_PATH))
            {
                Files.createDirectory(INPUT_DIR_PATH);
            }

            if (!Files.exists(OUTPUT_DIR_PATH))
            {
                Files.createDirectory(OUTPUT_DIR_PATH);
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }

        File inputFolder = new File(INPUT_DIR_PATH.toUri());

        File[] inputFiles = inputFolder.listFiles();

        for (File inputFile : inputFiles)
        {
            try
            {
                if (inputFile.isFile())
                {
                    FileInputStream fis = new FileInputStream(inputFile);
                    byte[] data = new byte[(int) inputFile.length()];
                    fis.read(data);
                    fis.close();

                    String str = new String(data, "UTF-8");

                    ParserThroughMap parserThroughMap = new ParserThroughMap();
                    //ParserThroughString parserThroughString = new ParserThroughString();

                    JSONObject object = new JSONObject(str);

                    //String resultOne = parserThroughString.parseXML(str);

                    String result = parserThroughMap.parseXML(object);

                    File resultFile = new File(OUTPUT_DIR_PATH.resolve(stripExtension(inputFile.getName()).concat(".xml")).toUri());

                    FileWriter writer = new FileWriter(resultFile);

                    writer.write(result);

                    writer.close();
                }
            }
            catch (FileNotFoundException exception)
            {
                exception.printStackTrace();
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private static String stripExtension (String str) {
        // Handle null case specially.

        if (str == null) return null;

        // Get position of last '.'.

        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.

        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.

        return str.substring(0, pos);
    }
}
