package io.github.aquerr.json2xml;

import org.json.JSONObject;

import java.util.*;

public class ParserThroughMap
{
    public String parseXML(JSONObject object)
    {
        Map<String, Object> jsonMap = sortMap(object.toMap());

        boolean hasRootObject = true;
        String resultXML = "<Kibana" + " ";

        for (Map.Entry<String, Object> elementEntry : jsonMap.entrySet())
        {
            if (elementEntry.getValue() instanceof Map)
            {
                resultXML += ">";

                resultXML += getTag(elementEntry.getKey(), (Map<String, Object>)elementEntry.getValue());
            }
            else if (elementEntry.getValue() instanceof List)
            {
                resultXML += ">";
                List<Map<String, Object>> list = (List<Map<String, Object>>) elementEntry.getValue();

                for (Map<String, Object> hashMap : list)
                {
                    hashMap = sortMap(hashMap);

                    resultXML += getTag(elementEntry.getKey(), hashMap);
                }
            }
            else
            {
                resultXML += elementEntry.getKey() + "=\"" + elementEntry.getValue() + "\" ";
            }
        }
        return resultXML;
    }

    private static String getTag(String tagName, Map<String, Object> tagMap)
    {
        Boolean hasOnlyAttributes = true;
        tagMap = sortMap(tagMap);
        String tagContent = "<" + tagName + " ";

        for (Map.Entry<String, Object> elementEntry : tagMap.entrySet())
        {
            if (elementEntry.getValue() instanceof String)
            {
                tagContent += elementEntry.getKey() + "=\"" + elementEntry.getValue() + "\" ";
            }
            else if (elementEntry.getValue() instanceof Map)
            {
                hasOnlyAttributes = false;
                if (!(tagContent.charAt(tagContent.length() - 1) == '>'))
                {
                    tagContent += ">";
                }
                //tagClosed = true;
                tagContent += getTag(elementEntry.getKey(), (Map<String, Object>)elementEntry.getValue());
            }
            else if (elementEntry.getValue() instanceof List)
            {
                hasOnlyAttributes = false;
                if (!(tagContent.charAt(tagContent.length() - 1) == '>'))
                {
                    tagContent += ">";
                }

                List<Object> list = (List<Object>)elementEntry.getValue();

                for (Object listObject : list)
                {
                    if (listObject instanceof Map)
                    {
                        Map<String, Object> map = (Map<String, Object>)listObject;

                        map = sortMap(map);

                        tagContent += getTag(elementEntry.getKey(), map);
                    }
                    else
                    {
                        tagContent += elementEntry.getKey() + "=\"" + elementEntry.getValue() + "\" ";
                    }
                }
            }
        }

        if (hasOnlyAttributes)
        {
            tagContent += "></" + tagName + ">";
        }
        else
        {
            tagContent += "</" + tagName + ">";
        }

        return tagContent;
    }

    private static LinkedHashMap<String, Object> sortMap(Map<String, Object> mapToSort)
    {
        Map<String, Object> mapWithMap = new HashMap<String, Object>();
        Map<String, Object> mapWithList = new HashMap<String, Object>();
        Map<String, Object> mapWithString = new HashMap<String, Object>();
        LinkedHashMap<String, Object> sortedMap = new LinkedHashMap<String, Object>();

        for (Map.Entry<String, Object> entryKey : mapToSort.entrySet())
        {
            if (entryKey.getValue() instanceof Map)
            {
                mapWithMap.put(entryKey.getKey(), entryKey.getValue());
            }
            else if (entryKey.getValue() instanceof List)
            {
                mapWithList.put(entryKey.getKey(), entryKey.getValue());
            }
            else
            {
                mapWithString.put(entryKey.getKey(), entryKey.getValue());
            }
        }

        Map<String, Object> reversedMapWithMap = new TreeMap<String, Object>(Collections.<String>reverseOrder());
        Map<String, Object> reversedMapWithList = new TreeMap<String, Object>(Collections.<String>reverseOrder());

        reversedMapWithMap.putAll(mapWithMap);
        reversedMapWithList.putAll(mapWithList);

        for (Map.Entry<String, Object> entryKey : mapWithString.entrySet())
        {
            sortedMap.put(entryKey.getKey(), entryKey.getValue());
        }

        for (Map.Entry<String, Object> entryKey : mapWithMap.entrySet())
        {
            sortedMap.put(entryKey.getKey(), entryKey.getValue());
        }

        for (Map.Entry<String, Object> entryKey : mapWithList.entrySet())
        {
            sortedMap.put(entryKey.getKey(), entryKey.getValue());
        }

        return sortedMap;
    }
}
