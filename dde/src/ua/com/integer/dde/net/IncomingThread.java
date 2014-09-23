package ua.com.integer.dde.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Timer.Task;


/**
 * Поток для обслуживания входящих сообщений
 * 
 * @author integer
 */
class IncomingThread extends Task {
	private BufferedReader inputStream;
	private ObjectMap<String, IncomingCommandHandler> commandHandlers;
	private Array<String> incomingMessages;
	private String delimiter = " ";
	private IncomingCommandHandler beforeCommandHandler;
	private IncomingCommandHandler unhandledCommandHandler;
	
	/**
	 * Установить обработчика, который обрабатывает сообщения до 
	 * его поступления к дальнейшим обработчикам. Удобно для логгирования всех 
	 * входящих команд
	 */
	public void setBeforeCommandHandler(IncomingCommandHandler beforeCommandHandler) {
		this.beforeCommandHandler = beforeCommandHandler;
	}
	
	/**
	 * Установить обработчика для неизвестных команд, для которых нет зарегистрированных обработчиков. 
	 */
	public void setUnhandledCommandHandler(IncomingCommandHandler unhandledCommandHandler) {
		this.unhandledCommandHandler = unhandledCommandHandler;
	}
	
	/**
	 * Установить разделитель частей команды
	 * @param delimiter строка-разделитель
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
	/**
	 * Добавить сообщение в очередь на обработку. Полезно 
	 * при отладке
	 * @param message строка сообщения
	 */
	public void addIncomingMessage(String message) {
		incomingMessages.add(message);
	}
	
	/**
	 * @throws IOException в случае, когда не получается получить поток для чтения данных
	 */
	public IncomingThread(Socket socket) throws IOException {
		inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		incomingMessages = new Array<String>();
		commandHandlers = new ObjectMap<String, IncomingCommandHandler>();
	}
	
	/**
	 * Установить обработчик для определенной команды
	 * @param command текстовое представление команды
	 * @param handler обработчик для этой команды
	 */
	public void addCommandHandler(String command, IncomingCommandHandler handler) {
		commandHandlers.put(command, handler);
	}
	
	private String message;
	
	@Override
	public void run() {
		try {
			while(inputStream.ready()) {
				message = inputStream.readLine();
				incomingMessages.add(message);
				handleIncomingMessages();
			}
		} catch (IOException e) {
			Gdx.app.error("net", "Error during handle incoming message: " + e);
		}
	}
	
	private boolean messageHandled;
	private void handleIncomingMessages() {
		for(String message : incomingMessages) {
			messageHandled = false;
			if (beforeCommandHandler != null) {
				beforeCommandHandler.onCommand(message);
			}
			String command = message.split(delimiter)[0];
			IncomingCommandHandler handler = commandHandlers.get(command);
			if (handler != null) {
				handler.onCommand(message);
				messageHandled = true;
			} else {
				if (unhandledCommandHandler != null && !messageHandled) {
					unhandledCommandHandler.onCommand(message);
				}
			}
		}
		incomingMessages.clear();
	}
};