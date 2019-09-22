package com.isf.usersupload.parser;

import com.isf.usersupload.exception.SameUsersFoundException;
import com.isf.usersupload.persistence.model.User;
import com.isf.usersupload.utill.UserKey;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Class with one public method for parse xml file to User list
 */
public class XmlParser {

    private final static Logger logger = Logger.getLogger(XmlParser.class);

    private final String XML_PATH = "C:\\XML\\";

    public List<User> parse(String fileName) throws SameUsersFoundException {
        File xmlFile = new File(XML_PATH + fileName + ".xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<User> users = null;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("user");
            users = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                users.add(getUser(nodeList.item(i)));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("Xml parse error: " + e);
        }

        checkForDuplicate(Objects.requireNonNull(users));
        logger.info("Xml parse successful - " + fileName + ".xml");
        return users;

    }


    private User getUser(Node node) {
        User user = new User();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            user.setDepCode(getTagValue("dep_code", element));
            user.setDepJob(getTagValue("dep_job", element));
            user.setDescription(getTagValue("description", element));
        }

        return user;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    /**
     * @param users - List of Users to check for On identical entities
     * @throws SameUsersFoundException if the Users list has repeated entries
     */
    private void checkForDuplicate(List<User> users) throws SameUsersFoundException {
        HashSet<UserKey> userKeys = new HashSet<>();
        for (User user : users) {
            userKeys.add(new UserKey(user.getDepCode(), user.getDepJob()));
        }
        if (!(userKeys.size() == users.size()))
            throw new SameUsersFoundException("Same Users found");
    }
}
