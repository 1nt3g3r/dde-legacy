package ua.com.integer.dde.startpanel;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * 
 * @author Мельничук Іван Володимирович aka 1nt3g3r
 * 
 * @since 05.01.2012
 * 
 * Клас для роботи з фреймами(розміщення фрейма по центру вікна, повернення координат центру екрану)
 */
public class FrameTools {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static int screenCenterX = screenSize.width / 2;
	private static int screenCenterY = screenSize.height / 2;
	
	/**
	 * Розміщує фрейм по центру екрану, враховуючи при цьому розміри фрейму.
	 * Для знаходження розміру екрану застосовується клас Toolkit
	 * 
	 * @param frame фрейм для розміщення
	 */
	public static void situateOnCenter(JFrame frame) {
		int frameWidth = frame.getSize().width;
		int frameHeight = frame.getSize().height;
		
		int frameX = screenCenterX - frameWidth / 2;
		int frameY = screenCenterY - frameHeight / 2;
		
		Point frameLocation = new Point(frameX, frameY);
		
		frame.setLocation(frameLocation);
	}
	
	/**
	 * Повертає точку, в якій необхідно розмістити фрейм для його позиціювання по центру
	 * 
	 * @param size розмір фрейму(можливо, іншого "важкого" компонента)
	 * 
	 * @return точку, де потрібно розміщувати компонент
	 */
	public static Point getCenterForSelectedSize(Dimension size) {
		return new Point(screenCenterX - size.width/2, screenCenterY - size.height/2);
	}
	
	/**
	 * Повертає об'єкт типу Dimension, який потім можна застосувати для встановлення розмірів будь-якого компонента
	 * Розмір виходить в четверть екрана - половина довжини екрану в пікселях і половина висоти екрану в пікселях
	 * 
	 * @return об'єкт із розміром типу Dimension
	 */
	public static Dimension getQuarterOfScreen() {
		return new Dimension(screenSize.width / 2, screenSize.height / 2);
	}
	
	/**
	 * Повертає об'єкт Dimension, у якого довжина найбільша з двох довжин
	 * і висота найбільша з двох висот
	 * 
	 * @param d1 перший об'єкт Dimension
	 * @param d2 другий об'єкт Dimension
	 * 
	 * @return об'єкт, скомпонований з двох об'єктів.
	 */
	public static Dimension getMaxDimension(Dimension d1, Dimension d2) {
		int maxX = d1.width > d2.width ? d1.width : d2.width;
		int maxY = d1.height > d2.height ? d1.height : d2.height;
		return new Dimension(maxX, maxY);
	}
	
	/**
	 * Для обох об'єктів встановлює максимальні розміри для висоти і довжини
	 * Приклад
	 * Dimension d1 = new Dimension(10, 20);
	 * Dimension d2 = new Dimension(15, 10);
	 * В результаті виклику setMaxDimension(d1, d2); ми отримаємо:
	 * d1 = (15, 20);
	 * d2 = (15, 20);
	 * 
	 * @param d1 перший об'єкт
	 * @param d2 другий об'єкт
	 */
	public static void setMaxDimension(Dimension d1, Dimension d2) {
		Dimension max = getMaxDimension(d1, d2);
		d1.setSize(max);
		d2.setSize(max);
	}
	
	/**
	 * Встановлює всім об'єктам максимальний розмір
	 * Працює аналогічно setMaxDimension(Dimension d1, Dimension d2). але для списку об'єктів
	 * 
	 * @param dimensions список об'єктів
	 */
	public static void setMaxDimension(List<Dimension> dimensions) {
		if (dimensions.size() < 2) {
			return;
		}
		
		Dimension max = dimensions.get(0);
		for(Dimension dim : dimensions) {
			max = getMaxDimension(max, dim);
		}
		
		for(Dimension dim : dimensions) {
			dim.setSize(max);
		}
	}
	
	/**
	 * Встановлює для всіх об'єктів розмір по зразку
	 * 
	 * @param example зразок
	 * @param others об'єкти, розмір яких буде встановлено по зразку
	 */
	public static void setThisDimensionForOther(Dimension example, List<Dimension> others) {
		for(Dimension d : others) {
			d.setSize(example);
		}
	}
	
	/**
	 * Встановлює максимальну висоту компонента не більшою, ніж найкраща
	 * Це означає, що текстові поля і комбобокси не стануть вищими, ніж це потрібно
	 * 
	 * @param component компонент для встановлення нормальних розмірів
	 */
	public static void setPreferredHeight(JComponent component) {
		Dimension maximumSize = component.getMaximumSize();
		Dimension preferredSize = component.getPreferredSize();
		Dimension goodSize = new Dimension(maximumSize.width, preferredSize.height);
		component.setMaximumSize(goodSize);
	}
	
	/**
	 * Показує вікно з компонентом component
	 * При закритті вікна програма завершує роботу
	 * Зручно для тестування
	 * 
	 * @param component який компонент буде знаходитись у вікні
	 */
	public static void testingFrame(JComponent component) {
		JFrame frame = new JFrame("Вікно з набору інструментів класу FrameTools");
		frame.add(component);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		situateOnCenter(frame);
	}

	/**
	 * Розміщує діалог по центру екрана, не змінюючи його розмірів і видимості.
	 * 
	 * @param dialog діалог для розміщення
	 */
	public static void situateOnCenter(JDialog dialog) {
		int frameWidth = dialog.getSize().width;
		int frameHeight = dialog.getSize().height;
		
		int frameX = screenCenterX - frameWidth / 2;
		int frameY = screenCenterY - frameHeight / 2;
		
		Point frameLocation = new Point(frameX, frameY);
		
		dialog.setLocation(frameLocation);
	}
	
	public static void setAsExample(JComponent example, JComponent ... toSet) {
		for(JComponent s : toSet) {
			s.setMaximumSize(example.getMaximumSize());
			s.setMinimumSize(example.getMinimumSize());
			s.setPreferredSize(example.getPreferredSize());
		}
	}
}
