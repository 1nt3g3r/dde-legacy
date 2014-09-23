package ua.com.integer.dde.startpanel.util.color;

import java.awt.Color;

import javax.swing.JOptionPane;

/**
 * Статические методы для выбора цвета
 * 
 * @author 1nt3g3r
 *
 */
public class ColorDialog {
	/**
	 * Показывает диалоговое окно с выбором цвета
	 * 
	 * @param initialColor начальный цвет
	 * @param colorListener слушатель на изменение цвета
	 * @return выбранный цвет или null если пользователь нажал на "Отмена"
	 */
	public static Color selectColor(Color initialColor, ColorListener colorListener) {
		SelectColorPanel panel = new SelectColorPanel();
		panel.setColor(initialColor);
		panel.setColorListener(colorListener);
		int answer = JOptionPane.showConfirmDialog(null, panel, "Select color", JOptionPane.OK_CANCEL_OPTION);
		
		if (answer == JOptionPane.OK_OPTION) {
			return panel.getColor();
		} else {
			return null;
		}
	}
	
	/**
	 * Показывает диалоговое окно с выбором цвета. Начальный цвет - белый
	 * @param colorListener слушатель на изменение цвета
	 * @return
	 */
	public static Color selectColor(ColorListener colorListener) {
		return selectColor(Color.WHITE, colorListener);
	}
	
	/**
	 * Показывает диалоговое окно с выбором цвета. Начальный цвет - белый
	 * @return
	 */
	public static Color selectColor() {
		return selectColor(null);
	}
}
