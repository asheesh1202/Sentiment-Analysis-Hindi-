package com.hindi.sa;

import java.util.ArrayList;
import java.util.TreeMap;

import com.hindi.ssfreader.Phrase;
import com.hindi.ssfreader.Sentence;
import com.hindi.ssfreader.Token;

public class UnigramCreator 
{
	TreeMap<String,FeatureValue> unigramMap;
	static int featuresCount=1;
	public TreeMap<String,FeatureValue> unigramMapCreator(ArrayList<Sentence> arrayList)
	{	
		
		
		unigramMap=new TreeMap<String,FeatureValue>();
		for(Sentence s:arrayList)
		{
			
			ArrayList<Phrase> phrases=s.getPhrases();
			for(Phrase p: s.getPhrases())
			{
				ArrayList<Token> tokens=p.getTokens();
				for(Token t: tokens)
				{
					if(!unigramMap.containsKey(t.getWord()))
					{
						FeatureValue f=new FeatureValue();
						f.setId(featuresCount++);
						f.setToken(t);
						unigramMap.put(t.getWord(),f);
					}	
					
				}
			}
			
		}
		
		return unigramMap;
	}
}
