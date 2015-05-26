package ua.com.integer.dde.extension.ui.editor.shortcut;

import ua.com.integer.dde.extension.ui.editor.EditorKernel;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.ObjectMap;

public class KeyboardListener extends InputListener {
	private Shortcut tmpShortcut = new Shortcut();
	private ObjectMap<Shortcut, Runnable> shortcuts = new ObjectMap<Shortcut, Runnable>();
	
	private boolean control, alt, shift;
	
	public KeyboardListener() {
		shortcuts.put(new Shortcut(Keys.DEL), new ExecuteCommandRunnable("rm"));
		shortcuts.put(new Shortcut(Keys.F2), new RenameRunnable());
		shortcuts.put(new Shortcut(Keys.C).ctrl(true), new ExecuteCommandRunnable("copy"));
		shortcuts.put(new Shortcut(Keys.V).ctrl(true), new ExecuteCommandRunnable("paste"));
		shortcuts.put(new Shortcut(Keys.Z).ctrl(true), new UndoActionRunnable());
	}
	
	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		if (EditorKernel.editorScreen().isCommandFieldVisible()) {
			return false;
		}
		
		if (keycode == Keys.CONTROL_LEFT || keycode == Keys.CONTROL_RIGHT) {
			control = true;
			return true;
		}
		
		if (keycode == Keys.ALT_LEFT || keycode == Keys.ALT_RIGHT) {
			alt = true;
			return true;
		}
		
		if (keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT) {
			shift = true;
			return true;
		}
		
		tmpShortcut.key(keycode);
		tmpShortcut.ctrl(control);
		tmpShortcut.alt(alt);
		tmpShortcut.shift(shift);
		
		Runnable shortcutRunnable = shortcuts.get(tmpShortcut);
		if (shortcutRunnable != null) {
			shortcutRunnable.run();
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean keyUp(InputEvent event, int keycode) {
		if (keycode == Keys.CONTROL_LEFT || keycode == Keys.CONTROL_RIGHT) {
			control = false;
			return true;
		}
		
		if (keycode == Keys.ALT_LEFT || keycode == Keys.ALT_RIGHT) {
			alt = false;
			return true;
		}
		
		if (keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT) {
			shift = false;
			return true;
		}
		
		return true;
	}
}
