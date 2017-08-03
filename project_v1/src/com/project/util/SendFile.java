package com.project.util;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class SendFile {

	private Socket s;

	public SendFile(String host, int port, String file) {
		try {
			s = new Socket(host, port);
			file(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void file(String file) throws IOException {
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[4096];

		while (fis.read(buffer) > 0) {
			dos.write(buffer);
		}

		fis.close();
		dos.close();
	}
}
