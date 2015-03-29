package ua.com.integer.dde.extension.localize.editor;

import javax.swing.AbstractListModel;

import ua.com.integer.dde.extension.localize.Translation;

@SuppressWarnings("rawtypes")
public class TagListModel extends AbstractListModel {
	private static final long serialVersionUID = 8160630556306607701L;
	
	private Translation translation;
	
	public TagListModel(Translation translation) {
		this.translation = translation;
	}

	@Override
	public Object getElementAt(int index) {
		return translation.getTags().get(index);
	}

	@Override
	public int getSize() {
		return translation.getTags().size;
	}
}
