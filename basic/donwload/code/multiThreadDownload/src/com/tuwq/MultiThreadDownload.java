package com.tuwq;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.RandomAccess;

public class MultiThreadDownload {
	
	// ����ͼƬ��ַ
	private static String path = "http://blog.img.tuwq.cn/upload/user/avatar/11E68E08859F3D3ED8123CA35AB08B6F.jpg";
	// �߳�����
	private static Integer threadCount = 3;
	/**
	 * ������ȡҪ���ص��ļ�����
	 * �ڱ��ش���һ��һ������ļ�
	 * �����̵߳����ط�Χ
	 * ����Rangeͷ,�ü���õĿ�ʼ�����ͽ����������������������
	 * seek����ͬ��startIndexλ��д������
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			URL url = new URL(path);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(10000);
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				int contentLength = connection.getContentLength();
				// �ڱ��ش���һ��һ������ļ�
				RandomAccessFile file = new RandomAccessFile(getFileName(path), "rw");
				file.setLength(contentLength);
				int blockSize = contentLength/threadCount;
				// �����̵߳����ط�Χ
				for (int i = 0; i < threadCount; i++) {
					int startIndex = i * blockSize;
					int endIndex = (i + 1) * blockSize - 1;
					if (i == threadCount - 1) {
						// ˵�������һ���߳�
						endIndex = contentLength - 1;
					}
					new DonwloadThread(startIndex, endIndex, i).start();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static class DonwloadThread extends Thread {
		private int startIndex;
		private int endIndex;
		private int threadId;
		
		public DonwloadThread(int startIndex, int endIndex, int threadId) {
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.threadId = threadId;
		}

		@Override
		public void run() {
			try {
				URL url = new URL(path);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(10000);
				// ����Rangeͷ,�ü���õĿ�ʼ�����ͽ����������������������
				connection.setRequestProperty("Range", "bytes="+startIndex+"-" + endIndex);
				int responseCode = connection.getResponseCode();
				if (responseCode == 206) {
					System.out.println("�߳�" + threadId + "��ʼ����" + startIndex);
					InputStream inputStream = connection.getInputStream();
					int len = -1;
					byte[] buffer = new byte[1024];
					RandomAccessFile file = new RandomAccessFile(getFileName(path), "rw");
					// seek����ͬ��startIndexλ��д������
					file.seek(startIndex);
					//int saveCount = 0;
					while((len = inputStream.read(buffer))!=-1) {
						file.write(buffer, 0, len);
						// ��¼����λ��
						/*saveCount += len;
						int postion = saveCount + startIndex;
						// �洢����λ��,rwd�������ļ����̴洢,������л���
						RandomAccessFile temp = new RandomAccessFile(getFileName(path)+"_"+threadId+".log", "rwd");
						temp.write(String.valueOf(postion).getBytes());*/
					};
					file.close();
					System.out.println("�߳�" + threadId + "���ؽ���" + endIndex);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String getFileName(String path) {
		String[] result = path.split("/");
		return result[result.length - 1];
	}
}
