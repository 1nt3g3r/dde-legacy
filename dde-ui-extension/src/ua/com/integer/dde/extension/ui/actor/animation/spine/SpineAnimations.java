package ua.com.integer.dde.extension.ui.actor.animation.spine;

import java.io.File;

import ua.com.integer.dde.startpanel.FileUtils;
import ua.com.integer.dde.startpanel.ddestub.ProjectFinder;
import ua.com.integer.dde.util.JsonWorker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;

public class SpineAnimations {
	public static final String ANIMATION_PATH = "data/spine-animations";
	
	private Array<String> tmpAnimationList = new Array<String>();
	private String[] tmpAnimationArray;
	
	private static SpineAnimations instance = new SpineAnimations();
	
	private SpineAnimations() {
	}
	
	public static SpineAnimations getInstance() {
		return instance;
	}
	
	public String[] getAnimationConfigs() {
		tmpAnimationList.clear();
		
		String animationPath = ProjectFinder.findAndroidProject() + "/assets/" + ANIMATION_PATH;
		File[] children = new File(animationPath).listFiles();
		
		for(File child: children) {
			if (child.getName().endsWith(".json")) {
				tmpAnimationList.add(child.getName().split("\\.")[0]);
			}
		}
		
		tmpAnimationArray = new String[tmpAnimationList.size];
		for(int i = 0; i < tmpAnimationList.size; i++) {
			tmpAnimationArray[i] = tmpAnimationList.get(i);
		}
		
		Array<File> tmp = new Array<File>();
		File baseDirectory = new File(animationPath);
		
		FileUtils.getFilesRecursivelyByExtensions(baseDirectory , tmp, ".json");
		
		tmpAnimationArray = new String[tmp.size];
		for(int i = 0; i < tmpAnimationArray.length; i++) {
			File file = tmp.get(i);
			tmpAnimationArray[i] = FileUtils.getFilenameRelativeToParent(file, baseDirectory).split("\\.")[0];
		}
		
		return tmpAnimationArray;
	}
	
	
	@SuppressWarnings("unchecked")
	public String[] getAnimationList(String configName) {
		ObjectMap<String, JsonValue> tmp = new OrderedMap<String, JsonValue>();
		tmp = JsonWorker.JSON.fromJson(tmp.getClass(), Gdx.files.internal(ANIMATION_PATH + "/" + configName + ".json"));
		
		for(String key: tmp.keys()) {
			if (key.equals("animations")) {
				JsonValue tmpValue = tmp.get(key);
				int childCount = tmpValue.size;
				
				String[] result = new String[childCount];

				for(int i = 0; i < childCount; i++) {
					JsonValue animationName = tmpValue.get(i);
					result[i] = animationName.name;
				}
				
				return result;
			}
		}
		
		return new String[0];
	}
	
}
