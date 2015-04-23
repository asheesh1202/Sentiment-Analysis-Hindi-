package com.hindi.sa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.hindi.ssfreader.Phrase;
import com.hindi.ssfreader.Sentence;
import com.hindi.ssfreader.Token;

class Scores
{
	float posScore, negScore, lexPosScore, lexNegScore;

	public float getPosScore() {
		return posScore;
	}

	public void setPosScore(float posScore) {
		this.posScore = posScore;
	}

	public float getNegScore() {
		return negScore;
	}

	public void setNegScore(float negScore) {
		this.negScore = negScore;
	}

	public float getLexPosScore() {
		return lexPosScore;
	}

	public void setLexPosScore(float lexPosScore) {
		this.lexPosScore = lexPosScore;
	}

	public float getLexNegScore() {
		return lexNegScore;
	}

	public void setLexNegScore(float lexNegScore) {
		this.lexNegScore = lexNegScore;
	}
	
	
}

class UnigramPolarityCount
{
	int noOfPosUnigrams, noOfNegUnigrams;

	public int getNoOfPosUnigrams() {
		return noOfPosUnigrams;
	}

	public void setNoOfPosUnigrams(int noOfPosUnigrams) {
		this.noOfPosUnigrams = noOfPosUnigrams;
	}

	public int getNoOfNegUnigrams() {
		return noOfNegUnigrams;
	}

	public void setNoOfNegUnigrams(int noOfNegUnigrams) {
		this.noOfNegUnigrams = noOfNegUnigrams;
	}
}

class BigramPolarityCount
{
	int noOfPosBigrams, noOfNegBigrams;

	public int getNoOfPosBigrams() {
		return noOfPosBigrams;
	}

	public void setNoOfPosBigrams(int noOfPosBigrams) {
		this.noOfPosBigrams = noOfPosBigrams;
	}

	public int getNoOfNegBigrams() {
		return noOfNegBigrams;
	}

	public void setNoOfNegBigrams(int noOfNegBigrams) {
		this.noOfNegBigrams = noOfNegBigrams;
	}
}

class TrigramPolarityCount
{
	int noOfPosTrigrams, noOfNegTrigrams;

	public int getNoOfPosTrigrams() {
		return noOfPosTrigrams;
	}

	public void setNoOfPosTrigrams(int noOfPosTrigrams) {
		this.noOfPosTrigrams = noOfPosTrigrams;
	}

	public int getNoOfNegTrigrams() {
		return noOfNegTrigrams;
	}

	public void setNoOfNegTrigrams(int noOfNegTrigrams) {
		this.noOfNegTrigrams = noOfNegTrigrams;
	}
}

public class NGramMapCreator 
{
	public TreeMap<String, ArrayList<Integer>> UnigramCountMap = new TreeMap<String, ArrayList<Integer>>();
	public TreeMap<String, ArrayList<Integer>> BigramCountMap = new TreeMap<String, ArrayList<Integer>>();
	public TreeMap<String, ArrayList<Integer>> TrigramCountMap = new TreeMap<String, ArrayList<Integer>>();
	public TreeMap<String, ArrayList<Float>> UnigramProbMap = new TreeMap<String, ArrayList<Float>>();
	public TreeMap<String, ArrayList<Float>> BigramProbMap = new TreeMap<String, ArrayList<Float>>();
	public TreeMap<String, ArrayList<Float>> TrigramProbMap = new TreeMap<String, ArrayList<Float>>();
	Set<String> stopWordSet;
	
	public Set<String> getStopWordSet() {
		return stopWordSet;
	}

