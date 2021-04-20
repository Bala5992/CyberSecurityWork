package com.csw.converters.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.JSONArray;
import org.json.JSONObject;

import com.csw.converters.XMLJSONConverterI;

public class XMLJSONConverterIImpl implements XMLJSONConverterI {

	Logger logger = Logger.getLogger(XMLJSONConverterIImpl.class);
			
    @Override
    public void convertJSONtoXML(File json, File xml) throws IOException {
    	
    	this.logger.info("Conversion started...");
    	try (FileInputStream fileInputStream = new FileInputStream(json);
    			FileOutputStream outputStream = new FileOutputStream(xml);) {    		
    		int len = fileInputStream.available();
    		byte[] jsonArr = new byte[len];
    		fileInputStream.read(jsonArr, 0, len);
    		
    		JSONObject jsonObject = new JSONObject(new String(jsonArr));
    		Document doc = new Document();

            Element temp = processObject(null, jsonObject);
            doc.addContent(temp);

            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setOmitDeclaration(true));
            try {
                outputter.output(doc, outputStream);
                this.logger.info("File write completed successfully");
            } catch (Exception e) {
            	this.logger.info("Unable to complete the writing process -> " + e.getMessage());
                throw new RuntimeException("Unable to complete the writing process");
            }
    	} catch (Exception e) {
        	this.logger.info("Unable to read input/output file -> " + e.getMessage());
        	throw new RuntimeException("Unable to read input/output file");
        }
    	this.logger.info("Conversion completed...");
    }

	private Element processObject(String parentKey, JSONObject jsonObject) {
		Element temp = new Element("object");
        
        List<Element> elements = new ArrayList<>();
		for (String key : jsonObject.keySet()) {
        	Object object = jsonObject.get(key); 
        	Element element = identify(key, object);
        	elements.add(element);
        }
		if (parentKey != null) {
			temp.setAttribute("name", parentKey);
		}
		temp.addContent(elements);
		
		return temp;
	}

	private Element identify(String key, Object object) {
		String str = null;
        Integer integer = 0;
        Float flt = 0.0F;
        Double doubl = 0.0;
        Long lng = 0L;
        JSONArray array = null;
        JSONObject jsonObj = null;
        Boolean bool = false;
        Element temp = null;
		
		if (object instanceof JSONObject) {
    		jsonObj = (JSONObject) object;
    		temp = processObject(key, jsonObj);
    	} else if (object instanceof JSONArray) {
    		array = (JSONArray) object;
    		temp = new Element("array");
    		setValue(temp, key, null);
    		temp.setContent(processArray(key, array));
    	} else if (object instanceof String) {
    		str = (String) object;
    		temp = new Element("string");
    		setValue(temp, key, str);
    	} else if (object instanceof Integer) {
    		integer = (Integer) object;
    		temp = new Element("number");
    		setValue(temp, key, integer.toString());
    	} else if (object instanceof Float) {
    		flt = (Float) object;
    		temp = new Element("number");
    		setValue(temp, key, Float.toString(flt));
    	} else if (object instanceof Long) {
    		lng = (Long) object;
    		temp = new Element("number");
    		setValue(temp, key, Long.toString(lng));
    	} else if (object instanceof Double) {
    		doubl = (Double) object;
    		temp = new Element("number");
    		setValue(temp, key, Double.toString(doubl));
    	} else if (object instanceof Boolean) {
    		bool = (Boolean) object;
    		temp = new Element("boolean");
    		setValue(temp, key, Boolean.toString(bool));
    	} else {
    		temp = new Element("null");
    		setValue(temp, key, null);
    	}
		
		return temp;
	}

	private void setValue(Element temp, String key, String str) {
		if (key != null)
			temp.setAttribute("name", key);
		if (str != null)
			temp.addContent(str);
	}

	private List<Element> processArray(String key, JSONArray array) {
		List<Element> elements = new ArrayList<Element>();
		for (int i = 0;i < array.length();i++) {
			Object object = array.get(i);
			Element temp = identify(null, object);
			elements.add(temp);
		}
		return elements;
	}
}
