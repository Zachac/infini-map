package org.ex.infinite.map.exit;

import org.ex.infinite.map.location.Location;

public interface Exit {

	public Location getLocation();
	public String getName();
	public boolean isVisible();
	
}
