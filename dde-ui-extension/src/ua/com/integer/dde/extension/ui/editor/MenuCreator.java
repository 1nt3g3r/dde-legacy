package ua.com.integer.dde.extension.ui.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.WidgetType;
import ua.com.integer.dde.extension.ui.actor.DDEExtensionActors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public class MenuCreator {
	private Actor actor;
	
	private static MenuCreator instance = new MenuCreator();
	
	private MenuCreator() {
	}
	
	public static MenuCreator getInstance() {
		return instance;
	}
	
	public JPopupMenu createMenu(Actor actor, UiConfig uiConfig) {
		this.actor = actor;
		
		return createMenu();
	}
	
	class DeleteItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EditorKernel.executeCommand("rm");
		}
	};
	
	class CopyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EditorKernel.executeCommand("copy");
		}
	}
	
	class PasteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EditorKernel.executeCommand("paste");
		}
	}
	
	private JPopupMenu createMenu() {
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		JPopupMenu toReturn = new JPopupMenu();
			if (actor instanceof Group) {
				toReturn.add(createInsertMenu());
				toReturn.add(new JSeparator());
			}
			
			JMenuItem deleteItem = new JMenuItem("Delete");
			deleteItem.addActionListener(new DeleteItemListener());
			toReturn.add(deleteItem);
			
			JMenuItem copyItem = new JMenuItem("Copy");
			copyItem.addActionListener(new CopyListener());
			toReturn.add(copyItem);
			
			if (canPasteIntoActor()) {
				JMenuItem pasteItem = new JMenuItem("Paste");
				pasteItem.addActionListener(new PasteListener());
				toReturn.add(pasteItem);
			}
		return toReturn;
	}
	
	/**
	 * Можно ли вставить конфиг в выбранного актера. Вставка конфига 
	 * возможна лишь в случае если есть что вставить (temporary != null) 
	 * и актер является группой
	 */
	private boolean canPasteIntoActor() {
		UiConfig temporary = EditorKernel.getInstance().getTemporary();
		
		if (actor instanceof ScrollPane) {
			return temporary != null && ((ScrollPane) actor).getWidget() == null;
		}
		
		return temporary != null && actor instanceof Group;
	}
	
	class CreateWidgetListener implements ActionListener {
		private WidgetType widgetType;
		
		public CreateWidgetListener(WidgetType widgetType) {
			this.widgetType = widgetType;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (getUiConfig() == null) {
				JOptionPane.showMessageDialog(null, "Select parent widget!");
				return;
			}
			
			if (!getUiConfig().widgetType.isContainer()) {
				JOptionPane.showMessageDialog(null, "Selected widget can't contain other widgets!");
				return;
			}
			
			UiConfig config = new UiConfig();
			config.name = widgetType.getShortDescription();
			config.widgetType = widgetType;
			
			getEditorScreen().addConfig(getUiConfig(), config);
		}
	}
	
	public JMenu createInsertMenu() {
		JMenu toReturn = new JMenu("Insert");
			JMenu simpleWidgets = createAddWidgetMenu("Simple", WidgetType.SIMPLE_WIDGETS);
			JMenu containerWidgets = createAddWidgetMenu("Container", WidgetType.CONTAINER_WIDGETS);
			JMenu otherWidgets = createAddWidgetMenu("Other", WidgetType.OTHER_WIDGETS);
			
			if (DDEExtensionActors.getInstance().hasActors()) {
				for(String category: DDEExtensionActors.getInstance().getCategories()) {
					if (category.equals("Simple")) {
						addExtensionsWidgetToMenu(category, simpleWidgets);
					} else if (category.equals("Container")) {
						addExtensionsWidgetToMenu(category, containerWidgets);
					} else if (category.equals("Other")) {
						addExtensionsWidgetToMenu(category, otherWidgets);
					} else {
						toReturn.add(createAddExtensionWidgetMenu(category));
					}
				}
			}

			toReturn.add(simpleWidgets);
			toReturn.add(containerWidgets);
			toReturn.add(otherWidgets);
			
			toReturn.add(createInserUiConfigMenu());
		return toReturn;
	}
	
	
	public JMenu createAddWidgetMenu(String menuName, WidgetType ... widgets) {
		JMenu toReturn = new JMenu(menuName);
			for(WidgetType type : widgets) {
				JMenuItem insertItem = new JMenuItem(type.getShortDescription());
				insertItem.addActionListener(new CreateWidgetListener(type));
				toReturn.add(insertItem);
			}
		return toReturn;
	}
	
	class CreateExtensionWidgetListener implements ActionListener {
		private String id;
		
		public CreateExtensionWidgetListener(String id) {
			this.id = id;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (getUiConfig() == null) {
				JOptionPane.showMessageDialog(null, "Select parent widget!");
				return;
			}
			
			if (!getUiConfig().widgetType.isContainer()) {
				JOptionPane.showMessageDialog(null, "Selected widget can't contain other widgets!");
				return;
			}
			
			UiConfig config = new UiConfig();
			config.name = id;
			config.extensionId = id;
			config.widgetType = WidgetType.EXTENSION_ACTOR;
			
			getEditorScreen().addConfig(getUiConfig(), config);
		}
		
	}
	
	public JMenu createAddExtensionWidgetMenu(String category) {
		JMenu toReturn = new JMenu(category);
		for(String id: DDEExtensionActors.getInstance().getIdsFromCategory(category)) {
			String name = DDEExtensionActors.getInstance().getDescription(id);
			JMenuItem insertItem = new JMenuItem(name);
			insertItem.addActionListener(new CreateExtensionWidgetListener(id));
			toReturn.add(insertItem);
			
		}
		return toReturn;
	}
	
	public void addExtensionsWidgetToMenu(String category, JMenu menu) {
		for(String id: DDEExtensionActors.getInstance().getIdsFromCategory(category)) {
			String name = DDEExtensionActors.getInstance().getDescription(id);
			JMenuItem insertItem = new JMenuItem(name);
			insertItem.addActionListener(new CreateExtensionWidgetListener(id));
			menu.add(insertItem);
			
		}
	}
	
	class InsertDialogListener implements ActionListener {
		private File configFile;

		public InsertDialogListener(File configFile) {
			this.configFile = configFile;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			getEditorScreen().addConfig(getUiConfig(), UiConfig.fromFile(configFile));
		}
	};
	
	private JMenu createInserUiConfigMenu() {
		JMenu toReturn = new JMenu("Config");
		for(File actorFile : EditorKernel.getInstance().getMainWindow().getActorFiles()) {
			JMenuItem toInsert = new JMenuItem(actorFile.getName().split("\\.")[0]);
			toInsert.addActionListener(new InsertDialogListener(actorFile));
			toReturn.add(toInsert);
		}
		return toReturn;
	}
	
	private UiConfig getUiConfig() {
		return EditorKernel.editorScreen().getSelectedConfig();
	}
	
	private UiEditorScreen getEditorScreen() {
		return EditorKernel.editorScreen();
	}
}
