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
	private UiConfig temporary;
	
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
			getEditorScreen().removeUiConfig(getUiConfig());
			
			EditorKernel.getInstance().getMainWindow().updateActorTree();
		}
	};
	
	class CopyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			temporary = getUiConfig();
			temporary.saveToFile(new File("tmp.actor"));
			temporary = UiConfig.fromFile(new File("tmp.actor"));
			
			new File("tmp.actor").delete();
			
		}
	}
	
	class PasteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			temporary.saveToFile(new File("tmp.actor"));
			UiConfig toInsert = UiConfig.fromFile(new File("tmp.actor"));
			
			getUiConfig().children.add(toInsert);
			getEditorScreen().updateConfig();
			
			getEditorScreen().selectActorByConfig(toInsert);
			
			new File("tmp.actor").delete();
			
			EditorKernel.getInstance().getMainWindow().updateActorTree();
		}
	}
	
	/**
	 * Слушатель на вставку конфига до\после актера
	 * 
	 * @author 1nt3g3r
	 */
	class InsertAfterListener implements ActionListener {
		private int addIndex;
		
		/**
		 * Смещение относительно текущего актера. 
		 * Чтобы вставить актера ПОСЛЕ текущего - addIndex должен быть 1
		 * Чтобы ПЕРЕД текущим - addIndex должен быть 0
		 */
		public InsertAfterListener(int addIndex) {
			this.addIndex = addIndex;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			temporary.saveToFile(new File("tmp.actor"));
			UiConfig toInsert = UiConfig.fromFile(new File("tmp.actor"));
			
			Group parentActor = actor.getParent();
			UiConfig parentConfig = (UiConfig) parentActor.getUserObject();
			if (parentConfig != null) {
				int configIndex = parentConfig.children.indexOf(getUiConfig(), true);
				if (configIndex < 1) {
					configIndex = 1;
				}
				parentConfig.children.insert(configIndex + addIndex, toInsert);
				
				getEditorScreen().updateConfig();
				getEditorScreen().selectActorByConfig(toInsert);
				
				new File("tmp.actor").delete();
				
				EditorKernel.getInstance().getMainWindow().updateActorTree();
			}
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
				JMenuItem pasteItem = new JMenuItem("Paste into");
				pasteItem.addActionListener(new PasteListener());
				toReturn.add(pasteItem);
			}
				
			if (canPasteAfterAndBefore()) {
				JMenuItem insertAfterItem = new JMenuItem("Paste after");
				insertAfterItem.addActionListener(new InsertAfterListener(1));
				toReturn.add(insertAfterItem);
				
				JMenuItem insertBeforeItem = new JMenuItem("Paste before");
				insertBeforeItem.addActionListener(new InsertAfterListener(-1));
				toReturn.add(insertBeforeItem);
			}
		return toReturn;
	}
	
	/**
	 * Можно ли вставить конфиг в выбранного актера. Вставка конфига 
	 * возможна лишь в случае если есть что вставить (temporary != null) 
	 * и актер является группой
	 */
	private boolean canPasteIntoActor() {
		if (actor instanceof ScrollPane) {
			return temporary != null && ((ScrollPane) actor).getWidget() == null;
		}
		
		return temporary != null && actor instanceof Group;
	}
	
	/**
	 * Можно ли вставить конфиг до или после выбранного актера. Вставка конфига 
	 * возможна лишь в случае, если есть родительський конфиг
	 */
	private boolean canPasteAfterAndBefore() {
		return temporary != null && 
			   actor.getParent() != null &&
			   actor.getParent().getUserObject() != null;
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
			
			getUiConfig().children.add(config);
			getEditorScreen().updateConfig();
			
			getEditorScreen().selectActorByConfig(config);
			
			EditorKernel.getInstance().getMainWindow().updateActorTree();
		}
	}
	
	public JMenu createInsertMenu() {
		JMenu toReturn = new JMenu("Insert");
			toReturn.add(createAddWidgetMenu("Simple", WidgetType.SIMPLE_WIDGETS));
			toReturn.add(createAddWidgetMenu("Container", WidgetType.CONTAINER_WIDGETS));
			toReturn.add(createAddWidgetMenu("Other", WidgetType.OTHER_WIDGETS));
			if (DDEExtensionActors.getInstance().hasActors()) {
				for(String category: DDEExtensionActors.getInstance().getCategories()) {
					toReturn.add(createAddExtensionWidgetMenu(category));
				}
			}
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
			
			getUiConfig().children.add(config);
			getEditorScreen().updateConfig();
			
			getEditorScreen().selectActorByConfig(config);
			
			EditorKernel.getInstance().getMainWindow().updateActorTree();
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
	
	
	class InsertDialogListener implements ActionListener {
		private File configFile;

		public InsertDialogListener(File configFile) {
			this.configFile = configFile;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			getUiConfig().children.add(UiConfig.fromFile(configFile));
			getEditorScreen().updateConfig();
			
			EditorKernel.getInstance().getMainWindow().updateActorTree();
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
