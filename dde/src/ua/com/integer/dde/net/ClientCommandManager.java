package ua.com.integer.dde.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;


/**
 * Простой менеджер команд для клиентской стороны. Позволяет 
 * создать соединение с сервером на определенном порту, отсылать команды, 
 * принимать входящие команды. Отсылка и прием команд ведется асинхронно, в 
 * разных потоках
 * 
 * @author integer
 */
public class ClientCommandManager {
	private IncomingThread incomingThread;
	private OutcomingThread outcomingThread;
	private Socket socket;
	private String finalPart = "\r\n";
	
	/**
	 * Устанавливает обработчика для команды, который 
	 * сработает перед отправкой команды на сервер. 
	 * Удобно для логгирования всех исходящих сообщений
	 * 
	 * @param beforeCommandSendHandler
	 */
	public void setBeforeCommandSendHandler(IncomingCommandHandler beforeCommandSendHandler) {
		outcomingThread.setBeforeCommandSendHandler(beforeCommandSendHandler);
	}
	
	/**
	 * Установить обработчика, который обрабатывает входящие сообщения до 
	 * его поступления к дальнейшим обработчикам. Удобно для логгирования всех 
	 * входящих команд
	 */
	public void setBeforeCommandReceiveHandler(IncomingCommandHandler beforeCommandHandler) {
		incomingThread.setBeforeCommandHandler(beforeCommandHandler);
	}
	
	/**
	 * Установить обработчика для неизвестных входящих команд, для которых нет зарегистрированных обработчиков. 
	 */
	public void setUnhandledCommandHandler(IncomingCommandHandler unhandledCommandHandler) {
		incomingThread.setUnhandledCommandHandler(unhandledCommandHandler);
	}
	
	/**
	 * Устанавливает строку, которая будет добавлена к 
	 * каждой исходящей комманде. По умолчанию, пустая
	 * @param finalPart
	 */
	public void setFinalPart(String finalPart) {
		this.finalPart = finalPart;
	}
	
	/**
	 * Устанавливает разделитель между частями входящей команды. 
	 * 
	 * @param delimiter разделитель
	 */
	public void setDelimiter(String delimiter) {
		incomingThread.setDelimiter(delimiter);
	}
	
	/**
	 * Устанавливает обработчик для входящей команды
	 * @param command первое слово в команде
	 * @param handler обработчик
	 */
	public void addCommandHandler(String command, IncomingCommandHandler handler) {
		incomingThread.addCommandHandler(command, handler);
	}
	
	/**
	 * Имитация прихода команды от сервера. Полезно при отладке
	 * @param cmd текстовая строка
	 */
	public void addRawCommand(String cmd) {
		incomingThread.addIncomingMessage(cmd);
	}
	
	/**
	 * Отправить команду на сервер
	 * @param cmd команда для отправки
	 */
	public void sendCommand(OutcomingCommand cmd) {
		outcomingThread.addOutcomingMessage(cmd.getCommand() + finalPart);
	}
	
	public void sendCommand(String cmd) {
		outcomingThread.addOutcomingMessage(cmd + finalPart);
	}
	
	/**
	 * Создает менеджера команд и берет настройки из обьекта конфигурационного 
	 * класса
	 * @param config обьект с настройками
	 * @throws IOException 
	 * @throws UnknownHostException в случае, если не найден сервер с указанным адресом
	 */
	public ClientCommandManager(ClientCommandManagerConfig config) throws UnknownHostException, IOException {
		setConfig(config);
	}

	/**
	 * Создает менеджера команд, который может как отправлять, так и принимать сообщения. 
	 * Сразу открывается подключение к указанному серверу на указанный порт
	 * @param host адрес сервера
	 * @param port порт на сервере
	 * @throws UnknownHostException когда сервер не найден
	 * @throws IOException когда ошибка соединения
	 */
	public ClientCommandManager(String host, int port) throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		initThreads();
	}
	
	public ClientCommandManager() {
	}
	
	public void setConfig(ClientCommandManagerConfig config) throws UnknownHostException, IOException {
		close();
		
		socket = new Socket(config.serverHost, config.serverPort);
		setDelimiter(config.incomingMessagePartDelimiter);
		setFinalPart(config.outcomingMessageFinalPart);
		setBeforeCommandReceiveHandler(config.beforeIncomingCommandHandler);
		setUnhandledCommandHandler(config.unhandledIncomingCommandHandler);
		setBeforeCommandSendHandler(config.beforeCommandSendCommandHandler);
		
		if (config.commandHandlers != null) {
			for(String commandName : config.commandHandlers.keySet()) {
				addCommandHandler(commandName, config.commandHandlers.get(commandName));
			}
		}
		
		initThreads();
	}
	
	private void initThreads() throws IOException {
		incomingThread = new IncomingThread(socket);
		Timer.schedule(incomingThread, 0.0f, 0.1f);
		
		outcomingThread = new OutcomingThread(socket);
		Timer.schedule(outcomingThread, 0.0f, 0.1f);
	}
	
	/**
	 * Закрывает сетевое подключение
	 */
	public void close() {
		if (incomingThread != null) {
			incomingThread.cancel();
			incomingThread = null;
		}
		
		if(outcomingThread != null) {
			outcomingThread.cancel();
			outcomingThread = null;
		}
		
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				Gdx.app.error("net", "Error during socket closing!");
			}
			socket = null;
		}
	}
	
	/**
	 * Имеются ли сообщения для отправки. Рекомендуется проверить очередь сообщений 
	 * перед закрытием.
	 * @return
	 */
	public boolean hasMessagesToSend() {
		return outcomingThread.hasMessagesToSend();
	}
	
	/**
	 * Дожидается, пока все сообщения в очереди на отправку будут отправлены, 
	 * и потом закрывает сетевое подключение.
	 */
	public void flushMessagesAndClose() {
		new Thread() {
			public void run() {
				while(hasMessagesToSend()) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {}
				}
				close();
			}
		}.start();
	}
}
