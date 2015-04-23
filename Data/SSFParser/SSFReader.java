import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class SSFReader {

	ArrayList<Sentence> parseFile(String path, boolean sentenceIncluded) {

		ArrayList<Sentence> parsedSentences = new ArrayList<Sentence>();
		BufferedReader br = null;

		try {

			String sCurrentLine;
			boolean sentenceRead=false;
			int id = 1;
			Sentence parsedSentence=new Sentence();
			
			ArrayList<Phrase> parsedPhrases = new ArrayList<Phrase>();
			ArrayList<Token> parsedTokens=new ArrayList<Token>();
			
			Phrase parsedPhrase=new Phrase();
			Token parsedToken=new Token();
			br = new BufferedReader(new FileReader(path));
			
			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine=sCurrentLine.trim();
				

				if (!(sCurrentLine.isEmpty())) {
					if(!sentenceRead && sentenceIncluded)
					{
						parsedSentence.setSentence(sCurrentLine);
						sentenceRead=true;
					}
					else if(sCurrentLine.startsWith("<Sentence")||sCurrentLine.contains("SYM"))
					{
						continue;
					}
					else if(sCurrentLine.contains("(("))
					{
						//extract phrase info
						//System.out.println("line was:"+sCurrentLine);
						try
						{
						String info1=sCurrentLine.substring(sCurrentLine.lastIndexOf("(")+1,sCurrentLine.indexOf("<"));
						info1=info1.trim();
						parsedPhrase.setPhraseTag(info1);
						String info2=sCurrentLine.substring(sCurrentLine.indexOf("'")+1,sCurrentLine.indexOf(","));
						parsedPhrase.setPhraseHead(info2);
						String info3=sCurrentLine.substring(sCurrentLine.lastIndexOf("=")+2,sCurrentLine.indexOf(">")-1);
						parsedPhrase.setHeadRoot(info3);
						}
						catch(Exception e)
						{
							String info1=sCurrentLine.substring(sCurrentLine.lastIndexOf("(")+1);
							info1=info1.trim();
							parsedPhrase.setPhraseTag(info1);
								
						}
					}
					else if(sCurrentLine.contains("))"))
					{
						//set this phrase
						parsedPhrase.setTokens(parsedTokens);
						parsedTokens=new ArrayList<Token>();
						parsedPhrases.add(parsedPhrase);
						parsedPhrase=new Phrase();
						
					}
					else if(sCurrentLine.startsWith("</Sentence>"))
					{
						sentenceRead=false;
						parsedSentence.setPhrases(parsedPhrases);
						parsedPhrases=new ArrayList<Phrase>();
						parsedSentence.setId(id);
						id++;
						parsedSentences.add(parsedSentence);
						parsedSentence=new Sentence();
					}
					else
					{
						//extract token info
						StringTokenizer tokenizer =new  StringTokenizer(sCurrentLine);
						tokenizer.nextToken();//line no
						parsedToken.setWord(tokenizer.nextToken());
						parsedToken.setTag(tokenizer.nextToken());
						tokenizer.nextToken();//<fs
						String info1=tokenizer.nextToken();
						String info2=info1.substring(info1.indexOf("'")+1,info1.indexOf(","));
						parsedToken.setRootWord(info2);
						
						parsedTokens.add(parsedToken);
						parsedToken=new Token();
					}

				}
				

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return parsedSentences;

	}

	public static void main(String args[]) {

		if (args.length != 2) {
			System.out
					.println("Exiting: Usage java SSFReader SSF-file-path sentenceIncluded?[y/n]  ");
			System.out
					.println("If SSF File includes sentence also along with parsed data run as : java SSFReader SSF-File-path y");
			System.exit(0);
		}
		SSFReader parser = new SSFReader();
		boolean sentenceIncluded = false;
		if ("y".equals(args[1])) {
			sentenceIncluded = true;
		}
		ArrayList<Sentence> parsedSentences = parser.parseFile(args[0],
				sentenceIncluded);
		
		  for (Sentence s : parsedSentences) {
			  System.out.println(s);
			  }
		 
	}
	
	
}
