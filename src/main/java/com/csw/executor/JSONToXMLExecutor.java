package com.csw.executor;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.csw.converters.ConverterFactory;
import com.csw.converters.XMLJSONConverterI;

public class JSONToXMLExecutor {
	private String inputFileName;
	private String outputFileName;
	private Boolean isProjectFile;
	
	Logger logger = Logger.getLogger(JSONToXMLExecutor.class);
	
	public JSONToXMLExecutor(String inputFileName, String outputFileName, Boolean isProjectFile) {
		this.outputFileName = outputFileName;
		this.inputFileName = inputFileName;
		this.isProjectFile = isProjectFile;
	}
	
	public void execute() throws IOException {
    	File inputFile = null;
    	File outputFile = null;
    	
    	this.logger.info("Input File Name --> " + this.inputFileName);
    	this.logger.info("Output File Name --> " + this.outputFileName);
		XMLJSONConverterI xmljsonConverterI = ConverterFactory.createXMLJSONConverter();
        
        if (isProjectFile) {
        	ClassLoader classLoader = JSONToXMLExecutor.class.getClassLoader();
        	URL res1 = classLoader.getResource(inputFileName);
        	URL res2 = classLoader.getResource(outputFileName);
        	
        	if (res2 == null) {
            	this.logger.info("Invalid output file path");
            	throw new RuntimeException("Invalid output file path");
            } 
        	inputFile = new File(res1.getFile());
        	outputFile = new File(res2.getFile());
        } else {
        	inputFile = new File(inputFileName);
        	outputFile = new File(outputFileName);
        }
        
        if (inputFile == null || !inputFile.exists()) {
        	this.logger.info("Input file not found");
        	throw new RuntimeException("Input file not found");
        } 
        if (!outputFile.exists()) {
        	outputFile.getParentFile().mkdirs();
        	outputFile.createNewFile();
        }
        
        xmljsonConverterI.convertJSONtoXML(inputFile, outputFile);
	}

}
