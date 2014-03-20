import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLtoMode extends DefaultHandler {

	public boolean isfirstprogram = true;
	
	public static String languagedirpath = "";
	
	public static String outputdirpath = "";
	
	public StringBuffer sbcommand = new StringBuffer();
	
	public 	File modefile = null;
	
	public static void main(String[] args) {
		
		if(args.length<2) {
			System.out.println("Usage: java ModeConversion <Path of the language pair directory> <Path of the output dictionary for *.modes>");
			System.out.println("e.g. java ModeConversion apertium-hin apertium-hin/modes_test");
			System.exit(1);
		}
		File languagedir = new File(args[0]);
	    try {
			languagedirpath = languagedir.getCanonicalPath();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 outputdirpath = args[1];
		File outputdir = new File(outputdirpath);
		if(!(outputdir.exists())) {
			outputdir.mkdir();
		}
		try {
			runParser(languagedirpath+File.separator+"modes.xml");
		} catch (Exception e) {

		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("mode")) {
			String modefilename = attributes.getValue("name")+".mode";
			 modefile = new File(outputdirpath+File.separator+modefilename);
			try {
				modefile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(qName.equals("program")) {
			if(!isfirstprogram) {
				sbcommand.append("|");
			} else {
				isfirstprogram = false;
			}
			String programname = attributes.getValue("name");
			sbcommand.append(programname);
		} else if(qName.equals("file")) {
			String filename = attributes.getValue("name");
			sbcommand.append(" ");
			sbcommand.append(languagedirpath);
			sbcommand.append(File.separator);
			sbcommand.append(filename);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		}



	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("mode")) {
			try {
				System.out.println("File Created "+modefile.getCanonicalPath());
				BufferedWriter bw = new BufferedWriter(new FileWriter(modefile));
				bw.write(sbcommand.toString());
				bw.close();
				sbcommand.setLength(0);
				isfirstprogram = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This is method to run the SAX parser on the xml file
	 * @param fileurl Path of the xml file
	 */

	public static void runParser(String fileurl) {
		DefaultHandler def = new XMLtoMode();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		File f = new File(fileurl);
		SAXParser saxParser = null;
		try {
			saxParser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			saxParser.parse(f, def);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}