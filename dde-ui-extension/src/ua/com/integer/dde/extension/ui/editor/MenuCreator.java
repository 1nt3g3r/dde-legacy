package ua.com.integer.dde.extension.ui.editor;

import static ua.com.integer.dde.extension.ui.WidgetType.BOX;
import static ua.com.integer.dde.extension.ui.WidgetType.BUTTON;
import static ua.com.integer.dde.extension.ui.WidgetType.CHECKBOX;
import static ua.com.integer.dde.extension.ui.WidgetType.EMPTY_GROUP;
import static ua.com.integer.dde.extension.ui.WidgetType.IMAGE;
import static ua.com.integer.dde.extension.ui.WidgetType.SCROLL_PANE;
import static ua.com.integer.dde.extension.ui.WidgetType.TEXTURE_REGION_BUTTON;
import static ua.com.integer.dde.extension.ui.WidgetType.TEXTURE_REGION_GROUP_ACTOR;
import static ua.com.integer.dde.extension.ui.WidgetType.TEXT_BUTTON;
import static ua.com.integer.dde.extension.ui.WidgetType.TEXT_FIELD;
import static ua.com.integer.dde.extension.ui.WidgetType.TEXT_LABEL;
import static ua.com.integer.dde.extension.ui.WidgetType.TOUCHPAD;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.WidgetType;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public class MenuCreator {
	private Actor actor;
	private UiConfig uiConfig;
	private UiConfig temporary;
	private UiEditorScreen screen;
	
	private static MenuCreator instance = new MenuCreator();
	
	private MenuCreator() {
	}
	
	public static MenuCreator getInstance() {
		return instance;
	}
	
	public JPopupMenu createMenu(Actor actor, UiConfig uiConfig) {
		this.actor = actor;
		this.uiConfig = uiConfig;
		screen = EditorKernel.getInstance().getScreen(UiEditorScreen.class);
		
		return createMenu();
	}
	
	class DeleteItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			screen.removeUiConfig(getUiConfig());
			
			EditorKernel.getInstance().getActorListDialog().updateActorTree();
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
			screen.updateConfig();
			
			screen.selectActorByConfig(toInsert);
			
			new File("tmp.actor").delete();
			
			EditorKernel.getInstance().getActorListDialog().updateActorTree();
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
				
				screen.updateConfig();
				screen.selectActorByConfig(toInsert);
				
				new File("tmp.actor").delete();
				
				EditorKernel.getInstance().getActorListDialog().updateActorTree();
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
			UiConfig config = new UiConfig();
			config.name = widgetType.getShortDescription();
			config.widgetType = widgetType;
			
			getUiConfig().children.add(config);
			screen.updateConfig();
			
			screen.selectActorByConfig(config);
			
			EditorKernel.getInstance().getActorListDialog().updateActorTree();
		}
	}
	
	private JMenu createInsertMenu() {
		JMenu toReturn = new JMenu("Insert");
			toReturn.add(createAddWidgetMenu("Standard UI widget", IMAGE, TEXT_LABEL, BUTTON, TEXT_BUTTON, CHECKBOX, TEXT_FIELD, TOUCHPAD));
			toReturn.add(createAddWidgetMenu("Container", EMPTY_GROUP, TEXTURE_REGION_GROUP_ACTOR, BOX, SCROLL_PANE));
			toReturn.add(createAddWidgetMenu("Other widgets", TEXTURE_REGION_BUTTON));
			toReturn.add(createInserUiConfigMenu());
		return toReturn;
	}
	
	private JMenu createAddWidgetMenu(String menuName, WidgetType ... widgets) {
		JMenu toReturn = new JMenu(menuName);
			for(WidgetType type : widgets) {
				JMenuItem insertItem = new JMenuItem(type.getShortDescription());
				insertItem.addActionListener(new CreateWidgetListener(type));
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
			screen.updateConfig();
			
			EditorKernel.getInstance().getActorListDialog().updateActorTree();
		}
	};
	
	private JMenu createInserUiConfigMenu() {
		JMenu toReturn = new JMenu("Config");
		for(File actorFile : EditorKernel.getInstance().getActorListDialog().getActorFiles()) {
			JMenuItem toInsert = new JMenuItem(actorFile.getName().split("\\.")[0]);
			toInsert.addActionListener(new InsertDialogListener(actorFile));
			toReturn.add(toInsert);
		}
		return toReturn;
	}
	
	private UiConfig getUiConfig() {
		return uiConfig;
	}
}
