package ua.com.integer.dde.res.load.set.resolver;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;
import ua.com.integer.dde.res.load.imp.TTFFontManager;

public class TTFFontManagerResolver implements ResourceResolver {
	@Override
	public PathDescriptorLoadManager resolve(String resName) {
		if (resName.equals("fonts")) {
			return new TTFFontManager();
		}
		
		return null;
	}
}
