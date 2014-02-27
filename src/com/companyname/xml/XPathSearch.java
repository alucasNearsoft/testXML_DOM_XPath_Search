/*
 * Locating Chicago contacts with the XPath API
 */
package com.companyname.xml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

class XPathSearch {
	public static void main(String[] args) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder(); // building the DOM tree
			Document doc = db.parse("contacts.xml");       // parsing contacts.xml
			XPathFactory xpf = XPathFactory.newInstance(); // instantiates XPathFactory
			XPath xp = xpf.newXPath(); // to create and XPath object
			XPathExpression xpe;
			xpe = xp.compile("//contact[city='Chicago']/name/text()"); // compiles an XPath expression
																	   // returning that compiled expression as an instance
																	   // of a classs that implements the XPathExpression interface.
			Object result = xpe.evaluate(doc, XPathConstants.NODESET); // evaluate the expression. The first argument is the context node for the
																	   // expression, which happens to be a Document instance in the example.
																	   // The second argument specifies the
																	   // kind of object returned by evaluate() and is set to XPathConstants.NODESET, a qualified name for the
																	   // XPath 1.0 nodeset type, which is implemented via DOM’s NodeList interface
			NodeList nl = (NodeList) result; // casting the result to NodeList interface
			for (int i = 0; i < nl.getLength(); i++) // traverse the NodeList
				System.out.println(nl.item(i).getNodeValue()); // for each item call getNodeValue to return the node's value, which is subsequently output.
		} catch (IOException ioe) {
			System.err.println("IOE: " + ioe);
		} catch (SAXException saxe) {
			System.err.println("SAXE: " + saxe);
		} catch (FactoryConfigurationError fce) {
			System.err.println("FCE: " + fce);
		} catch (ParserConfigurationException pce) {
			System.err.println("PCE: " + pce);
		} catch (XPathException xpe) {
			System.err.println("XPE: " + xpe);
		}
	}
}