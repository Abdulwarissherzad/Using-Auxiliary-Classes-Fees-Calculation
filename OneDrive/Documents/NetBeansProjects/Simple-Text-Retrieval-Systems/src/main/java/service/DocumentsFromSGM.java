package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Document;
import model.DocumentListModel;

/*
 * DocumentsFromSGM.java
 *
 * @author  Abdul Waris SHERZAD
 * 
 */

public class DocumentsFromSGM {
	
        static Logger logger = Logger.getLogger(DocumentsFromSGM.class.getName());
        private static String[] META_CHARS = { "&", "<", ">", "\"", "'" };
        private static String[] META_CHARS_SERIALIZATIONS = { "&", "<",">", "\"", "'" };
        private static String parsedDir = "";
        static public JList<Document> listDocument = new JList<>();
        static public DocumentListModel<Document> listModel;
        static public java.util.List<Document> document = new ArrayList<>();
        public static ArrayList<String>    errList = null;
	
		public static JList<Document> readExtractAndParse(String reutersDir, String parsedDir) {
			
			logger.info("About to extract and parse [.sgm] files from directory " + reutersDir);
			DocumentsFromSGM.parsedDir = parsedDir;
			List<String> reutersList   = readReuters(reutersDir);
			errList = new ArrayList<String>();
			logger.info("Processing.... Please Wait!!");
			if(!reutersList.isEmpty()) {
				reutersList.forEach(filePath -> { 
					File file = new File(filePath);
					if(file.exists()) {
						try {
							Thread th1 = new Thread(() -> {
								try {
									extractAndParse(file);
								} catch(Exception e) {
									try {
										errList.add(e.getMessage());
									} catch (Exception ee) {ee.printStackTrace();}
								}
							});
							th1.start();
							th1.join();
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			
			logger.info("===========================REUTERS-DIR-PROCESS-DONE=========================");		
			logger.info("About to retrieve DATE, TOPICS, TITLE and BODY of Documents in [.xml] format from directory " + parsedDir);
			List<String> parsedList = readParsed(parsedDir);
			if(!parsedList.isEmpty()) {
				listModel = new DocumentListModel<Document>(document);
				listDocument.setModel(listModel);
				parsedList.forEach(filePath -> { 
					File file = new File(filePath);
					if(file.exists()) {
						try {
							Thread th = new Thread(() -> {
								try {
									retrieve(file);
								} catch(Exception e) {
									try {
										errList.add(e.getMessage());
									} catch (Exception ee) {ee.printStackTrace();}
								}
							});
							th.start();
							th.join();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			
			logger.info("===========================PARSED-DIR-PROCESS-DONE=========================");		
			/*
			new Thread(() -> {
				deleteOutputFiles();
			}).start();
			*/
			
			return listDocument;
		}
		
		private static List<String> readReuters(String reutersDir) {
			
			List<String> result = null;
			try (Stream<Path> walk = Files.walk(Paths.get(reutersDir))) {
				result = walk.map(x -> x.toString())
					.filter(f -> f.endsWith(".sgm") && !f.contains("-021") && Files.isRegularFile(Paths.get(f)))
					.collect(Collectors.toList());
						
				result.forEach(System.out::println);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		private static List<String> readParsed(String parsedDir) {
			
			List<String> result = null;
			try (Stream<Path> walk = Files.walk(Paths.get(parsedDir))) {
				result = walk.map(x -> x.toString())
					.filter(f -> f.endsWith(".xml") && Files.isRegularFile(Paths.get(f)))
					.collect(Collectors.toList());	
						
				// result.forEach(System.out::println);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		protected static void extractAndParse(File sgmFile) {
			
			try {
				BufferedReader reader = new BufferedReader(new FileReader(sgmFile));
				StringBuilder buffer  = new StringBuilder(1024);
				boolean appendXml = false;
				String line   = null;
				int docNumber = 0;
				while ((line  = reader.readLine()) != null) {
					if (line.indexOf("</REUTERS") == -1) {
						
						if (line.contains("<!DOCTYPE lewis SYSTEM \"lewis.dtd\">")) {
							line = "<?xml version=\"1.1\"?>";
						} else if (appendXml) {
							line = "<?xml version=\"1.1\"?>\n" + line;
							appendXml = false;
						}
						buffer.append(line).append('\n');
					} else {
	
						buffer.append(line);
						String out = buffer.toString();
						for (int i = 0; i < META_CHARS_SERIALIZATIONS.length; i++) {
							out = out.replaceAll(META_CHARS_SERIALIZATIONS[i], META_CHARS[i]);
						}
						String fileName = sgmFile.getName().substring(0, sgmFile.getName().lastIndexOf('.')) + "-sgm-" + (docNumber++) + ".xml";
						File outFile = new File(parsedDir, fileName);
						FileWriter writer = new FileWriter(outFile);
						writer.write(out);
						writer.close();
						buffer.setLength(0);
						appendXml = true;
					}
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				try {
					errList.add(e.getMessage());
				} catch (Exception ee) {ee.printStackTrace();}
			}
		}
		
		private static void retrieve(File file) {
			
			try {
				logger.info("File received :" + file);
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();  
				org.w3c.dom.Document doc = db.parse(file);  
				doc.getDocumentElement().normalize();  
				NodeList nodeList = doc.getElementsByTagName("REUTERS"); 
				for (int itr = 0; itr < nodeList.getLength(); itr++) {
					Node node = nodeList.item(itr);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) node;
						Document document = new Document();
						
						document.setDate((eElement.getElementsByTagName("DATE").item(0)==null)?"NA":eElement.getElementsByTagName("DATE").item(0).getTextContent());
						document.setTopic((eElement.getElementsByTagName("TOPICS").item(0)==null)?"NA":eElement.getElementsByTagName("TOPICS").item(0).getTextContent());
						document.setTitle((eElement.getElementsByTagName("TITLE").item(0)==null)?"NA":eElement.getElementsByTagName("TITLE").item(0).getTextContent());
						document.setBody((eElement.getElementsByTagName("BODY").item(0)==null)?"NA":eElement.getElementsByTagName("BODY").item(0).getTextContent());
						
						// logger.info("DATE : " + document.getDate() + "\tTOPIC : " + document.getTopic() + "\tTITLE : " + document.getTitle() + "\tBODY : " + document.getBody());
						
						listModel.addDocument(document);
						
						// file.delete();
					}
				}
			} catch (Exception e) {
				try {
					e.printStackTrace();
					errList.add(e.getMessage());
				} catch (Exception ee) {ee.printStackTrace();}
			}
		}
		
		public static void deleteOutputFiles() {
			logger.info("Deleting all files in " + parsedDir);
			File outDir = new File(parsedDir);
		    for (File f : outDir.listFiles()) {
		      f.delete();
		    }
		}
}
