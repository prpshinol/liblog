package com.liblog.util;

import com.liblog.exception.FileDownloadException;
import com.liblog.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 文件操作的工具类，包括web文件上传，文件下载
 * @author 21584
 *
 */
public class FileUtil {
		//默认文件上传的路径
	    public static final String UPLOAD_DIR = "upload";
	    
	    //封装常用的content-type
	    public static final String TYPE_ALL="application/octet-stream";
	    public static final String TYPE_WORD="application/msword";
	    public static final String TYPE_EXCEL="application/x-xls";
	    public static final String TYPE_JPEG="image/jpeg";
	    public static final String TYPE_JPG="application/x-jpg";
	    public static final String TYPE_PNG="application/x-png";
	    public static final String TYPE_GIF="image/gif";
	    
	  
	   /**
	    * 将上传的文件重命名，使用uuid算法
	    * @param name
	    * @return
	    */
	    private static String rename(String name) {  
	    
	        String fileName = UUID.randomUUID().toString();  
	  
	        if (name.indexOf(".") != -1) {  
	            fileName += name.substring(name.lastIndexOf("."));  
	        }  

	        return fileName;  
	    }
	    
	    /**
	     * 使用uuid算法生成唯一的jpg文件名
	     * @return
	     */
	    private static String renameJpg() {  
	    	
	    	String fileName = UUID.randomUUID().toString();  
	    	return fileName+".jpg";  
	    }  
	    
	    /**
	     * 使用uuid算法生成唯一的png文件名
	     * @return
	     */
	    public static String renamePng() {  
	    	
	    	String fileName = UUID.randomUUID().toString();  
	    	return fileName+".png";  
	    }  
	  
