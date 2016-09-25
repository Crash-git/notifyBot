package com.github.crashman054.notifybot;

/*
 * Code made by CrashMan054, AKA Collin Lavergne. Please don't copy it.	
 * This is a Discord bot made for the purpose of grabbing Blizzard dev posts from the forums, reading RSS feeds, and posting them in a discord server as soon as they're updated.
 * --------------------------------------VERSION HISTORY-----------------------------------------------------------------------
 * 1.0.0 - Initial program (9/21/16)
 * ----------------------------------------------------------------------------------------------------------------------------
 */
import java.io.IOException;	
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class notifyBot {
	
	@SuppressWarnings("unused")
	//Main method, used for debug.
	public static void main(String[] args) throws IOException {
		/*Set up main variables:
		 * html is entire file
		 * title is first title element, so post title
		 * content is the post content
		 * link is the link to the dev post
		 * oldContent and oldTitle are for comparison. If they don't match the new data grabbed, then something new has been posted.
		 */
		Element html;
		String title;
		String link;
		Element content;
		String oldContent;
		String oldTitle;
		
		//Connect to the dev tracker, select forum posts only
		Document doc = Jsoup.connect("http://www.overstalk.io/?sources=BLIZZARD_FORUM").get();
		html = doc.body();
		//Grab data from the HTML
		content = doc.select("a[href*=us.battle.net/forums/en/overwatch/topic/]").first();
		link = content.attr("href");
		title = content.text();
		
		
		//Print that data to the console (debug)
		System.out.println(content);
		System.out.println(link);
		System.out.println(title);
	}
	
	public static String update(String initialLink) {
		Element html;
		String title = null;
		String link = null;
		Element content;
		String oldContent;
		String oldTitle;
		
		
		return link + title;
		
	}

}
