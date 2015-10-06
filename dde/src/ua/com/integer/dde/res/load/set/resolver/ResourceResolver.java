package ua.com.integer.dde.res.load.set.resolver;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;

public interface ResourceResolver {
	public PathDescriptorLoadManager resolve(String resName);
}
