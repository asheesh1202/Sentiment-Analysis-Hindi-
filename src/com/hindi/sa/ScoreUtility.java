package com.hindi.sa;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hindi.ssfreader.Token;

public class ScoreUtility {
	
	
	Map<String,Value> scoreMap1=null;
	Map<String,Value> scoreMap2=null;
	
public Value getScore1(String path, String word){
		
		Value score=null;
		path += "/Polarity Scores";
		
		try {
			
		
			//search map1.
			if(scoreMap1==null)
			{
			
			FileInputStream fin = new FileInputStream(path + "/Scores/map1.ser");
			ObjectInputStream in = new ObjectInputStream(fin);

			scoreMap1 = (HashMap<String, Value>) in.readObject();
				
			}
			if(scoreMap1.containsKey(word))
			{
				return scoreMap1.get(word);
			}
			
			
			//else search map2
			
			if(scoreMap2==null)
			{
			
			FileInputStream fin = new FileInputStream(path + "/Scores/map2.ser");
			ObjectInputStream in = new ObjectInputStream(fin);

			scoreMap2 = (HashMap<String, Value>) in.readObject();
				
			}
			if(scoreMap2.containsKey(word))
			{
				return scoreMap2.get(word);
			}
			
			//else search score of rootword instead.
			/*String rootWord=word.getRootWord();
			if(scoreMap1.containsKey(rootWord))
			{
				return scoreMap1.get(rootWord);
			}
			
			if(scoreMap2.containsKey(rootWord))
			{
				return scoreMap2.get(rootWord);
			}
			*/
			
			
			//return some score;
			Value v =new Value();
			v.set(0.0f,0.0f);
			return v;
			
		

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return score;
	}
	
	public Value getScore(String polarityScoreFilePath, Token word){
		
		Value score=null;
		
		
		try {
			
		
			//search map1.
			if(scoreMap1==null)
			{
			
			FileInputStream fin = new FileInputStream(polarityScoreFilePath+"/Scores/map1.ser");
			ObjectInputStream in = new ObjectInputStream(fin);

			scoreMap1 = (HashMap<String, Value>) in.readObject();
				
			}
			if(scoreMap1.containsKey(word.getWord()))
			{
				return scoreMap1.get(word.getWord());
			}
			
			
			//else search map2
			
			if(scoreMap2==null)
			{
			
			FileInputStream fin = new FileInputStream(polarityScoreFilePath+"/Scores/map2.ser");
			ObjectInputStream in = new ObjectInputStream(fin);

			scoreMap2 = (HashMap<String, Value>) in.readObject();
				
			}
			if(scoreMap2.containsKey(word.getWord()))
			{
				return scoreMap2.get(word.getWord());
			}
			
			//else search score of rootword instead.
			String rootWord=word.getRootWord();
			if(scoreMap1.containsKey(rootWord))
			{
				return scoreMap1.get(rootWord);
			}
			
			if(scoreMap2.containsKey(rootWord))
			{
				return scoreMap2.get(rootWord);
			}
			
			
			
			//return some score;
			Value v =new Value();
			v.set(0.4f,0.4f);
			return v;
			
		

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return score;
	}
}
