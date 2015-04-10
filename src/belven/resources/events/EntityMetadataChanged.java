package belven.resources.events;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;

public class EntityMetadataChanged extends MetadataChanged {
	private Entity entity;

	public EntityMetadataChanged(MetadataValue metaData, Entity e) {
		super(metaData);
		this.setEntity(e);
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity e) {
		this.entity = e;
	}

}