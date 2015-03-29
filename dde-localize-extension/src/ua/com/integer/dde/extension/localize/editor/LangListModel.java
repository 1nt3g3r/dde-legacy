package ua.com.integer.dde.extension.localize.editor;

import javax.swing.AbstractListModel;

import ua.com.integer.dde.extension.localize.Translation;

import com.badlogic.gdx.utils.Array;

@SuppressWarnings("rawtypes")
public class LangListModel extends AbstractListModel {
	private static final long serialVersionUID = -1544368444811546330L;
	
	private Array<String> languages;
	
	public LangListModel(Translation translation) {
		languages = translation.getLanguages();
	}
	
	@Override
	public Object getElementAt(int index) {
		return languages.get(index);
	}

	@Override
	public int getSize() {
		return languages.size;
	}

}
