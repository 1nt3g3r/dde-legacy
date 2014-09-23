package ua.com.integer.dde.startpanel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

/**
 * Простая утилита для проверки целостности файлов и директорий. 
 * Использует класс CRC32 из стандартной библиотеки
 * 
 * @author 1nt3g3r
 */
public class Modify {
	/**
	 * Возвращает контрольную сумму файла
	 * @param file - файл для проверки
	 * @return 64-битное число - контрольную сумму файла
	 */
	public long getFileCrc(File file) throws IOException {
		CRC32 crc = new CRC32();
		
		FileInputStream fStream = new FileInputStream(file);
		crc.update(file.getName().getBytes());
		
		byte[] readBuffer = new byte[4096];
		int readCount;
		while((readCount = fStream.read(readBuffer)) > 0) {
			crc.update(readBuffer, 0, readCount);
		}
		
		fStream.close();
		return crc.getValue();
	}
	
	/**
	 * Возвращает контрольную сумму для директории. Она считается следующим образом: 
	 * контрольные суммы всех файлов в директории складываются в строку, потом считается и 
	 * возвращается контрольная сумма этой строки
	 * 
	 * @param folder каталог для проверки
	 * @return контрольную сумму
	 */
	public long getFolderCrc(File folder) throws IOException {
		if (!folder.isDirectory()) {
			throw new IllegalArgumentException("File should be directory!");
		}
		
		String totalCrc = "";
		for(File file : folder.listFiles()) {
			if (file.isDirectory()) {
				totalCrc += getFolderCrc(file);
			} else {
				totalCrc += getFileCrc(file);
			}
		}
		
		CRC32 crc = new CRC32();
		crc.update(totalCrc.getBytes());
		return crc.getValue();
	}
}
