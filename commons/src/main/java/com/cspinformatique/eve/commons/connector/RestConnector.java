package com.cspinformatique.eve.commons.connector;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public abstract class RestConnector {
	protected static XPath XPATH = XPathFactory.newInstance().newXPath();
	
	public Document executeRequest(String url, String resource){
		return this.executeRequest(url, resource, new ArrayList<String>());
	}
	
	public Document executeRequest(String url, String resource, List<String> params){
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
	
	public static Node getNode(String expr, Object document) {
	    try {
	        return (Node) XPATH.compile(expr).evaluate(document, XPathConstants.NODE);
	    } catch (XPathExpressionException XPathExpressionEx) {
	       throw new RuntimeException(XPathExpressionEx);
	    }
	}
	
	public static NodeList getNodeList(String expr, Object document) {
	    try {
	        return (NodeList) XPATH.compile(expr).evaluate(document, XPathConstants.NODESET);
	    } catch (XPathExpressionException XPathExpressionEx) {
	       throw new RuntimeException(XPathExpressionEx);
	    }
	}
	
	public static String extractField(String path, Document document){
		try{
			String result = XPATH.evaluate(path, document);
			
			if(result.equals("")){
				throw new NoResultException();
			}
			
			return result;
		}catch(XPathExpressionException xPathExpressionEx){
			throw new RuntimeException(xPathExpressionEx);
		}
	}
}
