package app.justlykbot.xmlparsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import app.justlykbot.datatypes.Server;

public class ServerXMLParseHandler {
	
	 List<Server> servers;
	    private Server server;
	    private String text;
	 
	    public ServerXMLParseHandler() {
	        servers = new ArrayList<Server>();
	    }
	 
	    public List<Server> getServers() {
	        return servers;
	    }
	 
	    public List<Server> parse(InputStream is) {
	        XmlPullParserFactory factory = null;
	        XmlPullParser parser = null;
	        try {
	            factory = XmlPullParserFactory.newInstance();
	            factory.setNamespaceAware(true);
	            parser = factory.newPullParser();
	 
	            parser.setInput(is, null);
	 
	            int eventType = parser.getEventType();
	            while (eventType != XmlPullParser.END_DOCUMENT) {
	                String tagname = parser.getName();
	                switch (eventType) {
	                case XmlPullParser.START_TAG:
	                    if (tagname.equalsIgnoreCase("row")) {
	                        server = new Server();
	                    }
	                    break;
	 
	                case XmlPullParser.TEXT:
	                    text = parser.getText();
	                    break;
	 
	                case XmlPullParser.END_TAG:
	                    if (tagname.equalsIgnoreCase("row")) {
	                       servers.add(server);
	                    } else if (tagname.equalsIgnoreCase("name")) {
	                        server.setName(text);
	                    } else if (tagname.equalsIgnoreCase("function")) {
	                        server.setFunction(text);
	                    } else if (tagname.equalsIgnoreCase("ipAddress")) {
	                        server.setIpAddress(text);
	                    } else if (tagname.equalsIgnoreCase("domain")) {
	                        server.setDomain(text);
	                    } else if (tagname.equalsIgnoreCase("port")) {
	                        server.setPort(text);
	                    } else if (tagname.equalsIgnoreCase("xcap_root")) {
	                        server.setXcapRoot(text);
	                    } else if (tagname.equalsIgnoreCase("service")) {
	                        server.setService(text);
	                    } 
	                    break;
	 
	                default:
	                    break;
	                }
	                eventType = parser.next();
	            }
	 
	        } catch (XmlPullParserException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	        return servers;
	    }

}
