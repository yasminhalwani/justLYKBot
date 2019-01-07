package app.justlykbot.xmlparsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import app.justlykbot.datatypes.ChallengeRequest;

public class ChallengeRequestXMLParseHandler {
	List<ChallengeRequest> challengeRequests;
	private ChallengeRequest challengeRequest;
	private String text;

	public ChallengeRequestXMLParseHandler() {
		challengeRequests = new ArrayList<ChallengeRequest>();
	}

	public List<ChallengeRequest> getChallengeRequests() {
		return challengeRequests;
	}

	public List<ChallengeRequest> parse(InputStream is) {
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
						challengeRequest = new ChallengeRequest();
					}
					break;

				case XmlPullParser.TEXT:
					text = parser.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagname.equalsIgnoreCase("row")) {
						challengeRequests.add(challengeRequest);
					} else if (tagname.equalsIgnoreCase("sender")) {
						challengeRequest.setSender(text);
					} else if (tagname.equalsIgnoreCase("receiver")) {
						challengeRequest.setReceiver(text);
					} else if (tagname.equalsIgnoreCase("date")) {
						challengeRequest.setDateAndTimeSent(text);
					} else if (tagname.equalsIgnoreCase("game")) {
						challengeRequest.setGame(text);
					} else if (tagname.equalsIgnoreCase("status")) {
						challengeRequest.setStatus(text);
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

		return challengeRequests;
	}
}