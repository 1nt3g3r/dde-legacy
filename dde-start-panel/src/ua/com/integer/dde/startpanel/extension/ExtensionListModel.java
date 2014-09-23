package ua.com.integer.dde.startpanel.extension;

import javax.swing.AbstractListModel;

import com.badlogic.gdx.utils.Array;

public class ExtensionListModel extends AbstractListModel<String> {
	private Array<ExtensionInfo> extensions;
	private static final long serialVersionUID = 487063743752426580L;

	public ExtensionListModel(Array<ExtensionInfo> extensions) {
		this.extensions = extensions;
	}
	
	@Override
	public String getElementAt(int index) {
		return extensions.get(index).getExtensionName();
	}

	@Override
	public int getSize() {
		return extensions.size;
	}
	
	public ExtensionInfo getExtensionInfo(int index) {
		return extensions.get(index);
	}

}
