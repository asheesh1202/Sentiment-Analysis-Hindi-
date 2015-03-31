package com.hindi.sa;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

public class stopWordHandler
{
	
	public Set<String> stopWordSet;
	String stopWordFilePath, stopWordSerDeFilePath;
	
	public void createStopWordSet(String path)
	{
		String line;
		
		stopWordSet = new HashSet<String>();
		stopWordFilePath = path + "/Stopwords/stopwords_hi.txt";
		stopWordSerDeFilePath = path + "/Stopwords/stopwords.ser";
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(stopWordFilePath));
			while((line = br.readLine()) != null)
				stopWordSet.add(line);
			FileOutputStream fout = new FileOutputStream(stopWordSerDeFilePath);
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(stopWordSet);
			System.out.println("Stopwords serialized");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
}
