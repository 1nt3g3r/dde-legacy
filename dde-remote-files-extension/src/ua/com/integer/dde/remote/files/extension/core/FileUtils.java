package ua.com.integer.dde.remote.files.extension.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.compression.CRC;

public class FileUtils {
	public static int getCRCChecksum(File file) {
		CRC crc = new CRC();
		
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int count = 0;
			while((count = input.read(buffer)) > 0) {
				crc.Update(buffer, 0, count);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException shouldNotHandle) {}
		}
		return crc.GetDigest();
	}
	
	public static FileList generateFileList(File base) {
		String root = base.getAbsolutePath();
		
		FileList result = new FileList();
		result.files = new Array<RemoteFile>();
		
		Array<File> files = new Array<File>();
		addFilesRecursive(files, base);
		for(File file : files) {
			Array<String> pathElements = new Array<String>();
			File parent = file;
			String folder = "";
			while((parent = parent.getParentFile()) != base) {
				if (root.equals(parent.getAbsolutePath())) {
					for(int i = 0; i < pathElements.size; i++) {
						folder += pathElements.get(i);
						if (i != pathElements.size-1) {
							folder += "/";
						}
					}
					break;
				} else {
					pathElements.add(parent.getName());
				}
			}

			String filename = file.getName();
			int crc = getCRCChecksum(file);
			
			if (!filename.equals("filelist.json")) {
				RemoteFile tmpRemote = new RemoteFile(folder, filename, crc);
				result.files.add(tmpRemote);
			}
		}
		
		return result;
	}
	
	
	public static void addFilesRecursive(Array<File> result, File base) {
		if (base.isFile()) {
			result.add(base);
		} else {
			for(File file : base.listFiles()) {
				addFilesRecursive(result, file);
			}
		}
	}
	
	public static void main(String[] args) {
		FileList list = generateFileList(new File("/home/integer/farm-game/data"));
		String json = list.toJson();
		System.out.println(json);
		
	}
}
