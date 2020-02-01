package org.ex.infinite.map.exit;

import org.ex.infinite.map.location.Location;

public interface Exit {

	Location getLocation();
	String getName();
	boolean isVisible();
	
}
