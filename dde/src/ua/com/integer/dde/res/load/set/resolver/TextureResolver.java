package ua.com.integer.dde.res.load.set.resolver;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;
import ua.com.integer.dde.res.load.imp.TextureManager;

public class TextureResolver implements ResourceResolver {
	@Override
	public PathDescriptorLoadManager resolve(String resName) {
		if (resName.equals("textures")) {
			return new TextureManager();
		}
		
		return null;
	}
}
