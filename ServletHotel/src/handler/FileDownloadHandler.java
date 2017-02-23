/*
 * JUST TEMPLATE FOR DEVEL
 */
package handler;

import java.io.File;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardFileDAO;
import board.BoardFileDTO;
import common.Env;
import common.FileUtil;
import common.MiscUtil;


public class FileDownloadHandler implements BaseHandler {
	private static final String FORM_VIEW = "/xxx/xxxXXX.jsp";

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
		
			return FORM_VIEW;
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		BoardFileDAO dao = new BoardFileDAO();
		BoardFileDTO dto;
		FileUtil fileUtil = new FileUtil();
		byte fileByte[];
		String filename;
		String savedFilename;
		String fileId;
		String uploadPath;
		String boardType;
		
		try {
			boardType = req.getParameter("type");
			fileId = req.getParameter("fileId");
			
			dto = dao.select(Integer.parseInt(fileId));
			
			filename = dto.getFilename();
			savedFilename = dto.getSavedFilename();
			
			uploadPath = Env.get("uploadPath");
			uploadPath += (File.separator + boardType); 
			
			fileByte = fileUtil.getByteArray(uploadPath, dto.getSavedFilename());
		     
			System.out.printf("filename %s, savedfilename %s, filesize %d", 
							filename, savedFilename, fileByte.length); 
			
			// FILE
		    res.setContentType("application/octet-stream");
		    res.setContentLength(fileByte.length);
		    res.setHeader("Content-Disposition", "attachment; fileName=\"" + 
		    						URLEncoder.encode(filename, "UTF-8")+"\";");
		    res.setHeader("Content-Transfer-Encoding", "binary");
		    res.getOutputStream().write(fileByte);
		     
		    res.getOutputStream().flush();
		    res.getOutputStream().close();
		    req.setAttribute("ignoreLastPage", true);
			return null;
		} catch (Exception e) {
			return MiscUtil.error(req, res, e);
		}
	}
}
