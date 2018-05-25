package io.github.aquerr.json2xml;

import org.json.JSONObject;

public class ParserThroughString
{
    public String parseXML(String jsonObject)
    {
        boolean rootElementExists = true;
        String xml = "";

        //No root element
        if (jsonObject.charAt(0) == '{')
        {
            rootElementExists = false;
            xml += "<Kibana ";
        }

        boolean insideTag = false;
        boolean isTagList = false;
        boolean isAttribute = false;
        boolean isAttributeValue = false;
        boolean nextTag = false;

        boolean firstAttributeApostrophe = false;
        boolean lastAttributeApostrophe = false;

        for (Character character : jsonObject.toCharArray())
        {
            if (character == '{')
            {
                insideTag = true;
                isAttribute = true;
            }
            else if (character == '\"')
            {
                if (isAttribute)
                {

                }
            }
            else if(character == ':')
            {

            }
            else
            {
                xml += character;
            }
        }

        if (!rootElementExists)
        {
            xml += "</Kibana>";
        }

       // xml += getTag(jsonObject);

        return xml;
    }

    public String getTag(JSONObject jsonObject)
    {
        String tag = "";

        return tag;
    }
}
