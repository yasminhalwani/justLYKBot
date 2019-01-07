package app.justlykbot.xmlparsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import app.justlykbot.datatypes.Game;
import app.justlykbot.datatypes.Trophy;

public class TrophyXMLParseHandler {
	List<Trophy> trophies;
	private Trophy trophy;
	private String text;

	public TrophyXMLParseHandler() {
		trophies = new ArrayList<Trophy>();
	}

	public List<Trophy> getTrophys() {
		return trophies;
	}

	public List<Trophy> parse(InputStream is) {
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
						trophy = new Trophy();
					}
					break;

				case XmlPullParser.TEXT:
					text = parser.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagname.equalsIgnoreCase("row")) {
						trophies.add(trophy);
					} else if (tagname.equalsIgnoreCase("description")) {
						trophy.setDescription(text);
					} else if (tagname.equalsIgnoreCase("name")) {
						trophy.setName(text);
					} else if (tagname.equalsIgnoreCase("game")) {
						trophy.setGame(new Game(text));
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

		return trophies;
	}
}