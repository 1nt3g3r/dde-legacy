package ua.com.integer.dde.extension.ui.editor.tree;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import ua.com.integer.dde.extension.ui.UiConfig;

import com.badlogic.gdx.utils.Array;

public class UIConfigTreeModel implements TreeModel {
	private Array<TreeModelListener> listeners = new Array<TreeModelListener>();
	
	private UiConfig config;
	
	public UIConfigTreeModel(UiConfig config) {
		this.config = config;
	}

	@Override
	public void addTreeModelListener(TreeModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public Object getChild(Object node, int index) {
		return getConfig(node).children.get(index);
	}

	@Override
	public int getChildCount(Object node) {
		return getConfig(node).getChildCount();
	}

	@Override
	public int getIndexOfChild(Object child, Object parentNode) {
		Array<UiConfig> children = getConfig(parentNode).children;
		if (children == null) {
			return 0;
		}
		
		for(int i = 0; i < children.size; i++) {
			if (children.get(i) == child) {
				return i;
			}
		}
		
		return 0;
	}

	@Override
	public Object getRoot() {
		return config;
	}

	@Override
	public boolean isLeaf(Object node) {
		UiConfig config = getConfig(node);
		boolean isLeaf = config != null && config.children.size == 0;
		if (config != null) {
			System.out.println("is leaf: " + config.name + ", leaf: " + isLeaf);
		}
		return isLeaf;
	}

	@Override
	public void removeTreeModelListener(TreeModelListener listener) {
		listeners.removeValue(listener, true);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object node) {
	}

	private UiConfig getConfig(Object node) {
		return config.recursiveSearch(node);
	}
}
