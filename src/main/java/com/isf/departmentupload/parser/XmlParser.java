package com.isf.departmentupload.parser;

import com.isf.departmentupload.exception.SameDepartmentsFoundException;
import com.isf.departmentupload.persistence.model.Department;
import com.isf.departmentupload.utill.DepartmentKey;
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

public class XmlParser {

    private final static Logger logger = Logger.getLogger(XmlParser.class);

    private final String XML_PATH = "C:\\XML\\";

    public List<Department> parse(String fileName) throws SameDepartmentsFoundException {
        File xmlFile = new File(XML_PATH + fileName + ".xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<Department> dep = null;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("department");
            dep = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                dep.add(getDepartment(nodeList.item(i)));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("Xml parse error: " + e);
        }

        checkForDuplicate(Objects.requireNonNull(dep));
        logger.info("Xml parse successful");
        return dep;

    }


    private Department getDepartment(Node node) {
        Department department = new Department();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            department.setDepCode(getTagValue("dep_code", element));
            department.setDepJob(getTagValue("dep_job", element));
            department.setDescription(getTagValue("description", element));
        }

        return department;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    private boolean checkForDuplicate(List<Department> departments) throws SameDepartmentsFoundException {
        HashSet<DepartmentKey> departmentKeys = new HashSet<>();
        for (Department department : departments) {
            departmentKeys.add(new DepartmentKey(department.getDepCode(), department.getDepJob()));
        }
        if (departmentKeys.size() == departments.size()) return true;
        else throw new SameDepartmentsFoundException("Same departments found");
    }
}