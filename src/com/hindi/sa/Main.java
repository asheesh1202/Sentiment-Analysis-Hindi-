package com.hindi.sa;

import java.util.ArrayList;

import com.hindi.ssfreader.SSFReader;
import com.hindi.ssfreader.Sentence;

public class Main 
{ 
	static String path, posParsedPath, negParsedPath;
	static SSFReader ssfReaderPosObj, ssfReaderNegObj;
	static public stopWordHandler stopWordHandlerObj;
	static ArrayList<Sentence> posSSFSentences, negSSFSentences;
	static UnigramCreator unigramCreatorObj;
	
	public static void main(String args[])
	{
		path = args[0];
		posParsedPath = path + "/ParsedReviews/PositiveParsed.txt";
		negParsedPath = path + "/ParsedReviews/NegativeParsed.txt";
		stopWordHandlerObj = new stopWordHandler();
		ssfReaderPosObj = new SSFReader();
		ssfReaderNegObj = new SSFReader();
		posSSFSentences = new ArrayList<Sentence>();
		negSSFSentences = new ArrayList<Sentence>();
		
		stopWordHandlerObj.createStopWordSet(path);
		posSSFSentences = ssfReaderPosObj.parseFile(posParsedPath, true);
		negSSFSentences = ssfReaderNegObj.parseFile(negParsedPath, true);
		unigramCreatorObj.unigramMapCreator(posSSFSentences);
		
	}
	
}
