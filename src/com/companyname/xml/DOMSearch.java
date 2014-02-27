/*
 * Locating Chicago contacts with the DOM API
 */

package com.companyname.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class DOMSearch
{
	public static void main(String[] args)
	{
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();           // building the DOM tree
			Document doc = db.parse("contacts.xml");                 // parsing
			List<String> contactNames = new ArrayList<String>();
			NodeList contacts = doc.getElementsByTagName("contact"); // return a NodeList of contact element nodes
			for (int i = 0; i < contacts.getLength(); i++)           // For each member of this list ...
			{
				Element contact = (Element) contacts.item(i);            // extracts the contact element node
				NodeList cities = contact.getElementsByTagName("city");  // and uses this node with getElementsByTagName() to
																		 // return a NodeList of the contact element node’s city element nodes.
				boolean chicago = false;
				for (int j = 0; j < cities.getLength(); j++)             // For each member of the cities list ...
				{
					Element city = (Element) cities.item(j);             // extracts the city element node
					NodeList children = city.getChildNodes();            // return a NodeList of the city element node’s child nodes
					StringBuilder sb = new StringBuilder();
					for (int k = 0; k < children.getLength(); k++)
					{
						Node child = children.item(k);
						if (child.getNodeType() == Node.TEXT_NODE)       // If the child’s node type indicates that it is a text node 
							sb.append(child.getNodeValue());             // the child node’s value (obtained via getNodeValue()) is stored in a string builder
					}
					if (sb.toString().equals("Chicago"))                 // If the builder’s contents indicate that Chicago has been found
					{
						chicago = true;                					 // the chicago flag is set to true                    
						break;											 // execution leaves the cities loop
					}
				}
				if (chicago)  // If the chicago flag is set when the cities loop exits
				{
					NodeList names = contact.getElementsByTagName("name"); // the current contact element node’s getElementsByTagName() method is called
																		   // to return a NodeList of the contact element node’s name element nodes
					contactNames.add(names.item(0).getFirstChild().getNodeValue()); // extract the first item from this list, call getFirstChild() on this
																					// item to return the text node (I assume that only text appears between 
																					// <name> and </name>), and call getNodeValue() on the text node to obtain
																					// its value, which is then added to the contactNames list.
				}
			} // for
		for (String contactName: contactNames)
			System.out.println(contactName);
		}
		catch (IOException ioe)
		{
			System.err.println("IOE: "+ioe);
		}
		catch (SAXException saxe)
		{
			System.err.println("SAXE: "+saxe);
		}
		catch (FactoryConfigurationError fce)
		{
			System.err.println("FCE: "+fce);
		}
		catch (ParserConfigurationException pce)
		{
			System.err.println("PCE: "+pce);
		}
	}
}