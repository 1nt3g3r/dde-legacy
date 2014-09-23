package ua.com.integer.dde.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Класс-поток для отправки сообщений
 * 
 * @author integer
 */
class OutcomingThread extends Task {
	private Array<String> outcomingMessages;
	private IncomingCommandHandler beforeCommandSendHandler;
	private PrintWriter printWriter;
	
	/**
	 * Устанавливает обработчика для команды, который 
	 * сработает перед отправкой команды на сервер. 
	 * Удобно для логгирования всех исходящих сообщений
	 * 
	 * @param beforeCommandSendHandler
	 */
	public void setBeforeCommandSendHandler(IncomingCommandHandler beforeCommandSendHandler) {
		this.beforeCommandSendHandler = beforeCommandSendHandler;
	}
	
	/**
	 * @throws IOException в случае, когда не удается получить поток для записи
	 */
	public OutcomingThread(Socket socket) throws IOException {
		printWriter = new PrintWriter(socket.getOutputStream(), true);
		outcomingMessages = new Array<String>();
	}
	
	/**
	 * Добавить сообщение в очередь на отправку
	 * 
	 * @param message строка сообщения
	 */
	public void addOutcomingMessage(String message) {
		outcomingMessages.add(message);
	}

	@Override
	public void run() {
		if (outcomingMessages.size > 0) {
			for (String message : outcomingMessages) {
				if (beforeCommandSendHandler != null) {
					beforeCommandSendHandler.onCommand(message);
				}
				printWriter.println(message);
			}
			outcomingMessages.clear();
		}
	}
	
	public boolean hasMessagesToSend() {
		return outcomingMessages.size > 0;
	}
};
