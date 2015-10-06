package ua.com.integer.dde.res.load.set;

import ua.com.integer.dde.res.load.CompositeLoadManager;
import ua.com.integer.dde.res.load.PathDescriptorLoadManager;
import ua.com.integer.dde.res.load.descriptor.CompositePathDescriptor;
import ua.com.integer.dde.res.load.descriptor.PathDescriptor;
import ua.com.integer.dde.res.load.set.resolver.AtlasResolver;
import ua.com.integer.dde.res.load.set.resolver.MusicResolver;
import ua.com.integer.dde.res.load.set.resolver.ResourceResolver;
import ua.com.integer.dde.res.load.set.resolver.SoundResolver;
import ua.com.integer.dde.res.load.set.resolver.TTFFontManagerResolver;
import ua.com.integer.dde.res.load.set.resolver.TextureResolver;
import ua.com.integer.dde.util.JsonWorker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class ResourceSet {
	private Array<ResourceResolver> resolvers = new Array<ResourceResolver>();
	
	public ObjectMap<String, ObjectMap<String, Array<String>>> resources = new ObjectMap<String, ObjectMap<String, Array<String>>>();
	private CompositeLoadManager loadManager;
	
	public ResourceSet() {
		resolvers = new Array<ResourceResolver>();
		resolvers.add(new AtlasResolver());
		resolvers.add(new MusicResolver());
		resolvers.add(new SoundResolver());
		resolvers.add(new TextureResolver());
		resolvers.add(new TTFFontManagerResolver());
	}
	
	public static ResourceSet getInternal(String fullPathToConfig) {
		return JsonWorker.JSON.fromJson(ResourceSet.class, Gdx.files.internal(fullPathToConfig));
	}
	
	public Array<ResourceResolver> getResolvers() {
		return resolvers;
	}
	
	public void addResolver(ResourceResolver resolver) {
		resolvers.add(resolver);
	}
	
	public void addResolverFirst(ResourceResolver resolver) {
		resolvers.insert(0, resolver);
	}
	
	public CompositeLoadManager createLoadManager() {
		loadManager = new CompositeLoadManager();
		for(String key: resources.keys()) {
			for(ResourceResolver resolver: resolvers) {
				PathDescriptorLoadManager manager = resolver.resolve(key);
				if (manager != null) {
					processManager(manager, resources.get(key));
				}
			}
		}
		return loadManager;
	}
	
	private void processManager(PathDescriptorLoadManager manager, ObjectMap<String, Array<String>> params) {
		CompositePathDescriptor pathDescriptor = new CompositePathDescriptor();
		for(String path: params.keys()) {
			PathDescriptor partPathDescriptor = PathDescriptor.fromString(path);
			pathDescriptor.addDescriptor(partPathDescriptor);
			
			Array<String> loadItems = params.get(path);
			if (loadItems == null || loadItems.size == 0) {
				manager.loadAllFromPathDescriptor(partPathDescriptor);
			} else {
				manager.load(params.get(path));
			}
		}
		
		manager.setDescriptor(pathDescriptor);
		
		loadManager.addManager(manager);
	}
	
	
	public void unload() {
		if (loadManager != null) {
			loadManager.dispose();
		}
	}
}
