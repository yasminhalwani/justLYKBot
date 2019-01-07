package app.justlykbot.xmlparsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import app.justlykbot.datatypes.User;

public class UserXMLParseHandler {
	List<User> users;
	private User user;
	private String text;

	public UserXMLParseHandler() {
		users = new ArrayList<User>();
	}

	public List<User> getUsers() {
		return users;
	}

	public List<User> parse(InputStream is) {
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
						user = new User();
					}
					break;

				case XmlPullParser.TEXT:
					text = parser.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagname.equalsIgnoreCase("row")) {
						users.add(user);
					} else if (tagname.equalsIgnoreCase("username")) {
						user.setName(text);
					} else if (tagname.equalsIgnoreCase("displayPicture")) {
						user.setDpURL(text);
					} else if (tagname.equalsIgnoreCase("imageResourceId")) {
						user.setImageResourceId(text);
					}else if (tagname.equalsIgnoreCase("sipPass")) {
						user.setSipPass(text);
					} else if (tagname.equalsIgnoreCase("sipId")) {
						user.setSipID(text);
					} else if (tagname.equalsIgnoreCase("xmppId")) {
						user.setXmppID(text);
					} else if (tagname.equalsIgnoreCase("xmppPass")) {
						user.setXmppPass(text);
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

		return users;
	}
}