package ua.com.integer.dde.extension.ui.editor.property.edit.base;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;

/**
 * Интерфейс для редактора свойств. Отдельные компоненты, что реализуют этот 
 * интерфейс, предназначены для редактирования определенных типов свойств - например, 
 * редактирование цвета\чисел\строк и т. д.
 * 
 * @author 1nt3g3r
 */
public interface PropertyEditComponent {
	/**
	 * Установить конфиг, для которого нужно редактировать свойство
	 */
	public void setConfig(UiConfig config);
	/**
	 * Имя свойства в этом конфиге, которое мы будем редактировать
	 */
	public void setUiPropertyName(String propertyName);
	/**
	 * Название свойства - именно оно будет отображаться в редакторе
	 */
	public void setPropertyName(String propertyName);
	/**
	 * Устанавливает слушателя на обработку событий, когда конфиг меняется. 
	 * Вы должны вручную вызывать метод propertyChanged() когда вы меняете значение свойства
	 */
	public void setPropertyChangedListener(PropertyChangeListener listener);
	/**
	 * Значение по умолчанию для выбранного свойства
	 * @return
	 */
	public String getDefaultValue();
}
