package com.hindi.sa;

import java.util.ArrayList;

import com.hindi.ssfreader.SSFReader;
import com.hindi.ssfreader.Sentence;

public class Main 
{ 
	static String path, testPosParsedPath, testNegParsedPath, trainPosParsedPath, trainNegParsedPath, polarityScoreFilePath;
	static SSFReader ssfReaderPosObj, ssfReaderNegObj;
	static public StopWordHandler stopWordHandlerObj;
	static ArrayList<Sentence> testPosSSFSentences, testNegSSFSentences, trainPosSSFSentences, trainNegSSFSentences;
	//static UnigramCreator unigramCreatorObj;
	static PolarityScore polarityScoreObj;
	static NGramMapCreator ngramCreatorObj;
	static PolarityDecider polarityDeciderObj;
	
	public static void main(String args[])
	{
		path = args[0];
		trainPosParsedPath = path + "/ParsedReviews/Train/PositiveParsed.txt";
		trainNegParsedPath = path + "/ParsedReviews/Train/NegativeParsed.txt";
		testPosParsedPath = path + "/ParsedReviews/Test/PositiveParsed.txt";
		testNegParsedPath = path + "/ParsedReviews/Test/NegativeParsed.txt";
		polarityScoreFilePath = path + "/Polarity Scores";
		
		stopWordHandlerObj = new StopWordHandler();
		ssfReaderPosObj = new SSFReader();
		ssfReaderNegObj = new SSFReader();
		trainPosSSFSentences = new ArrayList<Sentence>();
		trainNegSSFSentences = new ArrayList<Sentence>();
		testPosSSFSentences = new ArrayList<Sentence>();
		testNegSSFSentences = new ArrayList<Sentence>();
		ngramCreatorObj = new NGramMapCreator();
		polarityDeciderObj = new PolarityDecider();
		polarityScoreObj = new PolarityScore();
		
		
		stopWordHandlerObj.createStopWordSet(path);
		polarityScoreObj.polarityScoreMapCreator(polarityScoreFilePath);
		trainPosSSFSentences = ssfReaderPosObj.parseFile(trainPosParsedPath, true);
		trainNegSSFSentences = ssfReaderNegObj.parseFile(trainNegParsedPath, true);
		testPosSSFSentences = ssfReaderPosObj.parseFile(testPosParsedPath, true);
		testNegSSFSentences = ssfReaderNegObj.parseFile(testNegParsedPath, true);
		
		
		ngramCreatorObj.setStopWordSet(stopWordHandlerObj.stopWordSet);
		ngramCreatorObj.createNGramMap(path, trainPosSSFSentences, 1, 0);
		ngramCreatorObj.createNGramMap(path, trainNegSSFSentences, 1, 1);
		ngramCreatorObj.storeProbabilities(path, trainPosSSFSentences, trainNegSSFSentences, 1);
		
		polarityDeciderObj.setStopWordSet(stopWordHandlerObj.stopWordSet);
		polarityDeciderObj.mapPopulator(path);
		polarityDeciderObj.polarityDecider(path, testPosSSFSentences, 1, 0);
		polarityDeciderObj.polarityDecider(path, testNegSSFSentences, 1, 1);
		
		//polarityDeciderObj.polarityDecider(path, stopWordHandlerObj.stopWordSet);
		
		//unigramCreatorObj=new UnigramCreator();
		
		
		
		/*
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
		*/
		
		
		
		
		/*TreeMap<String,FeatureValue> featuresMap=new TreeMap<String, FeatureValue>();
		TreeMap<String,FeatureValue> tempMap=new TreeMap<String, FeatureValue>();
		tempMap=unigramCreatorObj.unigramMapCreator(posSSFSentences);
		featuresMap.putAll(tempMap);
		System.out.println("temp size:"+tempMap.size());
		
		tempMap=unigramCreatorObj.unigramMapCreator(negSSFSentences);
		System.out.println("temp size:"+tempMap.size());
		featuresMap.putAll(tempMap);
		System.out.println("featuremap size:"+featuresMap.size());
		tempMap.clear();
		*/
		/*System.out.println("printing features:");
		for(Entry e:featuresMap.entrySet())
		{
			System.out.println(e.getKey()+":"+e.getValue());
		}*/
	}
	
}
