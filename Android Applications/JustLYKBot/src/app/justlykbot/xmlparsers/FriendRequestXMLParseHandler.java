package app.justlykbot.xmlparsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import app.justlykbot.datatypes.FriendRequest;

public class FriendRequestXMLParseHandler {
	List<FriendRequest> friendRequests;
	private FriendRequest friendRequest;
	private String text;

	public FriendRequestXMLParseHandler() {
		friendRequests = new ArrayList<FriendRequest>();
	}

	public List<FriendRequest> getFriendRequests() {
		return friendRequests;
	}

	public List<FriendRequest> parse(InputStream is) {
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
						friendRequest = new FriendRequest();
					}
					break;

				case XmlPullParser.TEXT:
					text = parser.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagname.equalsIgnoreCase("row")) {
						friendRequests.add(friendRequest);
					} else if (tagname.equalsIgnoreCase("sender")) {
						friendRequest.setSender(text);
					} else if (tagname.equalsIgnoreCase("receiver")) {
						friendRequest.setReceiver(text);
					} else if (tagname.equalsIgnoreCase("date")) {
						friendRequest.setDateAndTimeSent(text);
					} else if (tagname.equalsIgnoreCase("status")) {
						friendRequest.setStatus(text);
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

		return friendRequests;
	}
}