package ua.com.integer.dde.res.load.set.resolver;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;
import ua.com.integer.dde.res.load.imp.SoundManager;

public class SoundResolver implements ResourceResolver {
	@Override
	public PathDescriptorLoadManager resolve(String resName) {
		if (resName.equals("sounds")) {
			return new SoundManager();
		}
		
		return null;
	}
}
