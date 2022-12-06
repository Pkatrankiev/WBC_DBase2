package Aplication;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

final public class ResourceLoader {

	public static InputStream load(String path) {
		InputStream is = ResourceLoader.class.getResourceAsStream(path);	
		if(is==null){
			is = ResourceLoader.class.getResourceAsStream("/"+path);	
		}
		return is;
	}
	
	public static void appendToFile(Exception e) {
        try {
        	 final String time = "< " + extended.format( new Date() ) + " > ";
        	final String indexFile = time.substring(5,12).replace(".20","");
            FileWriter New_File = new FileWriter("Error-logDB"+indexFile+".txt", true);
            BufferedWriter Buff_File = new BufferedWriter(New_File);
            PrintWriter Print_File = new PrintWriter(Buff_File, true);

    		 Print_File.println("");
    		 Print_File.println(time);

            e.printStackTrace(Print_File);

        }
        catch (Exception ie) {
        	ResourceLoader.appendToFile(e);
            throw new RuntimeException("Cannot write the Exception to file", ie);
        }
   }

	private static final SimpleDateFormat   extended    = new SimpleDateFormat( "dd.MM.yyyy (HH:mm:ss) zz" );
	
	
}
