package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import de.unihd.dbs.heideltime.standalone.DocumentType;
import de.unihd.dbs.heideltime.standalone.HeidelTimeStandalone;
import de.unihd.dbs.heideltime.standalone.OutputType;
import de.unihd.dbs.heideltime.standalone.POSTagger;
import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import de.unihd.dbs.uima.annotator.heideltime.resources.Language;

public class annotateTime {
	
	
	
	// load conll style file and annotate with heidelTime
	public static void loadFileAndAnnotate(String inputPath, String outputPath, HeidelTimeStandalone time) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(inputPath));
		String line = "";
		String sentence = "";
		String output = "";
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() == 0){
				output = makeSentence(time, sentence);
				System.out.println(output);
				sentence = "";
				Scanner sc = new Scanner(System.in);
				char c = sc.next().charAt(0);
			}
			else {
				String[] li = null;
				li = line.split("\t");
				sentence += li[2] + " ";
			}
		}
		reader.close();
	}
	
	
	public static String makeSentence(HeidelTimeStandalone time, String sentence) {
		String output = "";
		System.out.println(sentence);
		output = heidelAnnotate(time, sentence);
		String annotation = output.substring(output.indexOf("<TimeML>") + ("<TimeML>").length(), output.indexOf("</TimeML>"));
		return output;
	}
	
	
	public static String heidelAnnotate(HeidelTimeStandalone time, String sentence) {
		String output = "";
		try {
			output = time.process(sentence);
		} catch (DocumentCreationTimeMissingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String CONFIG_PATH = "****.props";
		
		HeidelTimeStandalone time= new HeidelTimeStandalone(Language.ENGLISH,
				 DocumentType.NARRATIVES,
				 OutputType.TIMEML,
				 CONFIG_PATH,
				 POSTagger.TREETAGGER, true);
		
		String inputPath = "****/train.conll";
		String outputPath = "";
		loadFileAndAnnotate(inputPath, outputPath, time);
		String result = "";
		
	}

}
