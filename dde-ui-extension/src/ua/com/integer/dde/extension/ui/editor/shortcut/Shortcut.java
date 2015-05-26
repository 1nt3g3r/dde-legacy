package ua.com.integer.dde.extension.ui.editor.shortcut;



public class Shortcut {
	public boolean control, alt, shift;
	public int key;
	
	public Shortcut(int key) {
		this.key = key;
	}
	
	public Shortcut() {
	}
	
	public Shortcut key(int key) {
		this.key = key;
		return this;
	}
	
	public Shortcut alt(boolean alt) {
		this.alt = alt;
		return this;
	}
	
	public Shortcut ctrl(boolean control) {
		this.control = control;
		return this;
	}
	
	public Shortcut shift(boolean shift) {
		this.shift = shift;
		return this;
	}
	
	public boolean equals(Object obj) {
		if(obj == null || ! (obj instanceof Shortcut)) {
			return false;
		} else {
			Shortcut other = (Shortcut) obj;
			return  other.key == key &&
					other.control == control && 
					other.alt == alt && 
					other.shift == shift;
		}
	};
	
	@Override
	public int hashCode() {
		return key + (control ? 1 : 0) + (alt ? 1 : 0) + (shift ? 1: 0); 
	}
}
