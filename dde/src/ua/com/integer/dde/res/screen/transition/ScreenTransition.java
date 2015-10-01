package ua.com.integer.dde.res.screen.transition;

import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.res.screen.AbstractScreen;

public abstract class ScreenTransition {
	protected Class<? extends AbstractScreen> next;
	
	public void transit(Class<? extends AbstractScreen> next) {
		this.next = next;

		beforeTransaction();
		
		performTransition();
	};
	
	public void beforeTransaction() {
	}
	
	public abstract void performTransition();
	
	protected DDKernel getCore() {
		return AbstractScreen.getKernel();
	}
	
	protected AbstractScreen getNextScreen() {
		return getCore().getScreen(next);
	}
	
	public ScreenTransition create() {
		return this;
	}
}
