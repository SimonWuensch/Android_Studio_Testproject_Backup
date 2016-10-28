package ssi.ssn.com.ssi_service.model.helper;

import org.apache.commons.io.IOUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLHelper {

    private String startTag;
    private List<String> searchedTags;
    private List<String> searchedAttributes;

    public XMLHelper(String startTag, List<String> searchedTags, List<String> searchedAttributes) {
        this.startTag = startTag;
        this.searchedTags = searchedTags;
        this.searchedAttributes = searchedAttributes;
    }

    public List<XMLObject> startSearching(String xmlString) {
        List<XMLObject> xmlObjects = new ArrayList<XMLObject>();
        XmlPullParser myParser = null;
        try {
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(IOUtils.toInputStream(xmlString), null);

            int event = myParser.getEventType();
            boolean startTagReached = false;
            boolean inStartTag = false;
            while (event != XmlPullParser.END_DOCUMENT) {
                String tagName = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equals(startTag)) {
                            startTagReached = true;
                            break;
                        }

                        if (!startTagReached) {
                            break;
                        } else {
                            inStartTag = true;
                        }

                        for (String searchedTag : searchedTags) {
                            if (tagName.contains(searchedTag)) {
                                XMLObject xmlObject = new XMLObject(tagName);
                                xmlObjects.add(xmlObject);
                                for (String searchedAttribute : searchedAttributes) {
                                    String attributeValue = myParser.getAttributeValue(null, searchedAttribute);
                                    if (attributeValue != null) {
                                        xmlObject.addAttribute(searchedAttribute, attributeValue);
                                    }
                                }
                            }
                        }

                    case XmlPullParser.END_TAG:
                        if (tagName.endsWith(startTag) && inStartTag) {
                            startTagReached = false;
                        }
                        break;
                }
                event = myParser.next();
            }
            return xmlObjects;
        } catch (Throwable t) {
            t.printStackTrace();
            throw new NullPointerException("No XML Objects in XML String found. StartTag: " + startTag + ", SearchedTags: " + searchedTags + ", SearchedAttributes: " + searchedAttributes + ".\nException: " + t.getMessage() + " - " + t.getStackTrace());
        }
    }


    public class XMLObject {

        private String tagName;
        private Map<String, String> attributes;

        XMLObject(String tagName) {
            this.tagName = tagName;
            this.attributes = new HashMap<>();
        }

        public String getTagName() {
            return tagName;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        void addAttribute(String attributeName, String attributeValue) {
            attributes.put(attributeName, attributeValue);
        }
    }


}
