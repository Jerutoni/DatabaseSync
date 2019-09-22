package com.isf.usersupload.parser;

import com.isf.usersupload.persistence.model.User;
import com.isf.usersupload.service.UserService;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * Class with one public method for creating a xml file based on data from a database
 */
public class XmlCreator {
    private final static Logger logger = Logger.getLogger(XmlCreator.class);
    private final String xmlFilePath = "C:\\XML\\";

    private UserService userService = new UserService();

    /**
     * @param fileName - The name of the xml file to be created
     */
    public void createXmlFile(String fileName) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            Element root = document.createElement("users");
            document.appendChild(root);

            List<User> users = userService.getUsers();

            for (User dep : users) {

                Element user = document.createElement("user");
                root.appendChild(user);

                Element depCode = document.createElement("dep_code");
                depCode.appendChild(document.createTextNode(dep.getDepCode()));
                user.appendChild(depCode);

                Element depJob = document.createElement("dep_job");
                depJob.appendChild(document.createTextNode(dep.getDepJob()));
                user.appendChild(depJob);

                Element depDescription = document.createElement("description");
                depDescription.appendChild(document.createTextNode(dep.getDescription()));
                user.appendChild(depDescription);

            }
            createFile(document, fileName);
            logger.info("Xml file created - " + fileName + ".xml");

        } catch (ParserConfigurationException | TransformerException e) {
            logger.error("Create xml file exception: " + e);
        }

    }

    private void createFile(Document document, String fileName) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath + fileName + ".xml"));
        Objects.requireNonNull(transformer).transform(domSource, streamResult);

    }
}
