package com.hindi.sa;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Set;

public class ReadStopWords {

	public static void main(String[] args) {
		
		try
		{
		FileInputStream fin=new FileInputStream(args[0]+"/Stopwords/stopwords.ser");
		ObjectInputStream in=new ObjectInputStream(fin);
		
		Set<String> stopwords=(Set<String>)in.readObject();
		System.out.println("Stopwords deserialized");
		
		for(String s:stopwords)
		{
			System.out.println(s);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
