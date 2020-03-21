package org.ex.infinite.services.channels;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.ex.infinite.utility.ExpiringMessageQueue;
import org.ex.infinite.utility.ExpiringMessageQueue.Message;
import org.ex.infinite.utility.NullIterator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ChannelManager {

	private static final long MESSAGE_RETENTION = TimeUnit.MINUTES.toMillis(5);
	
	private ConcurrentHashMap<String, ExpiringMessageQueue> channels = new ConcurrentHashMap<>();

	@Autowired private Logger log;
	
	public Iterator<Message> getMessages(String channel, long effectiveTs) {
		var queue = channels.get(channel);
		
		if (queue == null) {
			return NullIterator.value();
		}
		
		return queue.getMessages(effectiveTs);
	}
	
	public void addMessage(String channel, String message) {
		var queue = channels.computeIfAbsent(channel, (c) -> new ExpiringMessageQueue(MESSAGE_RETENTION));
		queue.add(message);
	}
	
	@Scheduled(fixedRate=10_000)
	public void cullMessages() {
		var startTs = System.currentTimeMillis();
		AtomicInteger culled = new AtomicInteger();
		AtomicInteger deleted = new AtomicInteger();
		
		for (var entry : channels.entrySet()) {
			channels.merge(entry.getKey(), entry.getValue(), (channel, queue) -> {
				culled.addAndGet(queue.cull());
				
				if (queue.isEmpty()) {
					queue = null;
					deleted.incrementAndGet();
				}
				
				return queue;
			});
		}

		long duration = System.currentTimeMillis() - startTs;
		
		if (culled.get() != 0 || duration != 0) {
			log.info("Culled {} messages in {}ms", culled.get(), duration);
		}
		
		if (deleted.get() != 0) {
			log.info("Deleted {} channels in {}ms", deleted.get(), duration);
		}
	}
}
