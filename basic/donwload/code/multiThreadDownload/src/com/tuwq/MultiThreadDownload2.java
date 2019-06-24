package com.tuwq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.temporal.TemporalField;

public class MultiThreadDownload2 {
		// 下载图片地址
		private static String path = "http://blog.img.tuwq.cn/upload/user/avatar/11E68E08859F3D3ED8123CA35AB08B6F.jpg";
		// 线程数量
		private static Integer threadCount = 3;
		/**
		 * 联网获取要下载的文件长度
		 * 在本地创建一个一样大的文件
		 * 计算线程的下载范围
		 * 设置Range头,用计算好的开始索引和结束索引到服务端请求数据
		 * seek到不同的startIndex位置写入数据
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
					// 在本地创建一个一样大的文件
					RandomAccessFile file = new RandomAccessFile(getFileName(path), "rw");
					file.setLength(contentLength);
					int blockSize = contentLength/threadCount;
					// 计算线程的下载范围
					for (int i = 0; i < threadCount; i++) {
						int startIndex = i * blockSize;
						int endIndex = (i + 1) * blockSize - 1;
						if (i == threadCount - 1) {
							// 说明是最后一个线程
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
					// 读取出记录下来的位置
					File temp = new File(getFileName(path) + "_" + threadId + ".log");
					if (temp != null && temp.length() > 0) {
						// 说明日志文件有内容
						FileInputStream fis = new FileInputStream(temp);
						BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
						String result = reader.readLine();
						// 读取记录下来的位置更新下载请求数据的起始位置
						startIndex = Integer.parseInt(result);
						fis.close();
						reader.close();
					}
					
					URL url = new URL(path);
					HttpURLConnection connection = (HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(10000);
					// 设置Range头,用计算好的开始索引和结束索引到服务端请求数据
					connection.setRequestProperty("Range", "bytes="+startIndex+"-" + endIndex);
					int responseCode = connection.getResponseCode();
					if (responseCode == 206) {
						System.out.println("线程" + threadId + "开始下载" + startIndex);
						InputStream inputStream = connection.getInputStream();
						int len = -1;
						byte[] buffer = new byte[1024];
						RandomAccessFile file = new RandomAccessFile(getFileName(path), "rw");
						// seek到不同的startIndex位置写入数据
						file.seek(startIndex);
						int saveCount = 0;
						while((len = inputStream.read(buffer))!=-1) {
							file.write(buffer, 0, len);
							// 记录下载位置
							saveCount += len;
							int postion = saveCount + startIndex;
							// 存储下载位置,rwd表明该文件立刻存储,不会进行缓存
							RandomAccessFile tempFile = new RandomAccessFile(getFileName(path)+"_"+threadId+".log", "rwd");
							tempFile.write(String.valueOf(postion).getBytes());
							tempFile.close();
						};
						inputStream.close();
						file.close();
						System.out.println("线程" + threadId + "下载结束" + endIndex);
						// 删除对应的日志文件
	                    if (temp != null) {
	                        temp.delete();;
	                    }
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
