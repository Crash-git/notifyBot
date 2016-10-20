package com.github.crashman054.notifyBot;
/*
 * Code made by CrashMan054, AKA Collin Lavergne. Please don't copy it.	
 * This is a Discord bot made for the purpose of grabbing Blizzard dev posts from the forums, reading RSS feeds, and posting them in a discord server as soon as they're updated.
 * --------------------------------------VERSION HISTORY-----------------------------------------------------------------------
 * 0.1.0 - Initial program (9/21/16)
 * 0.2.0 - Added command handler and login classes. Set up listeners and data extraction.
 * 0.3.0 - Added main loop for automatic updating. Fixed update check not registering old content properly.
 * ----------------------------------------------------------------------------------------------------------------------------
 */
import java.io.IOException;	
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;

public class App extends NotifyBot implements IListener<ReadyEvent>{
	
	@SuppressWarnings("unused")
	public static String update(String initialLink) { //Method to grab forum data whenever it is called
		/*Set up main variables:
		 * html is entire file
		 * title is first title element, so post title
		 * content is the post content
		 * link is the link to the dev post
		 * oldContent and oldTitle are for comparison. If they don't match the new data grabbed, then something new has been posted.
		 */
		Element html;
		String title = "test"; //Don't want them to be null, just in case
		String link = "test";
		Element content;
		String oldContent;
		String oldTitle = null; //Can't be nothing, because of the if statement below
		String oldLink = null;
		Document doc = null;
		
		
		//Connect to the dev tracker, select forum posts only
		try {
			doc = Jsoup.connect("http://www.overstalk.io/?sources=BLIZZARD_FORUM").get();
		} catch (IOException e) {
			System.err.println("Couldn't connect!");
			e.printStackTrace();
		}
		
		
		html = doc.body();
		//Grab data from the HTML
		content = doc.select("a[href*=us.battle.net/forums/en/overwatch/topic/]").first();
		link = content.attr("href");
		title = content.text();
		
		//set the old title and link for reference
		oldTitle = title;
		oldLink = link;
		
		//Print that data to the console (debug)
		System.out.println(content);
		System.out.println(link);
		System.out.println(title);
		
		//If the post is the same as before, return a value of no. Otherwise, return the info.
		if (title != oldTitle && link != oldLink) {
			return link +  " " + title;
		}
		else
			return "no";
		
	}
	
	public void handle(ReadyEvent event) {//Discord throws out a ReadyEvent when the bot is ready to communicate. This prints a "Ready!" into the console.
		System.out.print("Ready!");
	}
}