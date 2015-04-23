package com.hindi.sa;

import com.hindi.sa.NGramMapCreator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.hindi.ssfreader.Phrase;
import com.hindi.ssfreader.Sentence;
import com.hindi.ssfreader.Token;


public class PolarityDecider 
{
	Set<String> stopWordSet;
	public TreeMap<String, ArrayList<Float>> UnigramProbMap = new TreeMap<String, ArrayList<Float>>();
	public TreeMap<String, ArrayList<Float>> BigramProbMap = new TreeMap<String, ArrayList<Float>>();
	public TreeMap<String, ArrayList<Float>> TrigramProbMap = new TreeMap<String, ArrayList<Float>>();
	
	public Set<String> getStopWordSet() {
		return stopWordSet;
	}

	public void setStopWordSet(Set<String> stopWordSet) {
		this.stopWordSet = stopWordSet;
	}

	
	void mapPopulator(String path)
	{
		try
		{
		FileInputStream fin=new FileInputStream(path+"/SERs/unigram_prob.ser");
		ObjectInputStream in=new ObjectInputStream(fin);
		
		UnigramProbMap=(TreeMap<String, ArrayList<Float>>)in.readObject();
		
		
		FileInputStream bi_fin=new FileInputStream(path+"/SERs/bigram_prob.ser");
		ObjectInputStream bi_in=new ObjectInputStream(bi_fin);
		
		BigramProbMap=(TreeMap<String, ArrayList<Float>>)bi_in.readObject();
		
		FileInputStream tri_fin=new FileInputStream(path+"/SERs/trigram_prob.ser");
		ObjectInputStream tri_in=new ObjectInputStream(tri_fin);
		
		TrigramProbMap=(TreeMap<String, ArrayList<Float>>)tri_in.readObject();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	String removeStopWords(String originalLine)
	{  
		String newLine = "";
		StringTokenizer tokenizer = new StringTokenizer(originalLine," ,#.:!?");
		String token;
		while(tokenizer.hasMoreTokens())
		{
			token = tokenizer.nextToken();
			if(!stopWordSet.contains(token))
				newLine += (token + " ");
		}
		newLine = newLine.trim();
		
		return newLine;
	}
	
	public static List<String> ngrams(int n, String str) 
	{
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
        return ngrams;
    }

    public static String concat(String[] words, int start, int end) 
    {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }
    
    public Scores negagte3rdCase(List<String> unigrams,ArrayList<Integer> negIndices,ArrayList<Integer> ccIndices,int category,ScoreUtility scoreUtilityObj, String path, float posScore, float negScore, float lexPosScore, float lexNegScore)
	{
		int nextNeg = 0,currToken = 0;
		int negIndex = 0, ccIndex;
		float temp, pScore, nScore,lexPScore, lexNScore;
		boolean negOver = false;
		boolean negate = false;
		String unigram = "";

		Value valueObj = new Value();
		ArrayList<String> modifiedUnigrams=new ArrayList<String>();
		
		while(currToken<unigrams.size())
		{

			unigram=unigrams.get(currToken);
			
			if(!negate)
			{
				if(negIndices.contains(currToken))
				{
					//modifiedUnigrams.add(unigram);
					if(UnigramProbMap.containsKey(unigram))
					{
						posScore += UnigramProbMap.get(unigram).get((category - 1) * 2 + 0);
						negScore += UnigramProbMap.get(unigram).get((category - 1) * 2 + 1);
					}
					valueObj = scoreUtilityObj.getScore1(path, unigram);
		    		posScore += 2 * valueObj.posScore;
		    		negScore += 2 * valueObj.negScore;
		    		lexPosScore += 2 * valueObj.posScore;
		    		lexNegScore += 2 * valueObj.negScore;
					
					while(true)
					{
						if(ccIndices.contains(currToken+1))
						{	
							unigram=unigrams.get(currToken+1);
							if(UnigramProbMap.containsKey(unigram))
							{
								posScore += UnigramProbMap.get(unigram).get((category - 1) * 2 + 0);
								negScore += UnigramProbMap.get(unigram).get((category - 1) * 2 + 1);
							}
							valueObj = scoreUtilityObj.getScore1(path, unigram);
				    		posScore += 2 * valueObj.posScore;
				    		negScore += 2 * valueObj.negScore;
				    		lexPosScore += 2 * valueObj.posScore;
				    		lexNegScore += 2 * valueObj.negScore;
							currToken++;
						}
						else
						{
							break;
						}
					}
					negate=true;
				}	
				else
				{
					if(UnigramProbMap.containsKey(unigram))
					{
						posScore += UnigramProbMap.get(unigram).get((category - 1) * 2 + 0);
						negScore += UnigramProbMap.get(unigram).get((category - 1) * 2 + 1);
					}
					valueObj = scoreUtilityObj.getScore1(path, unigram);
		    		posScore += 2 * valueObj.posScore;
		    		negScore += 2 * valueObj.negScore;
		    		lexPosScore += 2 * valueObj.posScore;
		    		lexNegScore += 2 * valueObj.negScore;
				}
			}
			else
			{
				if(unigram.equals(",")||ccIndices.contains(currToken)||negIndices.contains(currToken))
				{
					if(UnigramProbMap.containsKey(unigram))
					{
						posScore += UnigramProbMap.get(unigram).get((category - 1) * 2 + 0);
						negScore += UnigramProbMap.get(unigram).get((category - 1) * 2 + 1);
					}
					valueObj = scoreUtilityObj.getScore1(path, unigram);
		    		posScore += 2 * valueObj.posScore;
		    		negScore += 2 * valueObj.negScore;
		    		lexPosScore += 2 * valueObj.posScore;
		    		lexNegScore += 2 * valueObj.negScore;
					if(negIndices.contains(currToken))
					{
						while(currToken<unigrams.size())
						{
							if(ccIndices.contains(currToken+1))
							{
								unigram=unigrams.get(currToken+1);
								if(UnigramProbMap.containsKey(unigram))
								{
									posScore += UnigramProbMap.get(unigram).get((category - 1) * 2 + 0);
									negScore += UnigramProbMap.get(unigram).get((category - 1) * 2 + 1);
								}
								valueObj = scoreUtilityObj.getScore1(path, unigram);
					    		posScore += 2 * valueObj.posScore;
					    		negScore += 2 * valueObj.negScore;
					    		lexPosScore += 2 * valueObj.posScore;
					    		lexNegScore += 2 * valueObj.negScore;
								currToken++;
							}
							else
							{
								break;
							}
						}
						negate=true;
					}
					else
						negate=false;
				}
				else
				{
					pScore = nScore = 0.0f;
					if(UnigramProbMap.containsKey(unigram))
					{
						pScore = UnigramProbMap.get(unigram).get((category - 1) * 2 + 0);
						nScore = UnigramProbMap.get(unigram).get((category - 1) * 2 + 1);
					}
					valueObj = scoreUtilityObj.getScore1(path, unigram);
		    		pScore += 2 * valueObj.posScore;
		    		nScore += 2 * valueObj.negScore;
		    		lexPScore = 2 * valueObj.posScore;
		    		lexNScore = 2 * valueObj.negScore;   		
		    		
		    		temp = pScore;
	    			pScore = nScore;
	    			nScore = temp;
	    			temp = lexPScore;
	    			lexPScore = lexNScore;
	    			lexNScore = temp;
	    			posScore += pScore;
		    		negScore += nScore;
		    		lexPosScore += lexPScore;
		    		lexNegScore += lexNScore;
				}	
			}
			currToken++;
		}

		Scores scores = new Scores();
		scores.setPosScore(posScore);
		scores.setNegScore(negScore);
		scores.setLexPosScore(lexPosScore);
		scores.setLexNegScore(lexNegScore);
		
		return scores;
	}
        
    int calculatePolarity(String line, String path, int actualLabel, int category, BufferedWriter bw, ArrayList<Integer> negArrayIndices, ArrayList<Integer> conjArrayIndices)
    {
    	// for Unigrams
    	List <String> unigrams = ngrams(1, line);
    	ScoreUtility scoreUtilityObj;
    	scoreUtilityObj = new ScoreUtility();
    	//path += "/Polarity Scores";
		int noOfPosUnigrams, noOfNegUnigrams, noOfPosBigrams, noOfNegBigrams, noOfPosTrigrams, noOfNegTrigrams;
    	float posScore, negScore, lexPosScore, lexNegScore;
    	UnigramPolarityCount unigramPolarityCount = new UnigramPolarityCount();
		BigramPolarityCount bigramPolarityCount = new BigramPolarityCount();
		TrigramPolarityCount trigramPolarityCount = new TrigramPolarityCount();
		NGramMapCreator nGramMapCreator = new NGramMapCreator();
    	
    	posScore = negScore = 0.0f;
    	lexPosScore = lexNegScore = 0.0f;
    	if(negArrayIndices.size() == 1 && conjArrayIndices.size() == 0)
		{
    		//System.out.println(line + " case 1");
			int i=0,negIndex=negArrayIndices.get(0);
			//System.out.println("negIndex: "+negIndex);
			float temp,pScore,nScore,lexPScore,lexNScore;
			for(String tok : unigrams)
			{
				
				if(UnigramProbMap.containsKey(tok))
				{
					pScore= UnigramProbMap.get(tok).get((category - 1) * 2 + 0);
					nScore= UnigramProbMap.get(tok).get((category - 1) * 2 + 1);
				}
				else
				{
					pScore = 0;
					nScore = 0;
				}
				Value valueObj = new Value();
	    		valueObj = scoreUtilityObj.getScore1(path, tok);
	    		pScore += 2 * valueObj.posScore;
	    		nScore += 2 * valueObj.negScore;
	    		
	    		lexPScore = 2 * valueObj.posScore;
	    		lexNScore = 2 * valueObj.negScore;
	    		//invert score of token before NEG
	    		if(i<negIndex)
	    		{
	    		//	System.out.println("reversing");
	    			temp=pScore;
	    			pScore=nScore;
	    			nScore=temp;
	    			temp=lexPScore;
	    			lexPScore=lexNScore;
	    			lexNScore=temp;
	    		}
	    		posScore+=pScore;
	    		negScore+=nScore;
	    		lexPosScore+=lexPScore;
	    		lexNegScore+=lexNScore;
	    		i++;
	    		
			}
			
			//System.out.println("ps: "+posScore+" ns: "+negScore+" lexps: "+lexPosScore+" lexns: "+lexNegScore);
		}
		else if(negArrayIndices.size() == 1 && conjArrayIndices.size() != 0)
		{
			//System.out.println(line + " case 2");
			int i=0,negIndex=negArrayIndices.get(0),ccIndex=conjArrayIndices.get(0);
			float temp,pScore,nScore,lexPScore,lexNScore;
			//if index of CC is less than NEG : NORMAL BACKWARD NEGATION
			
			if(ccIndex<negIndex)
			{
				
				for(String tok : unigrams)
				{
					if(UnigramProbMap.containsKey(tok))
					{
						pScore= UnigramProbMap.get(tok).get((category - 1) * 2 + 0);
						nScore= UnigramProbMap.get(tok).get((category - 1) * 2 + 1);
					}
					else
					{
						pScore = 0;
						nScore = 0;
					}
					Value valueObj = new Value();
		    		valueObj = scoreUtilityObj.getScore1(path, tok);
		    		pScore += 2 * valueObj.posScore;
		    		nScore += 2 * valueObj.negScore;
		    			
		    		lexPScore = 2 * valueObj.posScore;
		    		lexNScore = 2 * valueObj.negScore;
		    		//invert score of token before NEG
		    		if(i<negIndex)
		    		{
		    			temp=pScore;
		    			pScore=nScore;
		    			nScore=temp;
		    			temp=lexPScore;
		    			lexPScore=lexNScore;
		    			lexNScore=temp;
		    		}
		    		posScore+=pScore;
		    		negScore+=nScore;
		    		lexPosScore+=lexPScore;
		    		lexNegScore+=lexNScore;
		    		i++;
		    		
				}
			}
			else //forward negation
			{
				
				for(String tok : unigrams)
				{
					if(UnigramProbMap.containsKey(tok))
					{
						pScore= UnigramProbMap.get(tok).get((category - 1) * 2 + 0);
						nScore= UnigramProbMap.get(tok).get((category - 1) * 2 + 1);
					}
					else
					{
						pScore = 0;
						nScore = 0;
					}
					Value valueObj = new Value();
		    		valueObj = scoreUtilityObj.getScore1(path, tok);
		    		pScore += 2 * valueObj.posScore;
		    		nScore += 2 * valueObj.negScore;
		    		
		    			
		    		lexPScore = 2 * valueObj.posScore;
		    		lexNScore = 2 * valueObj.negScore;
		    		//invert score of token after CC
		    		if(i>ccIndex)
		    		{
		    			temp=pScore;
		    			pScore=nScore;
		    			nScore=temp;
		    			temp=lexPScore;
		    			lexPScore=lexNScore;
		    			lexNScore=temp;
		    		}
		    		posScore+=pScore;
		    		negScore+=nScore;
		    		lexPosScore+=lexPScore;
		    		lexNegScore+=lexNScore;
		    		i++;
		    		
				}
			}
		}
		else if(negArrayIndices.size() > 1)
		{
			//System.out.println(line + " case 3");
			Scores scores = negagte3rdCase(unigrams, negArrayIndices, conjArrayIndices, category, scoreUtilityObj, path, posScore, negScore, lexPosScore, lexNegScore);
			posScore += scores.getPosScore();
			negScore += scores.getNegScore();
			lexPosScore += scores.getLexPosScore();
			lexNegScore += scores.getLexNegScore();
		}
		else if(negArrayIndices.size() == 0)
		{
			//System.out.println(line + " case 0");
			for(String word : unigrams)
	    	{
	    		if(UnigramProbMap.containsKey(word))
	    		{
	    			posScore += UnigramProbMap.get(word).get((category - 1) * 2 + 0);
	    			negScore += UnigramProbMap.get(word).get((category - 1) * 2 + 1);
	    		}
	    		Value valueObj = new Value();
	    		valueObj = scoreUtilityObj.getScore1(path, word);
	    		posScore += 2 * valueObj.posScore;
	    		negScore += 2 * valueObj.negScore;
	    		lexPosScore += 2 * valueObj.posScore;
	    		lexNegScore += 2 * valueObj.negScore;
	    		
	    	}
		}
    	
    	//for Bigrams
    	unigrams = ngrams(2, line);
    	for(String word : unigrams)
    	{
    		if(BigramProbMap.containsKey(word))
    		{
    			posScore += BigramProbMap.get(word).get((category - 1) * 2 + 0);
    			negScore += BigramProbMap.get(word).get((category - 1) * 2 + 1);
    		}
    	}
    	
    	//for Trigrams
    	unigrams = ngrams(3, line);
    	for(String word : unigrams)
    	{
    		if(TrigramProbMap.containsKey(word))
    		{
    			posScore += TrigramProbMap.get(word).get((category - 1) * 2 + 0);
    			negScore += TrigramProbMap.get(word).get((category - 1) * 2 + 1);
    		}
    	}
    	
    	/*
    	trigramPolarityCount = nGramMapCreator.getNoOfTrigrams(line, category, path);
		noOfPosTrigrams = trigramPolarityCount.getNoOfPosTrigrams();
		noOfNegTrigrams = trigramPolarityCount.getNoOfNegTrigrams();
		bigramPolarityCount = nGramMapCreator.getNoOfBigrams(line, category, path);
		noOfPosBigrams = bigramPolarityCount.getNoOfPosBigrams();
		noOfNegBigrams = bigramPolarityCount.getNoOfNegBigrams();
    	unigramPolarityCount = nGramMapCreator.getNoOfUnigrams(line, category, path);
		noOfPosUnigrams = unigramPolarityCount.getNoOfPosUnigrams();
		noOfNegUnigrams = unigramPolarityCount.getNoOfNegUnigrams();
		*/
		
    	String lineToWriteForSVM;
    	/*
    	lineToWriteForSVM = String.valueOf(actualLabel) + " 1:" + String.valueOf(posScore) + " 2:" + String.valueOf(negScore);
    	lineToWriteForSVM += " 3:" + String.valueOf(lexPosScore) + " 4:" + String.valueOf(lexNegScore);
    	//lineToWriteForSVM += " 5:" + String.valueOf(noOfPosTrigrams) + " 6:" + String.valueOf(noOfNegTrigrams);
    	//lineToWriteForSVM += " 7:" + String.valueOf(noOfPosBigrams) + " 8:" + String.valueOf(noOfNegBigrams);
    	//lineToWriteForSVM += " 9:" + String.valueOf(noOfPosUnigrams) + " 10:" + String.valueOf(noOfNegUnigrams);
    	lineToWriteForSVM += "\n";
		*/
    	lineToWriteForSVM = String.valueOf(actualLabel + 1) + "," + String.valueOf(posScore) + "," + String.valueOf(negScore) + ",";
    	lineToWriteForSVM += String.valueOf(lexPosScore) + "," + String.valueOf(lexNegScore);
    	lineToWriteForSVM += "\n";
		
    	try 
    	{
			bw.write(lineToWriteForSVM);
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	
    	if(posScore >= negScore)
    		return 0;
    	else
    		return 1;
    }
	
    void polarityDecider(String path, ArrayList<Sentence> parsedSentences, int category, int posOrNeg)
    {

		try 
		{
			String line;
			int polarity, noOfSamples, correctlyClassified;
			correctlyClassified = noOfSamples = 0;
			BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/SVM/featureVectorTest"+ category +".txt",true));
			
			ArrayList<Phrase> phrases;
			ArrayList<Token> tokens;
			ArrayList<Integer> negArrayIndices, conjArrayIndices;
			for (Sentence s : parsedSentences) 
			{
				phrases=s.getPhrases();
				
				line = "";
				negArrayIndices = new ArrayList<Integer>();
				conjArrayIndices = new ArrayList<Integer>();
				int wordPos=0;
				for(Phrase p:phrases)
				{
					tokens=p.getTokens();
					for(Token t:tokens)
					{
						if(!stopWordSet.contains(t.getWord()))
						{
							
							//line += t.getWord() + " ";
							if(t.getTag().equals("NEG"))
							{
								negArrayIndices.add(wordPos);
							}
							else if(t.getTag().equals("CC"))
								conjArrayIndices.add(wordPos);
							line+=t.getRootWord()+" ";
							wordPos++;
						}
						
					}
					
				}
				
				line=line.trim();
				polarity = calculatePolarity(line, path, posOrNeg, category, bw, negArrayIndices, conjArrayIndices);
				if(polarity == posOrNeg)
					correctlyClassified++;
				else
					System.out.println(line);
				noOfSamples++;
			}
			//System.out.println("Sample No." + noOfSamples + " Actual Polarity:0" +" Predicted Polarity:"+polarity);
			bw.close();
			System.out.println("Correctly Classified:" + correctlyClassified + " No of Samples:" + noOfSamples);
			System.out.println("Accuracy:"+(((float)correctlyClassified) / ((float)noOfSamples)));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	
    }
    
    /*
	void polarityDecider(String path, Set<String> set)
	{
		try 
		{
			String line;
			int polarity, noOfSamples, correctlyClassified;
			correctlyClassified = noOfSamples = 0;
			BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/SVM/featureVectorTest.txt"));
			BufferedReader brPos = new BufferedReader(new FileReader(path + "/Reviews/Test/PositiveReviews.txt"));
			BufferedReader brNeg = new BufferedReader(new FileReader(path + "/Reviews/Test/NegativeReviews.txt"));
			while((line = brPos.readLine()) != null)
			{
				line = removeStopWords(line);
				
				
				polarity = calculatePolarity(line, path, 0, bw);
				if(polarity == 0)
					correctlyClassified++;
				noOfSamples++;
				//System.out.println("Sample No." + noOfSamples + " Actual Polarity:0" +" Predicted Polarity:"+polarity);
			}
			while((line = brNeg.readLine()) != null)
			{
				line = removeStopWords(line);
				polarity = calculatePolarity(line, path, 1, bw);
				if(polarity == 1)
					correctlyClassified++;
				noOfSamples++;
				//System.out.println("Sample No." + noOfSamples + " Actual Polarity:1" +" Predicted Polarity:"+polarity);
			}
			brPos.close();
			brNeg.close();
			bw.close();
			System.out.println("Correctly Classified:" + correctlyClassified + " No of Samples:" + noOfSamples);
			System.out.println("Accuracy:"+(((float)correctlyClassified) / ((float)noOfSamples)));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	*/
}
