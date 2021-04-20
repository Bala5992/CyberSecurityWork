package com.csw;

import java.io.IOException;

import com.csw.constant.AppConstant;
import com.csw.executor.JSONToXMLExecutor;

public class AppRunner {

    public static void main(String[] args) throws IOException {
    	boolean isProjectFile = true;
    	String inputFileName = "input" + AppConstant.INPUT_EXT;
    	String outputFileName = "output" + AppConstant.OUTPUT_EXT;
    	
    	if (args != null && args.length > 1) {
    		inputFileName = args[0];
    		outputFileName = args[1];
    		
    		if (null != outputFileName && outputFileName.indexOf(AppConstant.OUTPUT_EXT) == -1) {
    			outputFileName += "/output" + AppConstant.OUTPUT_EXT;
    		}
    		isProjectFile = false;
    	}
    	JSONToXMLExecutor executor = new JSONToXMLExecutor(inputFileName, outputFileName, isProjectFile);
    	executor.execute();
    }
}
