package com.example.aladinne.myapplicationandoidstudio.handler;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;

import javax.xml.transform.TransformerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Aladinne on 20/02/2016.
 */
public class GetXmlFromUrl {

    Document doc = null;


    public Document  getDoc(String url )
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            doc = db.parse(new URL(url).openStream());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer xform = null;
        try {
            xform = factory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

// that’s the default xform; use a stylesheet to get a real one
      /*  try {
            xform.transform(new DOMSource(doc), new StreamResult(System.out));
        } catch (TransformerException e) {
            e.printStackTrace();
        }*/


        return doc  ;



    }


}