	public void setStopWordSet(Set<String> stopWordSet) {
		this.stopWordSet = stopWordSet;
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
    
	void populateUnigrams(String line, int posOrNeg, int category)
	{
		List<String> tokens = ngrams(1, line);
		for(String token : tokens)
		{
			if(UnigramCountMap.containsKey(token))
			{
				ArrayList<Integer> countList = new ArrayList<Integer>();
				countList = UnigramCountMap.get(token);
				int index = (category - 1) * 2 + posOrNeg;
				int newVal = countList.get(index) + 1;
				countList.set(index, newVal);
				UnigramCountMap.put(token, countList);
			}
			else
			{
				ArrayList<Integer> countList = new ArrayList<Integer>();
				for(int i=0;i<10;i++)
					countList.add(0);
				int in=(category - 1) * 2 + posOrNeg;
				countList.set(in, 1);
				UnigramCountMap.put(token, countList);
			}
		}
	}
	
	void populateBigrams(String line, int posOrNeg, int category)
	{
		List<String> tokens = ngrams(2, line);
		for(String token : tokens)
		{
			if(BigramCountMap.containsKey(token))
			{
				ArrayList<Integer> countList = new ArrayList<Integer>();
				countList = BigramCountMap.get(token);
				int index = (category - 1) * 2 + posOrNeg;
				int newVal = countList.get(index) + 1;
				countList.set(index, newVal);
				BigramCountMap.put(token, countList);
			}
			else
			{
				ArrayList<Integer> countList = new ArrayList<Integer>();
				for(int i=0;i<10;i++)
					countList.add(0);
				int in=(category - 1) * 2 + posOrNeg;
				countList.set(in, 1);
				BigramCountMap.put(token, countList);
			}
		}
	}
	
	void populateTrigrams(String line, int posOrNeg, int category)
	{
		List<String> tokens = ngrams(3, line);
		for(String token : tokens)
		{
			if(TrigramCountMap.containsKey(token))
			{
				ArrayList<Integer> countList = new ArrayList<Integer>();
				countList = TrigramCountMap.get(token);
				int index = (category - 1) * 2 + posOrNeg;
				int newVal = countList.get(index) + 1;
				countList.set(index, newVal);
				TrigramCountMap.put(token, countList);
			}
			else
			{
				ArrayList<Integer> countList = new ArrayList<Integer>();
				for(int i=0;i<10;i++)
					countList.add(0);
				int in=(category - 1) * 2 + posOrNeg;
				countList.set(in, 1);
				TrigramCountMap.put(token, countList);
			}
		}
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
					posScore += UnigramCountMap.get(unigram).get((category - 1) * 2 + 0);
					negScore += UnigramCountMap.get(unigram).get((category - 1) * 2 + 1);
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
							posScore += UnigramCountMap.get(unigram).get((category - 1) * 2 + 0);
							negScore += UnigramCountMap.get(unigram).get((category - 1) * 2 + 1);
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
					posScore += UnigramCountMap.get(unigram).get((category - 1) * 2 + 0);
					negScore += UnigramCountMap.get(unigram).get((category - 1) * 2 + 1);
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
					posScore += UnigramCountMap.get(unigram).get((category - 1) * 2 + 0);
					negScore += UnigramCountMap.get(unigram).get((category - 1) * 2 + 1);
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
								posScore += UnigramCountMap.get(unigram).get((category - 1) * 2 + 0);
								negScore += UnigramCountMap.get(unigram).get((category - 1) * 2 + 1);
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
					pScore = UnigramCountMap.get(unigram).get((category - 1) * 2 + 0);
					nScore = UnigramCountMap.get(unigram).get((category - 1) * 2 + 1);
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
	
	public UnigramPolarityCount getNoOfUnigrams(String line, int category, String path)
	{
		List<String> unigrams = ngrams(1, line);
		int noOfPosUnigrams, noOfNegUnigrams;
		float posScore, negScore;
		Value valueObj = new Value();
		ScoreUtility scoreUtilityObj = new ScoreUtility();
		UnigramPolarityCount unigramPolarityCount = new UnigramPolarityCount();
		
		noOfPosUnigrams = noOfNegUnigrams = 0;
		for(String unigram : unigrams)
		{
			if(UnigramProbMap.containsKey(unigram))
			{
				posScore = UnigramProbMap.get(unigram).get((category - 1) * 2 + 0);
				negScore = UnigramProbMap.get(unigram).get((category - 1) * 2 + 1);
			}
			else
			{
	    		valueObj = scoreUtilityObj.getScore1(path, unigram);
	    		posScore = valueObj.posScore;
	    		negScore = valueObj.negScore;
			}
			if(posScore == negScore)
				continue;
			if(posScore > negScore)
				noOfPosUnigrams++;
			else
				noOfNegUnigrams++;
		}
		unigramPolarityCount.setNoOfPosUnigrams(noOfPosUnigrams);
		unigramPolarityCount.setNoOfNegUnigrams(noOfNegUnigrams);
		
		return unigramPolarityCount;
	}
	
	public BigramPolarityCount getNoOfBigrams(String line, int category, String path)
	{
		List<String> bigrams = ngrams(2, line);
		BigramPolarityCount bigramPolarityCount = new BigramPolarityCount();
		UnigramPolarityCount unigramPolarityCount = new UnigramPolarityCount();
		int noOfPosBigrams, noOfNegBigrams;
		float posScore, negScore;
		
		noOfPosBigrams = noOfNegBigrams = 0;
		for(String bigram : bigrams)
		{
			posScore = negScore = 0.0f;
			if(BigramProbMap.containsKey(bigram))
			{
				posScore = BigramProbMap.get(bigram).get((category - 1) * 2 + 0);
				negScore = BigramProbMap.get(bigram).get((category - 1) * 2 + 1);
			}
			else
			{
				List<String> unigrams = ngrams(1, bigram);
				unigramPolarityCount = getNoOfUnigrams(bigram, category, path);
				noOfPosBigrams += unigramPolarityCount.getNoOfPosUnigrams();
				noOfNegBigrams += unigramPolarityCount.getNoOfNegUnigrams();
				continue;
			}
			if(posScore == negScore)
				continue;
			if(posScore > negScore)
				noOfPosBigrams++;
			else
				noOfNegBigrams++;
		}
		bigramPolarityCount.setNoOfPosBigrams(noOfPosBigrams);
		bigramPolarityCount.setNoOfNegBigrams(noOfNegBigrams);
		
		return bigramPolarityCount;
	}
	
	public TrigramPolarityCount getNoOfTrigrams(String line, int category, String path)
	{
		List<String> trigrams = ngrams(3, line);
		TrigramPolarityCount trigramPolarityCount = new TrigramPolarityCount();
		BigramPolarityCount bigramPolarityCount = new BigramPolarityCount();
		int noOfPosTrigrams, noOfNegTrigrams;
		float posScore, negScore;
		
		noOfPosTrigrams = noOfNegTrigrams = 0;
		for(String trigram : trigrams)
		{
			posScore = negScore = 0.0f;
			if(TrigramProbMap.containsKey(trigram))
			{
				posScore = TrigramProbMap.get(trigram).get((category - 1) * 2 + 0);
				negScore = TrigramProbMap.get(trigram).get((category - 1) * 2 + 1);
			}
			else
			{
				List<String> bigrams = ngrams(2, trigram);
				bigramPolarityCount = getNoOfBigrams(trigram, category, path);
				noOfPosTrigrams += bigramPolarityCount.getNoOfPosBigrams();
				noOfNegTrigrams += bigramPolarityCount.getNoOfNegBigrams();
				continue;
			}
			if(posScore == negScore)
				continue;
			if(posScore > negScore)
				noOfPosTrigrams++;
			else
				noOfNegTrigrams++;
		}
		trigramPolarityCount.setNoOfPosTrigrams(noOfPosTrigrams);
		trigramPolarityCount.setNoOfNegTrigrams(noOfNegTrigrams);
		
		return trigramPolarityCount;
	}
	
	void writeIntoFileForSVM(String path, ArrayList<Sentence> posParsedSentences, ArrayList<Sentence> negParsedSentences, int category)
	{
		
		try 
		{
			String line, lineToBeWrittenInSVM;
			float posScore, negScore, lexPosScore, lexNegScore;
			BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/SVM/featureVectorTrain" + category + ".txt"));
			ScoreUtility scoreUtilityObj = new ScoreUtility();
			int noOfPosUnigrams, noOfNegUnigrams, noOfPosBigrams, noOfNegBigrams, noOfPosTrigrams, noOfNegTrigrams;
			UnigramPolarityCount unigramPolarityCount = new UnigramPolarityCount();
			BigramPolarityCount bigramPolarityCount = new BigramPolarityCount();
			TrigramPolarityCount trigramPolarityCount = new TrigramPolarityCount();
			
			int negHandlingCase;
			ArrayList<Phrase> phrases;
			ArrayList<Token> tokens;
			ArrayList<Integer> negArrayIndices, conjArrayIndices;
			for (Sentence s : posParsedSentences) 
			{
				line="";
				phrases=s.getPhrases();
				negArrayIndices = new ArrayList<Integer>();
				conjArrayIndices = new ArrayList<Integer>();
				int wordPos = 0;
				for(Phrase p:phrases)
				{
					tokens=p.getTokens();
					
					for(Token t:tokens)
					{
						if(!stopWordSet.contains(t.getWord()))
						{
							//line += t.getWord() + " ";
							if(t.getTag().equals("NEG"))
								negArrayIndices.add(wordPos);
							else if(t.getTag().equals("CC"))
								conjArrayIndices.add(wordPos);
							line+=t.getRootWord()+" ";
							wordPos++;
						}
					}
				}
				line=line.trim();
				lineToBeWrittenInSVM = "";
				
				/* There are 3 cases for Negation Handling, here we are finding if there is negation, 
				 * then which case does it belong to. The following code does the same
				 */
				negHandlingCase = 0;
				List<String> unigrams = ngrams(1, line);
				posScore = negScore = 0.0f;
				lexPosScore = lexNegScore = 0.0f;
				
				if(negArrayIndices.size() == 1 && conjArrayIndices.size() == 0)
				{
					int i=0,negIndex=negArrayIndices.get(0);
					
					float temp,pScore,nScore,lexPScore,lexNScore;
					for(String tok : unigrams)
					{
						pScore= UnigramCountMap.get(tok).get((category - 1) * 2 + 0);
						nScore= UnigramCountMap.get(tok).get((category - 1) * 2 + 1);
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
				else if(negArrayIndices.size() == 1 && conjArrayIndices.size() != 0)
				{
					int i=0,negIndex=negArrayIndices.get(0),ccIndex=conjArrayIndices.get(0);
					float temp,pScore,nScore,lexPScore,lexNScore;
					//if index of CC is less than NEG : NORMAL BACKWARD NEGATION
					
					if(ccIndex<negIndex)
					{
						
						for(String tok : unigrams)
						{
							pScore= UnigramCountMap.get(tok).get((category - 1) * 2 + 0);
							nScore= UnigramCountMap.get(tok).get((category - 1) * 2 + 1);
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
							pScore= UnigramCountMap.get(tok).get((category - 1) * 2 + 0);
							nScore= UnigramCountMap.get(tok).get((category - 1) * 2 + 1);
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
					Scores scores = negagte3rdCase(unigrams, negArrayIndices, conjArrayIndices, category, scoreUtilityObj, path, posScore, negScore, lexPosScore, lexNegScore);
					posScore += scores.getPosScore();
					negScore += scores.getNegScore();
					lexPosScore += scores.getLexPosScore();
					lexNegScore += scores.getLexNegScore();
				}
				else if(negArrayIndices.size() == 0) // No negation
				{
					// Unigrams
					for(String tok : unigrams)
					{
						posScore += UnigramCountMap.get(tok).get((category - 1) * 2 + 0);
						negScore += UnigramCountMap.get(tok).get((category - 1) * 2 + 1);
						Value valueObj = new Value();
			    		valueObj = scoreUtilityObj.getScore1(path, tok);
			    		posScore += 2 * valueObj.posScore;
			    		negScore += 2 * valueObj.negScore;
			    		lexPosScore += 2 * valueObj.posScore;
			    		lexNegScore += 2 * valueObj.negScore;
					}
				}
				// Bigrams
				List<String> bigrams = ngrams(2, line);
				for(String tok : bigrams)
				{
					posScore += BigramCountMap.get(tok).get((category - 1) * 2 + 0);
					negScore += BigramCountMap.get(tok).get((category - 1) * 2 + 1);
				}
				
				// Trigrams
				List<String> trigrams = ngrams(3, line);
				for(String tok : trigrams)
				{
					posScore += TrigramCountMap.get(tok).get((category - 1) * 2 + 0);
					negScore += TrigramCountMap.get(tok).get((category - 1) * 2 + 1);
				}
				
				/*
				trigramPolarityCount = getNoOfTrigrams(line, category, path);
				noOfPosTrigrams = trigramPolarityCount.getNoOfPosTrigrams();
				noOfNegTrigrams = trigramPolarityCount.getNoOfNegTrigrams();
				bigramPolarityCount = getNoOfBigrams(line, category, path);
				noOfPosBigrams = bigramPolarityCount.getNoOfPosBigrams();
				noOfNegBigrams = bigramPolarityCount.getNoOfNegBigrams();
				unigramPolarityCount = getNoOfUnigrams(line, category, path);
				noOfPosUnigrams = unigramPolarityCount.getNoOfPosUnigrams();
				noOfNegUnigrams = unigramPolarityCount.getNoOfNegUnigrams();
				*/
				
				/*
				lineToBeWrittenInSVM = "0 " + " 1:" + String.valueOf(posScore) + " 2:" + String.valueOf(negScore);
				lineToBeWrittenInSVM += " 3:" + String.valueOf(lexPosScore) + " 4:" + String.valueOf(lexNegScore);
				//lineToBeWrittenInSVM += " 5:" + String.valueOf(noOfPosTrigrams) + " 6:" + String.valueOf(noOfNegTrigrams);
				//lineToBeWrittenInSVM += " 7:" + String.valueOf(noOfPosBigrams) + " 8:" + String.valueOf(noOfNegBigrams);
				//lineToBeWrittenInSVM += " 9:" + String.valueOf(noOfPosUnigrams) + " 10:" + String.valueOf(noOfNegUnigrams);
				lineToBeWrittenInSVM += "\n";
				*/
				lineToBeWrittenInSVM = "1," + String.valueOf(posScore) + "," + String.valueOf(negScore) + ",";
				lineToBeWrittenInSVM += String.valueOf(lexPosScore) + "," + String.valueOf(lexNegScore);
				lineToBeWrittenInSVM += "\n";
				
				bw.write(lineToBeWrittenInSVM);
			}
			for (Sentence s : negParsedSentences) 
			{
				line="";
				phrases=s.getPhrases();
				negArrayIndices = new ArrayList<Integer>();
				conjArrayIndices = new ArrayList<Integer>();
				int wordPos = 0;
				for(Phrase p:phrases)
				{
					tokens=p.getTokens();
					
					for(Token t:tokens)
					{
						if(!stopWordSet.contains(t.getWord()))
						{
							//line += t.getWord() + " ";
							if(t.getTag().equals("NEG"))
								negArrayIndices.add(wordPos);
							else if(t.getTag().equals("CC"))
								conjArrayIndices.add(wordPos);
							line+=t.getRootWord()+" ";
							wordPos++;
						}
					}
				}
				line=line.trim();
				lineToBeWrittenInSVM = "";
				
				List<String> unigrams = ngrams(1, line);
				posScore = negScore = 0.0f;
				lexPosScore = lexNegScore = 0.0f;
				
				
				if(negArrayIndices.size() == 1 && conjArrayIndices.size() == 0)
				{
					int i=0,negIndex=negArrayIndices.get(0);
					
					float temp,pScore,nScore,lexPScore,lexNScore;
					for(String tok : unigrams)
					{
						pScore= UnigramCountMap.get(tok).get((category - 1) * 2 + 0);
						nScore= UnigramCountMap.get(tok).get((category - 1) * 2 + 1);
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
				else if(negArrayIndices.size() == 1 && conjArrayIndices.size() != 0)
				{
					int i=0,negIndex=negArrayIndices.get(0),ccIndex=conjArrayIndices.get(0);
					float temp,pScore,nScore,lexPScore,lexNScore;
					//if index of CC is less than NEG : NORMAL BACKWARD NEGATION
					
					if(ccIndex<negIndex)
					{
						
						for(String tok : unigrams)
						{
							pScore= UnigramCountMap.get(tok).get((category - 1) * 2 + 0);
							nScore= UnigramCountMap.get(tok).get((category - 1) * 2 + 1);
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
							pScore= UnigramCountMap.get(tok).get((category - 1) * 2 + 0);
							nScore= UnigramCountMap.get(tok).get((category - 1) * 2 + 1);
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
					Scores scores = negagte3rdCase(unigrams, negArrayIndices, conjArrayIndices, category, scoreUtilityObj, path, posScore, negScore, lexPosScore, lexNegScore);
					posScore += scores.getPosScore();
					negScore += scores.getNegScore();
					lexPosScore += scores.getLexPosScore();
					lexNegScore += scores.getLexNegScore();
				}
				else if(negArrayIndices.size() == 0)// No Negation
				{
					// Unigrams
					unigrams = ngrams(1, line);
					posScore = negScore = 0.0f;
					lexPosScore = lexNegScore = 0.0f;
					for(String tok : unigrams)
					{
						posScore += UnigramCountMap.get(tok).get((category - 1) * 2 + 0);
						negScore += UnigramCountMap.get(tok).get((category - 1) * 2 + 1);
						Value valueObj = new Value();
			    		valueObj = scoreUtilityObj.getScore1(path, tok);
			    		posScore += 2 * valueObj.posScore;
			    		negScore += 2 * valueObj.negScore;
			    		lexPosScore += 2 * valueObj.posScore;
			    		lexNegScore += 2 * valueObj.negScore;
			    		
					}
				}
				// Bigrams
				List<String> bigrams = ngrams(2, line);
				for(String tok : bigrams)
				{
					posScore += BigramCountMap.get(tok).get((category - 1) * 2 + 0);
					negScore += BigramCountMap.get(tok).get((category - 1) * 2 + 1);
				}
				
				// Trigrams
				List<String> trigrams = ngrams(3, line);
				for(String tok : trigrams)
				{
					posScore += TrigramCountMap.get(tok).get((category - 1) * 2 + 0);
					negScore += TrigramCountMap.get(tok).get((category - 1) * 2 + 1);
				}
				
				/*
				trigramPolarityCount = getNoOfTrigrams(line, category, path);
				noOfPosTrigrams = trigramPolarityCount.getNoOfPosTrigrams();
				noOfNegTrigrams = trigramPolarityCount.getNoOfNegTrigrams();
				bigramPolarityCount = getNoOfBigrams(line, category, path);
				noOfPosBigrams = bigramPolarityCount.getNoOfPosBigrams();
				noOfNegBigrams = bigramPolarityCount.getNoOfNegBigrams();
				unigramPolarityCount = getNoOfUnigrams(line, category, path);
				noOfPosUnigrams = unigramPolarityCount.getNoOfPosUnigrams();
				noOfNegUnigrams = unigramPolarityCount.getNoOfNegUnigrams();
				*/
				
				/*
				lineToBeWrittenInSVM = "1 " + " 1:" + String.valueOf(posScore) + " 2:" + String.valueOf(negScore);
				lineToBeWrittenInSVM += " 3:" + String.valueOf(lexPosScore) + " 4:" + String.valueOf(lexNegScore);
				//lineToBeWrittenInSVM += " 5:" + String.valueOf(noOfPosTrigrams) + " 6:" + String.valueOf(noOfNegTrigrams);
				//lineToBeWrittenInSVM += " 7:" + String.valueOf(noOfPosBigrams) + " 8:" + String.valueOf(noOfNegBigrams);
				//lineToBeWrittenInSVM += " 9:" + String.valueOf(noOfPosUnigrams) + " 10:" + String.valueOf(noOfNegUnigrams);
				lineToBeWrittenInSVM += "\n";
				*/
				lineToBeWrittenInSVM = "2," + String.valueOf(posScore) + "," + String.valueOf(negScore) + ",";
				lineToBeWrittenInSVM += String.valueOf(lexPosScore) + "," + String.valueOf(lexNegScore);
				lineToBeWrittenInSVM += "\n";
				
				bw.write(lineToBeWrittenInSVM);
			}
			
			bw.close();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void createNGramMap(String path,ArrayList<Sentence> parsedSentences,int category,int posOrNeg)
	{
		
		try 
		{
			//BufferedReader brPos = new BufferedReader(new FileReader(path + "/Reviews/Train/PositiveReviews.txt"));
			//BufferedReader brNeg = new BufferedReader(new FileReader(path + "/Reviews/Train/NegativeReviews.txt"));
			
			ArrayList<Phrase> phrases;
			ArrayList<Token> tokens;
			for (Sentence s : parsedSentences) 
			{
				String line="";
				phrases=s.getPhrases();
				
				
				for(Phrase p:phrases)
				{
					tokens=p.getTokens();
					
					for(Token t:tokens)
					{
						if(!stopWordSet.contains(t.getWord()))
						{
							//line += t.getWord() + " ";
							line+=t.getRootWord()+" ";
						}
					}
					
				}
				
				line=line.trim();
				populateUnigrams(line, posOrNeg, category);
				populateBigrams(line, posOrNeg, category);
				populateTrigrams(line, posOrNeg, category);

				
			}
			
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	
	/* Description for populateUnigrams, populateBigrams, populateTrigrams
	 * 2nd argument 0-> positive, 1-> negative
	 * 3rd argument 1-> products, 2-> entertainment, 3-> politics, 4-> food & tourism, 5-> sports
	 * */
	
	
	public void createNGramMap(Set<String> set, String path)
	{
		stopWordSet = set;
		
		try 
		{
			BufferedReader brPos = new BufferedReader(new FileReader(path + "/Reviews/Train/PositiveReviews.txt"));
			BufferedReader brNeg = new BufferedReader(new FileReader(path + "/Reviews/Train/NegativeReviews.txt"));
			String line;
			while((line = brPos.readLine()) != null)
			{
				line = removeStopWords(line);
				populateUnigrams(line, 0, 1);
				populateBigrams(line, 0, 1);
				populateTrigrams(line, 0, 1);
				
			}
			brPos.close();
			while((line = brNeg.readLine()) != null)
			{
				line = removeStopWords(line);
				populateUnigrams(line, 1, 1);
				populateBigrams(line, 1, 1);
				populateTrigrams(line, 1, 1);
			}
			
			brNeg.close();
			
			for(Entry entry: UnigramCountMap.entrySet())
			{
				System.out.println(entry.getKey()+" : "+(ArrayList<Integer>)entry.getValue());
			}
			for(Entry entry: BigramCountMap.entrySet())
			{
				System.out.println(entry.getKey()+" : "+(ArrayList<Integer>)entry.getValue());
			}
			for(Entry entry: TrigramCountMap.entrySet())
			{
				System.out.println(entry.getKey()+" : "+(ArrayList<Integer>)entry.getValue());
			}
			System.out.println("Unigram Size :" + UnigramCountMap.size());
			System.out.println("Bigram Size :" + BigramCountMap.size());
			System.out.println("Trigram Size :" + TrigramCountMap.size());
			
			
			
			//Calculate Probabilities and store them.
			 storeProbability(UnigramCountMap, UnigramProbMap);
			 storeProbability(BigramCountMap,  BigramProbMap);
			 storeProbability(TrigramCountMap, TrigramProbMap);
			
			 //writeIntoFileForSVM(path);
			 
			/* for(Entry e: UnigramProbMap.entrySet())
			 {
				 System.out.println((String)e.getKey()+":"+(ArrayList<Float>)e.getValue());
			 }*/
			 /*for(Entry e: BigramProbMap.entrySet())
			 {
				 System.out.println((String)e.getKey()+":"+(ArrayList<Float>)e.getValue());
			 }*/
			 /*for(Entry e: TrigramProbMap.entrySet())
			 {
				 System.out.println((String)e.getKey()+":"+(ArrayList<Float>)e.getValue());
			 }*/
			 
			 FileOutputStream fout = new FileOutputStream(path+"/SERs/unigram_prob.ser");
			 ObjectOutputStream out = new ObjectOutputStream(fout);
			 out.writeObject(UnigramProbMap);
			 
			 FileOutputStream bigram_fout = new FileOutputStream(path+"/SERs/bigram_prob.ser");
			 ObjectOutputStream bigram_out = new ObjectOutputStream(bigram_fout);
			 bigram_out.writeObject(BigramProbMap);
			 
			 FileOutputStream trigram_fout = new FileOutputStream(path+"/SERs/trigram_prob.ser");
			 ObjectOutputStream trigram_out = new ObjectOutputStream(trigram_fout);
			 trigram_out.writeObject(TrigramProbMap);
			 
			 
			 out.close();
			 bigram_out.close();
			 trigram_out.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void storeProbabilities(String path, ArrayList<Sentence> posParsedSentences, ArrayList<Sentence> negParsedSentences, int category)
	{
		try
		{
		//Calculate Probabilities and store them.
		/*	
		for(Entry entry: UnigramCountMap.entrySet())
		{
			System.out.println(entry.getKey()+" : "+(ArrayList<Integer>)entry.getValue());
		}
		for(Entry entry: BigramCountMap.entrySet())
		{
			System.out.println(entry.getKey()+" : "+(ArrayList<Integer>)entry.getValue());
		}
		for(Entry entry: TrigramCountMap.entrySet())
		{
			System.out.println(entry.getKey()+" : "+(ArrayList<Integer>)entry.getValue());
		}
		*/
		System.out.println("Unigram Size :" + UnigramCountMap.size());
		System.out.println("Bigram Size :" + BigramCountMap.size());
		System.out.println("Trigram Size :" + TrigramCountMap.size());
			
		storeProbability(UnigramCountMap, UnigramProbMap);
		storeProbability(BigramCountMap,  BigramProbMap);
		storeProbability(TrigramCountMap, TrigramProbMap);
		
		writeIntoFileForSVM(path, posParsedSentences, negParsedSentences, category);
		 
		 
		FileOutputStream fout = new FileOutputStream(path+"/SERs/unigram_prob.ser");
		ObjectOutputStream out = new ObjectOutputStream(fout);
		out.writeObject(UnigramProbMap);
		 
		FileOutputStream bigram_fout = new FileOutputStream(path+"/SERs/bigram_prob.ser");
		ObjectOutputStream bigram_out = new ObjectOutputStream(bigram_fout);
		bigram_out.writeObject(BigramProbMap);
		 
		FileOutputStream trigram_fout = new FileOutputStream(path+"/SERs/trigram_prob.ser");
		ObjectOutputStream trigram_out = new ObjectOutputStream(trigram_fout);
		trigram_out.writeObject(TrigramProbMap);
		 
		 
		out.close();
		bigram_out.close();
		trigram_out.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void storeProbability(TreeMap<String, ArrayList<Integer>> map,TreeMap<String,ArrayList<Float>> probMap)
	{
		for(Entry e: map.entrySet())
		{
			ArrayList<Integer> countList=(ArrayList<Integer>)e.getValue();
			ArrayList<Float> probList=new ArrayList<Float>();
			for(int i=0;i<10;i++)
			{
				if(i%2==0)
				{
					int occurrence=countList.get(i)+countList.get(i+1);
					
					if(occurrence>0)
						probList.add(i, (countList.get(i)*1.00f)/(occurrence));
					else
						probList.add(i,0f);
				}
				else
				{
					int occurrence=countList.get(i)+countList.get(i-1);
					
					if(occurrence>0)
						probList.add(i, (countList.get(i)*1.00f)/(occurrence));
					else
						probList.add(i,0f);
				}
			}
			//System.out.println((String)e.getKey() + ":" +probList);
			probMap.put((String)e.getKey(),probList);
			
		}
		
		
		
	}
}
