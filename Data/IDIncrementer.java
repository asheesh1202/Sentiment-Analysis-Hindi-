
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;


class IDIncrementer
{

public static void main(String[] args)throws Exception
{

	if(args.length==0)
	{
		System.out.println("Exiting , usage: java IDIncrementer <input file name >");
		System.exit(0);
	}
	FileInputStream in = null;
      	FileOutputStream out = null;
	BufferedReader br=null;
	BufferedWriter bw=null;
      try {
         in = new FileInputStream(args[0]);
         out = new FileOutputStream("new_"+args[0]);
	 br= new BufferedReader(new InputStreamReader(in));
 	 bw = new BufferedWriter(new OutputStreamWriter(out));
	 String line = null;
	 int id=1;
	 while ((line = br.readLine()) != null)
	 {
	//remove blanks lines and  lines containing SYM 
		if(!(line.trim().equals("")))
		{
			//include SYM "," only
			if(line.contains("SYM")&&!line.contains("name=','"))
			{
				continue;
			}
			
			if(line.startsWith("<Sentence"))
			{
				bw.write("<Sentence id=\""+id+"\">\n");
				id++;
			}
		

			else
			{
				bw.write(line+"\n");
			}
			
		}
	}
        br.close();
	bw.close();
         
      }
	finally 
	{
		if(br !=null)
		{
			br.close();
		}
		if(bw !=null)
		{
			bw.close();
		}
         	if (in != null) 
		{
            		in.close();
 	        }
                if (out != null) 
		{
            		out.close();
         	}
      }


}


}
