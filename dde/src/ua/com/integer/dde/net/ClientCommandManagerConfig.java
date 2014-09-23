package ua.com.integer.dde.net;

import java.util.Map;


/**
 * Конфигурационный класс для менеджера команд 
 * для клиентской стороны
 * 
 * @author integer
 */
public class ClientCommandManagerConfig {
	/**
	 * Адрес удаленного сервера
	 */
	public String serverHost;
	/**
	 * Порт на удаленном сервере
	 */
	public int serverPort;
	/**
	 * Строка-разделитель частей входящей команды
	 */
	public String incomingMessagePartDelimiter = " ";
	/**
	 * Строка, которая добавляется в конец каждого отправленного сообщения
	 */
	public String outcomingMessageFinalPart = "\r\n";
	/**
	 * Обработчик, который срабатывает до вызова обработчика по умолчанию
	 */
	public IncomingCommandHandler beforeIncomingCommandHandler;
	/**
	 * Обработчик, который срабатывает на неизвестные команды
	 */
	public IncomingCommandHandler unhandledIncomingCommandHandler;
	/**
	 * Обработчик, который срабатывает на отправленные команды до их отправки
	 */
	public IncomingCommandHandler beforeCommandSendCommandHandler;
	/**
	 * Обработчики для входящих команд
	 */
	public Map<String, IncomingCommandHandler> commandHandlers;
}
