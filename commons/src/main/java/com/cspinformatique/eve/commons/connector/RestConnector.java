package com.cspinformatique.eve.commons.connector;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public abstract class RestConnector {
	private static XPath xpath = XPathFactory.newInstance().newXPath();
	
	protected Document executeRequest(String url, String resource, List<String> params){
		try{
			StringBuffer urlBuffer = new StringBuffer();
			
			urlBuffer.append(
				url + 
				resource
			);
			
			if(params.size() > 0){
				urlBuffer.append("?");
			}
			
			boolean first = true;
			for(String param : params){
				if(!first){
					urlBuffer.append("&");
				}
				urlBuffer.append(param);
				
				first = false;
			}
			
			System.out.println("Url : " + urlBuffer.toString());
			
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
				new InputSource(
					new DefaultHttpClient().execute(
						new HttpGet(urlBuffer.toString())
					).getEntity().getContent()
				)
			);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	public static NodeList getNodeList(String expr, Document document) {
	    try {
	        return (NodeList) xpath.compile(expr).evaluate(document, XPathConstants.NODESET);
	    } catch (XPathExpressionException XPathExpressionEx) {
	       throw new RuntimeException(XPathExpressionEx);
	    }
	}
	
	public static String extractField(String path, Document document){
		try{
			return xpath.evaluate(path, document);
		}catch(XPathExpressionException xPathExpressionEx){
			throw new RuntimeException(xPathExpressionEx);
		}
	}
}
