package org.ex.infinite.utility;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ExpiringMessageQueue {

	private final long messageRetention;
	private final ConcurrentLinkedDeque<Message> messages;
	
	public ExpiringMessageQueue(long messageRetention) {
		this.messageRetention = messageRetention;
		this.messages = new ConcurrentLinkedDeque<>(); 
	}
	
	public void add(String message) {
		messages.add(new Message(message));
	}
	
	public Iterator<Message> getMessages(long effectiveTs) {
		var iter = messages.descendingIterator();
		
		return new Iterator<Message>() {

			Message currentMessage = iter.hasNext() ? iter.next() : null;
			
			@Override
			public boolean hasNext() {
				return currentMessage != null && currentMessage.effectiveTs > effectiveTs;
			}

			@Override
			public Message next() {
				var result = currentMessage;
				currentMessage = iter.hasNext() ? iter.next() : null;
				return result;
			}
		};
	}
	
	public int cull() {
		var count = 0;
		var iter = messages.iterator();
		
		while (iter.hasNext() && System.currentTimeMillis() - iter.next().effectiveTs > messageRetention) {
			iter.remove();
			count++;
		}
		
		return count;
	}
	
	public boolean isEmpty() {
		return messages.isEmpty();
	}
	
	public static class Message {
		public long effectiveTs = System.currentTimeMillis();
		public String message;
		
		public Message(String message) {
			this.message = message;
		}
	}
}
