package com.hindi.sa;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.hindi.ssfreader.SSFReader;
import com.hindi.ssfreader.Sentence;

public class Main 
{ 
	static String path, posParsedPath, negParsedPath, polarityScoreFilePath;
	static SSFReader ssfReaderPosObj, ssfReaderNegObj;
	static public StopWordHandler stopWordHandlerObj;
	static ArrayList<Sentence> posSSFSentences, negSSFSentences;
	static UnigramCreator unigramCreatorObj;
	static PolarityScore polarityScoreObj;
	
	public static void main(String args[])
	{
		path = args[0];
		posParsedPath = path + "/ParsedReviews/PositiveParsed.txt";
		negParsedPath = path + "/ParsedReviews/NegativeParsed.txt";
		polarityScoreFilePath = path + "/Polarity Scores";
		stopWordHandlerObj = new StopWordHandler();
		ssfReaderPosObj = new SSFReader();
		ssfReaderNegObj = new SSFReader();
		posSSFSentences = new ArrayList<Sentence>();
		negSSFSentences = new ArrayList<Sentence>();
		
		stopWordHandlerObj.createStopWordSet(path);
		polarityScoreObj=new PolarityScore();
		polarityScoreObj.polarityScoreMapCreator(polarityScoreFilePath);
		
		
		try {
			FileInputStream fin = new FileInputStream(polarityScoreFilePath
					+ "/Scores/map1.ser");
			ObjectInputStream in = new ObjectInputStream(fin);

			Map<String, Value> score1 = (HashMap<String, Value>) in.readObject();
			System.out.println("map1 scores deserialized");

			for (Entry<String, Value> entry : score1.entrySet()) {
				System.out.println("word: " + entry.getKey() + ","
						+ entry.getValue());

			}
			System.out.println();

			fin = new FileInputStream(polarityScoreFilePath
					+ "/Scores/map2.ser");
			in = new ObjectInputStream(fin);

			Map<String, Value> score2 = (HashMap<String, Value>) in.readObject();
			System.out.println("map2 scores deserialized");

			for (Entry<String, Value> entry : score2.entrySet()) {
				System.out.println("word: " + entry.getKey() + ","
						+ entry.getValue());

			}
			System.out.println();

			System.out.println("map1 size:" + score1.size());
			System.out.println("map2 size:" + score2.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		//posSSFSentences = ssfReaderPosObj.parseFile(posParsedPath, true);
		//negSSFSentences = ssfReaderNegObj.parseFile(negParsedPath, true);
		//unigramCreatorObj.unigramMapCreator(posSSFSentences);
		
	}
	
}
