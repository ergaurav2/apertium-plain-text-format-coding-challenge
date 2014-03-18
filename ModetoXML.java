import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * This class is used to generate the modes.xml from all the *.modes file
 * @author gaurav
 *
 */
public class ModetoXML {

	/**
	 * @param args argument 0 will be the <Path of the language pair directory> and 
	 * 1 will be the <Path of the output dictionary for modes.xml>
	 * e.g. java ModeConversion apertium-hin test
	 */
	public static void main(String[] args) {
		if(args.length<2) {
			System.out.println("Usage: java ModeConversion <Path of the language pair directory> <Path of the output dictionary for modes.xml>");
			System.out.println("e.g. java ModeConversion apertium-hin apertium-hin/testmodes");
			System.exit(1);
		}
		String inputdirpath = args[0];
		String outputdirpath = args[1];
		File outputfile = new File(outputdirpath+File.separator+"modes.xml");
		File outputdir = new File(outputdirpath);
		if(!(outputdir.exists())) {
			outputdir.mkdir();
		}
		try {
			outputfile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		 String outputEncoding = "UTF-8";
		    OutputStreamWriter bwout = null;
		try {
			   FileOutputStream fos = new FileOutputStream(outputfile);
			 bwout = new OutputStreamWriter(fos, outputEncoding);
			 bwout.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			 bwout.write("<modes>\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		inputdirpath = inputdirpath + File.separator + "modes";
		File directory = new File(inputdirpath);
		try {
			System.out.println("Input Directory"+directory.getCanonicalPath());
			System.out.println("Output File "+outputfile.getCanonicalPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String[] files = directory.list();
		int nooffiles = files.length;
		for(int i=0;i<nooffiles;i++) {
			String filename = files[i];
			if(filename.endsWith(".mode")) {
			//	System.out.println("filename "+filename);
				int extesnionstart = filename.indexOf('.');
				String modename = filename.substring(0,extesnionstart);
				File file = new File(inputdirpath+File.separator+File.separator+filename);
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					boolean flag = true;
					String command = null;
					while(flag) {
						String line = br.readLine();
						if(line == null) {
							flag = false;
							continue;
						}
						if(line.trim().equals("")) {
							continue;
						} else {
							command = line;
							flag = false;
						}
					}
					if (command != null) {
					String xmlFragment	= convertmodetoXML(modename, command);
					bwout.write(xmlFragment);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		 try {
			bwout.write("</modes>");
			bwout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method convert each modecommand into the xml format
	 * @param modecommand The string form of the mode command
	 */
	public static String convertmodetoXML(String modename, String modecommand) {
		String commands[] = modecommand.split("\\|");
		int noofcommands = commands.length;
		StringBuffer sbxml = new StringBuffer();
		sbxml.append("<mode name=\""+modename+"\">\n");
		sbxml.append("<pipeline>\n");
		for(int i=0;i<noofcommands;i++) {
			String command = commands[i].trim();
			int filepathstart = command.indexOf(File.separatorChar);
			if(filepathstart!=-1) {
				String programname = command.substring(0,filepathstart).trim();
				sbxml.append("<program name=\""+programname+"\">\n");
				String filespath = 	command.substring(filepathstart);
				String filepaths[] = filespath.replaceAll("\\s+"," ").split(" ");
				int nooffilepaths = filepaths.length;
				for(int j=0;j<nooffilepaths;j++) {
					String filepath = filepaths[j].trim();
				int filenamestart = filepath.lastIndexOf(File.separatorChar);
				String filename = filepath.substring(filenamestart+1);
				sbxml.append("<file name=\""+filename+"\"/>\n");
			}
				sbxml.append("</program>\n");
		}	else {
			String programname = command.trim();
			sbxml.append("<program name=\""+programname+"\"/>\n");
		}
	}
		sbxml.append("</pipeline>\n");
		sbxml.append("</mode>\n");
		return sbxml.toString();
	}  
}
