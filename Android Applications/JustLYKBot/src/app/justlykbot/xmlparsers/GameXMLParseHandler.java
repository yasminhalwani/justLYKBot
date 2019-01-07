package app.justlykbot.xmlparsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import app.justlykbot.datatypes.Game;

public class GameXMLParseHandler {
	List<Game> games;
	private Game game;
	private String text;

	public GameXMLParseHandler() {
		games = new ArrayList<Game>();
	}

	public List<Game> getGames() {
		return games;
	}

	public List<Game> parse(InputStream is) {
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
						game = new Game();
					}
					break;

				case XmlPullParser.TEXT:
					text = parser.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagname.equalsIgnoreCase("row")) {
						games.add(game);
					} else if (tagname.equalsIgnoreCase("name")) {
						game.setTitle(text);
					} else if (tagname.equalsIgnoreCase("displayPicture")) {
						game.setDpURL(text);
					} else if (tagname.equalsIgnoreCase("imageResourceId")) {
						game.setImageResourceId(text);
					}else if (tagname.equalsIgnoreCase("package")) {
						game.setGamePackage(text);
					} else if (tagname.equalsIgnoreCase("downloadURL")) {
						game.setDownloadURL(text);
					} else if (tagname.equalsIgnoreCase("appName")) {
						game.setAppName(text);
					} else if (tagname.equalsIgnoreCase("description")) {
						game.setDescription(text);
					} else if (tagname.equalsIgnoreCase("extraInstruction")) {
						game.setExtraInstruction(text);
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

		return games;
	}
}