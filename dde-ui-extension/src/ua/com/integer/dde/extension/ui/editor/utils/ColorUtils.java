package ua.com.integer.dde.extension.ui.editor.utils;

import ua.com.integer.dde.util.GraphicUtils;

import com.badlogic.gdx.graphics.Color;

public class ColorUtils {
	/**
		 * Возвращает AWT цвет, закодированный в строку. Строка - последовательность компонентов от 0 до 1 - 
		 * формат RGBA. Пример красного цвета, закодированного данным методом: "1 0 0 1"
		 * 
		 * @param color AWT цвет
		 * @return строку с цветом
		 */
		public static String encodeAWTColor(java.awt.Color color) {
			Color result = GraphicUtils.createColor(color.getRed(), color.getGreen(), color.getBlue());
			result.a = color.getAlpha()/255f;
			
			return result.r + " " + result.g + " " + result.b + " " + result.a;
		}
		
		/**
		 * Создает и возвращает AWT цвет из строки, закодированной ранее методом encodeAWTColor
		 * @param color
		 * @return
		 */
		public static java.awt.Color decodeToAWTColor(String color) {
			Color gdxColor = GraphicUtils.decodeToGDXColor(color);
			return new java.awt.Color(gdxColor.r, gdxColor.g, gdxColor.b, gdxColor.a);
		}
}

