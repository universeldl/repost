package cn.itproject.crm.util.fdfs;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

/**
 * 把图片上传到FastDFS
 * 
 * @author yangpeixin
 * 
 * @Date 2017年6月22日
 *
 *       version 1.0
 */
public class FastDFSUtils {

	public static String uploadPIC(byte[] pic, String name, long size) {
		String path = null;
		// ClientGloble 读配置文件
		ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
		try {
			ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
			// 老大客户端
			TrackerClient trackerClient = new TrackerClient();
			TrackerServer trackerServer = trackerClient.getConnection();
			StorageServer storageServer = null;
			StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
			String ext = FilenameUtils.getExtension(name);

			NameValuePair[] meta_list = new NameValuePair[3];
			meta_list[0] = new NameValuePair("fileName", name);
			meta_list[1] = new NameValuePair("fileExt", ext);
			meta_list[2] = new NameValuePair("fileSize", String.valueOf(size));

			// group1/M00/00/01/wKjIgFWOYc6APpjAAAD-qk29i78248.jpg
			path = storageClient1.upload_file1(pic, ext, meta_list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

}
