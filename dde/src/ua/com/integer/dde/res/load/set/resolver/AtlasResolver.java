package ua.com.integer.dde.res.load.set.resolver;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;
import ua.com.integer.dde.res.load.imp.AtlasManager;

public class AtlasResolver implements ResourceResolver {
	@Override
	public PathDescriptorLoadManager resolve(String resName) {
		if (resName.equals("atlases")) {
			return new AtlasManager();
		}
		
		return null;
	}
}
