package com.github.crashman054.notifyBot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

//This class logs in the bot and creates the loop for auto-updating.
public class NotifyBot {
	//Declares the Discord client variable
	public static IDiscordClient client;

	public static void main(String[] args) throws DiscordException, InterruptedException {
		//Calls the login method and registers a listener for commands and ready status
	    IDiscordClient client = NotifyBot.login("MjI5Nzg5MzQyNTAxOTYxNzI4.Cul_3g.Io8hHVjG5IEhYATtzK5B9o2BSgI", true);
	    EventDispatcher dispatcher = client.getDispatcher();
	    dispatcher.registerListener(new App());
	    dispatcher.registerListener(new Commands());
	    
	    //start up an infinite loop
	    int ticks = 0;
	    while(true) {
	    	if (ticks < 10) {//Check for a reasonable amount of time
	    		String result = App.update("http://www.overstalk.io/?sources=BLIZZARD_FORUM");
	    		
	    		//check to see that there's something to update
				if (result == "no") {
					App.update("http://www.overstalk.io/?sources=BLIZZARD_FORUM");
				}
				else{
					try {//try/catch loop for sending a message, self-explanatory
						Thread.sleep(30000); //Wait 30 seconds so someone can set the channel initially
						new MessageBuilder(client).withChannel(Commands.autoChannel).withContent(result).build();
					} catch (RateLimitException e) { 
						System.err.print("Sending messages too quickly!");
						e.printStackTrace();
					} catch (DiscordException e) { 
						System.err.print(e.getErrorMessage()); 
						e.printStackTrace();
					} catch (MissingPermissionsException e) { 
						System.err.print("Missing permissions for channel!");
						e.printStackTrace();
					}
				}
	    		ticks++;
	    	}
	    	else
	    		ticks = 0;
	    	
	    	//If the ticks reach 9, then don't check again for 5 minutes
	    	System.out.println("Update check done");
	    	Thread.sleep(30000);
	    }
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