	    /**
	     * 上传文件(可上传多个文件)
	     * @param request
	     * @param suffix 文件名后缀
	     * @return	文件上传后的路径列表
	     * @throws FileUploadException 
	     */
	    public static List<String> upload(HttpServletRequest request,String[] suffix) throws FileUploadException {
	  
	        try {
				List<String> pathList=new ArrayList<String>();
  
				MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
				Map<String, MultipartFile> fileMap = mRequest.getFileMap();
  
				String uploadDir = request.getSession().getServletContext()  
				        .getRealPath("/")  
				        +UPLOAD_DIR;  
				File file = new File(uploadDir);  
  
				if (!file.exists()) {  
				    file.mkdir();  
				}  
  
				String fileName = null;  
				
				//判断文件类型
				if (!judgeFileType(fileMap, suffix)) {
					throw new FileUploadException("文件类型有误！");
				}
				
				for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet()
				        .iterator(); it.hasNext();) {  
  
				    Map.Entry<String, MultipartFile> entry = it.next();
				    MultipartFile mFile = entry.getValue();
				    
				    //多文件上传时，判断是否为空
				    if (!mFile.getOriginalFilename().equals("")) {
				    	fileName = mFile.getOriginalFilename();  
				  	  
				        String newName = rename(fileName);  
				        String filePath=request.getServletContext().getRealPath("/"+UPLOAD_DIR)+"/"+newName;
				        String webPath=UPLOAD_DIR+"/"+newName;
  
				        // 上传文件 
				        File newFile=new File(filePath);
				        mFile.transferTo(newFile);
  
				       
				        pathList.add(webPath);	
					}
				}  
				return pathList;
			} catch (Exception e) {
				e.printStackTrace();
				throw new FileUploadException(e);
			}  
	    } 
	    
	    private static boolean judgeFileType(Map<String, MultipartFile> fileMap, String[] suffix){
	    	//判断文件类型是否符合
			for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet()
			        .iterator(); it.hasNext();) {  

			    Map.Entry<String, MultipartFile> entry = it.next();
			    MultipartFile mFile = entry.getValue();
			    
			    //多文件上传时，判断是否为空
			    if (!mFile.getOriginalFilename().equals("")) {
			    	String fileName = mFile.getOriginalFilename();
			    	boolean flag=false;
			    	for (String suf : suffix) {
						if (fileName.endsWith(suf)) {
							flag=true;
						}
					}
			    	if (!flag) {
						return false;
					}
			    	
				}
			}  
			return true;
	    }
	  
	    /**
	     * 下载
	     * @param request
	     * @param response
	     * @param localFile	服务器文件
	     * @param contentType	该文件符合的响应类型
	     * @param downloadName	浏览器显示的下载文件名
	     * @throws FileDownloadException 
	     */
	    public static void download(HttpServletRequest request,  
	            HttpServletResponse response, File localFile, String contentType,  
	            String downloadName) throws FileDownloadException {
	        try {
				response.setContentType("text/html;charset=UTF-8");  
				request.setCharacterEncoding("UTF-8");  
				BufferedInputStream bis = null;  
				BufferedOutputStream bos = null;  
  
				long fileLength = localFile.length();  
  
				response.setContentType(contentType);  
				response.setHeader("Content-disposition", "attachment; filename="  
				        + new String(downloadName.getBytes("utf-8"), "ISO8859-1"));  
				response.setHeader("Content-Length", String.valueOf(fileLength));  
  
				bis = new BufferedInputStream(new FileInputStream(localFile));  
				bos = new BufferedOutputStream(response.getOutputStream());  
				byte[] buff = new byte[2048];  
				int bytesRead;  
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
				    bos.write(buff, 0, bytesRead);  
				}  
				bis.close();  
				bos.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new FileDownloadException(e);
			}  
	    }
	    
	    /**
		 * base64字符串转图片
		 * @param imgStr base64字符串
		 * @return	上传后的图片路径
	     * @throws FileUploadException 
		 */
		public static String base64ToImg(String imgStr,HttpServletRequest request) throws FileUploadException{
			try {
				if (imgStr == null||imgStr.equals(""))
					return null;
				imgStr=imgStr.substring(imgStr.lastIndexOf(',')+1);
				
				String uploadDir=request.getServletContext().getRealPath("/")+UPLOAD_DIR;
				File file = new File(uploadDir);  
				if (!file.exists()) {  
				    file.mkdir();  
				}
				String filename = renameJpg();
				String uploadPath=uploadDir+"/"+filename;
				String dbPath=UPLOAD_DIR+"/"+filename;
				
				BASE64Decoder decoder = new BASE64Decoder();
				
					// 解密
					byte[] b = decoder.decodeBuffer(imgStr);
					// 处理数据
					for (int i = 0; i < b.length; ++i) {
						if (b[i] < 0) {
							b[i] += 256;
						}
					}
					OutputStream out = new FileOutputStream(uploadPath);
					out.write(b);
					out.flush();
					out.close();
					return dbPath;
			} catch (Exception e) {
				e.printStackTrace();
				throw new FileUploadException("base64图片上传失败！");
			}
				
		}
		
		/*public static List<String> createThumbnails(List<String> pathList,HttpServletRequest request) throws FileUploadException{
			List<String> list=new ArrayList<>();
			if (pathList!=null) {
				for (String path : pathList) {
					String thumbnailPath = ThumbnailUtil.createThumbnail(request, path);
					String newPath=path+"#"+thumbnailPath;
					list.add(newPath);
				}
			}
			return list;
		}*/
		
		/**
		 * 将上次上传，但没保存到数据库的图片删除
		 */
		public static void deletePathList(HttpServletRequest request){
			List<String> pathList = (List<String>)request.getSession().getAttribute("pathList");
			if (pathList!=null) {
				for (String path : pathList) {
					String[] paths = path.split("#");
					for (String p : paths) {
						String realPath=request.getServletContext().getRealPath("/")+p;
						File file=new File(realPath);
						file.delete();	
					}
					
				}	
			}
			request.getSession().setAttribute("pathList", null);
		}
		
		
}
