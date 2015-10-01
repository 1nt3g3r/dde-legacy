package ua.com.integer.dde.res.load.set.resolver;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;
import ua.com.integer.dde.res.load.imp.MusicManager;

public class MusicResolver implements ResourceResolver {
	@Override
	public PathDescriptorLoadManager resolve(String resName) {
		if (resName.equals("music")) {
			return new MusicManager();
		}
		
		return null;
	}
}
