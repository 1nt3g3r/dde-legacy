package ua.com.integer.dde.extension.localize.editor;

import javax.swing.AbstractListModel;

import ua.com.integer.dde.extension.localize.Localize;

@SuppressWarnings("rawtypes")
public class TagListModel extends AbstractListModel {
	private static final long serialVersionUID = 8160630556306607701L;
	
	private Localize localize;
	
	public TagListModel(Localize localize) {
		this.localize = localize;
	}

	@Override
	public Object getElementAt(int index) {
		return localize.getTags().get(index);
	}

	@Override
	public int getSize() {
		return localize.getTags().size;
	}
}
