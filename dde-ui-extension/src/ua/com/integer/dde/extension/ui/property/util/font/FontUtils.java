package ua.com.integer.dde.extension.ui.property.util.font;

import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.extension.ui.size.SizeType;
import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.res.screen.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 * Вспомогательный класс для работы со шрифтами
 * 
 * @author 1nt3g3r
 */
public class FontUtils {
	/**
	 * Возвращает список доступных шрифтов. Для работы этого метода 
	 * уже должно быть создано и инициализировано ядро
	 */
	public static String[] getFontNames() {
		DDKernel kernel = AbstractScreen.getKernel();
		Array<String> fontNamesArray = new Array<String>();
		
		for(FileHandle handle : Gdx.files.internal(kernel.getConfig().fontDirectory).list("ttf")) {
			fontNamesArray.add(handle.nameWithoutExtension());
		}
		
		String[] toReturn = new String[fontNamesArray.size];
		int index = -1;
		for(String fontName : fontNamesArray) toReturn[++index] = fontName;
		
		return toReturn;
	}
	
	/**
	 * Возвращает список доступных шрифтов. Первым идет шрифт "standard" - встроенный шрифт Roboto-Condensed 
	 * с поддержкой киррилицы
	 * @return
	 */
	public static String[] getFontNamesWithStandard() {
		String[] tmp = getFontNames();
		String[] toReturn = new String[tmp.length + 1];
		
		toReturn[0] = "standard";
		
		int index = 0;
		for(String fontName : tmp) {
			toReturn[++index] = fontName;
		}
		
		return toReturn;
	}
	
	/**
	 * Возвращает размер шрифта по умолчанию
	 */
	public static Size getDefaultFontSize() {
		Size defaultFontSize = new Size();
		
		defaultFontSize.setType(SizeType.SCREEN_WIDTH);
		defaultFontSize.setSizeValue(0.05f);
		
		return defaultFontSize;
	}
}
