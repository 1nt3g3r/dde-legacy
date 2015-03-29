package ua.com.integer.dde.extension.ui.editor.main;

import java.io.File;

import javax.swing.AbstractListModel;

import com.badlogic.gdx.utils.Array;

@SuppressWarnings("rawtypes")
public class ActorListModel extends AbstractListModel {
	private static final long serialVersionUID = -3369513931830934340L;
	
	private Array<File> actors;
	
	public ActorListModel(Array<File> actors) {
		this.actors = actors;
	}

	@Override
	public int getSize() {
		return actors.size;
	}

	@Override
	public Object getElementAt(int index) {
		return actors.get(index).getName().split("\\.")[0];
	}
	
	public File getFileAt(int index) {
		return actors.get(index);
	}
	
	public boolean containsName(String name) {
		for(int i = 0; i < getSize(); i++) {
			if (getElementAt(i).equals(name)) {
				return true;
			}
		}
		
		return false;
	}
	
	public Array<File> getActorFiles() {
		return actors;
	}
}
