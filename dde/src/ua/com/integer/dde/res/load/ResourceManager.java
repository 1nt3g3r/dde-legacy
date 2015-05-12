package ua.com.integer.dde.res.load;

import ua.com.integer.dde.res.font.TTFFontManager;
import ua.com.integer.dde.res.graphics.TextureManager;
import ua.com.integer.dde.res.load.imp.MusicManager;
import ua.com.integer.dde.res.load.imp.SoundManager;
import ua.com.integer.dde.res.screen.ScreenManager;

/**
 * Менеджер ресурсов для стандартных ресурсов. Стандартные ресурсы включают в себя следующие:
 * 
 * 1) Текстуры
 * 2) Звуки
 * 3) Музыка
 * 4) Шрифты
 * 5) Экраны
 * 
 * @author 1nt3g3r
 */
public class ResourceManager extends CompositeLoadManager {
	public TextureManager atlases() {
		return getManager(TextureManager.class);
	}
	
	public SoundManager sounds() {
		return getManager(SoundManager.class);
	}
	
	public MusicManager musics() {
		return getManager(MusicManager.class);
	}
	
	public TTFFontManager fonts() {
		return getManager(TTFFontManager.class);
	}
	
	public ScreenManager screens() {
		return getManager(ScreenManager.class);
	}
	
	public void startLoading() {
		sounds().startLoadingInSeparateThread();
		musics().startLoadingInSeparateThread();
	}
}
