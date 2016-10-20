package com.github.crashman054.notifyBot;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

//This method handles any text commands. It is running as long as the bot is.
public class Commands {
	
	public static IChannel autoChannel;
	
	//Prints a message when !update is typed, returns it to same channel
	 @EventSubscriber
	 public void updateEvent(MessageReceivedEvent event) {//Triggers when a message is sent to a channel the bot is in
		IMessage message = event.getMessage(); //Get the message that was sent
		IChannel channel = event.getMessage().getChannel(); //Gets the channel the message was sent from
		
		//!update command
		if(message.getContent().startsWith("!update")){//If the message starts with the proper command
			String result = App.update("http://www.overstalk.io/?sources=BLIZZARD_FORUM"); //Calls a method to grab data from the dev website
			
			//If the result from the update method was the same as before, don't post again. Instead, search again.
			if (result == "no") {
				App.update("http://www.overstalk.io/?sources=BLIZZARD_FORUM");
			}
			else{
				try {//try/catch loop for sending a message, self-explanatory
					new MessageBuilder(event.getClient()).withChannel(channel).withContent(result).build();
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
		}
		
		//!ping command
		if (message.getContent().startsWith("!ping")) {
			try {
				new MessageBuilder(event.getClient()).withChannel(channel).withContent("pong!").build();
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
		
		//!set channel command
		if (message.getContent().startsWith("!set channel")) {
			autoChannel = message.getChannel();
			
			try {
				new MessageBuilder(event.getClient()).withChannel(channel).withContent("Channel set!").build();
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
	}
}
