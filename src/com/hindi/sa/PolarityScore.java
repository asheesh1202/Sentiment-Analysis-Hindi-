package com.hindi.sa;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;



public class PolarityScore 
{
	public Map<String, Value> polarityScoreMap1 = new HashMap<String, Value>();
	public Map<String, Value> polarityScoreMap2 = new HashMap<String, Value>();
	
	//populates the map.
	public void polarityScoreMapCreator(String path)
	{
		polarityScoreMapCreator1(path);
		polarityScoreMapCreator2(path);
		String scoreSerDeFilePath1,scoreSerDeFilePath2;
		
		scoreSerDeFilePath1 = path + "/Scores/map1.ser";
		scoreSerDeFilePath2 = path + "/Scores/map2.ser";
		try
		{
			
			FileOutputStream fout = new FileOutputStream(scoreSerDeFilePath1);
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(polarityScoreMap1);
			System.out.println("map1 serialized");
			
			fout = new FileOutputStream(scoreSerDeFilePath2);
			out = new ObjectOutputStream(fout);
			out.writeObject(polarityScoreMap2);
			System.out.println("map2 serialized");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		
		
	}
	
	
	//populates map from subjective lexicon
	public void polarityScoreMapCreator1(String path)
	{
		String line;
		
		path += "/SubjectiveLexicons/HSL_V1.0_11March2012.txt";
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(path));
			while((line = br.readLine()) != null)
			{
				StringTokenizer stringTokenizer = new StringTokenizer(line);
				String str1 = stringTokenizer.nextToken(); // single word
				String str2 = stringTokenizer.nextToken(); // positive score
				String str3 = stringTokenizer.nextToken(); // negative score
				if(!polarityScoreMap1.containsKey(str1))
				{
					Value obj = new Value();
					obj.set(Float.parseFloat(str2), Float.parseFloat(str3));
					polarityScoreMap1.put(str1, obj);
				}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//populates map from hindi sentiwordnet
	public void polarityScoreMapCreator2(String path)
	{
		String line;
		
		path += "/IIT Bombay Hindi Sentiwordnet/HSWN_WN.txt";
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(path));
			while((line = br.readLine()) != null)
			{
				StringTokenizer stringTokenizer = new StringTokenizer(line);
				stringTokenizer.nextToken(); // leaving POS tag
				stringTokenizer.nextToken(); // leaving synset ID
				String str1 = stringTokenizer.nextToken(); // positive score
				String str2 = stringTokenizer.nextToken(); // negative score
				String str3 = stringTokenizer.nextToken(); // word list
				String words[] = str3.split(",");
				for(int i = 0 ; i < words.length ; i++)
				{
					if(!polarityScoreMap2.containsKey(words[i]))
					{
						Value obj = new Value();
						obj.set(Float.parseFloat(str1), Float.parseFloat(str2));
						polarityScoreMap2.put(words[i], obj);
					}
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}
