package belven.resources.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.metadata.MetadataValue;

public class MetadataChanged extends Event {
	private static final HandlerList handlers = new HandlerList();
	private MetadataValue metaData;

	public MetadataChanged(MetadataValue metaData) {
		this.metaData = metaData;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public MetadataValue getMetaData() {
		return metaData;
	}

	public void setMetaData(MetadataValue metaData) {
		this.metaData = metaData;
	}

}