package ua.com.integer.dde.net.file.sync;

import com.badlogic.gdx.Gdx;

import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.res.screen.ScreenEvent;
import ua.com.integer.dde.res.screen.ScreenListener;

public class SyncScreen extends AbstractScreen implements SyncListener {
	private SyncListener sListener;
	private FolderSynchronizer fSync;
	private float syncPercent;
	
	class SynchroScreenListener implements ScreenListener {
		@Override
		public void eventHappened(AbstractScreen screen, ScreenEvent event) {
			switch(event) {
			case SHOW:
				if (sListener != null && fSync != null) {
					new Thread() {
						public void run() {
							fSync.synchronize(SyncScreen.this);
						}
					}.start();
				}
				break;
			default:
				break;
			}
		}
	}
	
	public SyncScreen() {
		addScreenEventListener(new SynchroScreenListener());
	}
	
	private Runnable beginSyncRunnable = new Runnable() {
		@Override
		public void run() {
			sListener.beginSync();
		}
	};
	
	private Runnable percentSyncRunnable = new Runnable() {
		@Override
		public void run() {
			sListener.percentSync(syncPercent);
		}
	};
	
	private Runnable endSyncRunnable = new Runnable() {
		public void run() {
			if (sListener == null) return;
			sListener.endSync();
			fSync = null;
			sListener = null;
		}
	};
	
	private Runnable failSyncRunnable = new Runnable() {
		public void run() {
			if (sListener == null) return;
			sListener.failSync();
			fSync = null;
			sListener = null;
		}
	};
	
	private Runnable dontNeedSyncRunnable = new Runnable() {
		public void run() {
			if (sListener == null) return;
			sListener.dontNeedSync();
			fSync = null;
			sListener = null;
		}
	};
	
	private Runnable noInetRunnable = new Runnable() {
		public void run() {
			if (sListener == null) return;
			sListener.noInet();
			fSync = null;
			sListener = null;
		}
	};
	
	public void setSyncParams(String baseFolder, String remoteUrl, SyncListener sListener) {
		fSync = new FolderSynchronizer(baseFolder, remoteUrl);
		this.sListener = sListener;
	}

	@Override
	public void beginSync() {
		Gdx.app.postRunnable(beginSyncRunnable);
	}

	@Override
	public void percentSync(float percent) {
		syncPercent = percent;
		Gdx.app.postRunnable(percentSyncRunnable);
	}

	@Override
	public void endSync() {
		Gdx.app.postRunnable(endSyncRunnable);
	}

	@Override
	public void failSync() {
		Gdx.app.postRunnable(failSyncRunnable);
	}

	@Override
	public void dontNeedSync() {
		Gdx.app.postRunnable(dontNeedSyncRunnable);
	}

	@Override
	public void noInet() {
		Gdx.app.postRunnable(noInetRunnable);
	}
}
