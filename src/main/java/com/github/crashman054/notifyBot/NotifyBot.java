package com.github.crashman054.notifyBot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

public class NotifyBot {
	//Declares the Discord client variable
	public static IDiscordClient client;

	public static void main(String[] args) throws DiscordException {
		//Calls the login method and registers a listener for commands and ready status
	    IDiscordClient client = NotifyBot.login("MjI5Nzg5MzQyNTAxOTYxNzI4.Cul_3g.Io8hHVjG5IEhYATtzK5B9o2BSgI", true);
	    EventDispatcher dispatcher = client.getDispatcher();
	    dispatcher.registerListener(new App());
	    dispatcher.registerListener(new Commands());
	}

	public static IDiscordClient login(String token, boolean login) throws DiscordException { //Logs the bot in and grabs an instance of the Discord client
		ClientBuilder clientBuilder = new ClientBuilder(); //Creates the ClientBuilder to log in
		clientBuilder.withToken(token); //Gives the constructor the token
		if (login) { //Checks for a variable to delay login
			return clientBuilder.login(); // Creates the client instance and logs the client in
		} 
		else {
			return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() manually
		}
	}

}
