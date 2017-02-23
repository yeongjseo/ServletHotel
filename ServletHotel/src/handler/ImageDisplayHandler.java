/*
 * JUST TEMPLATE FOR DEVEL
 */
package handler;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardFileDAO;
import board.BoardFileDTO;
import common.Env;
import common.FileUtil;
import common.MimeType;
import common.MiscUtil;


public class ImageDisplayHandler implements BaseHandler {
	private static final String FORM_VIEW = "/xxx/xxxXXX.jsp";

	@Override
	public String doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return process(req, res);
	}

	@Override
	public String doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return process(req, res);
	}
	
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		BoardFileDAO dao = new BoardFileDAO();
		BoardFileDTO dto;
		FileUtil fileUtil = new FileUtil();
		byte fileByte[];
		String filename;
		String savedFilename;
		String fileId;
		String uploadPath;
		String boardType;
		String ext;
		try {
			fileId = req.getParameter("fileId");
			boardType = req.getParameter("type");
			uploadPath = Env.get("uploadPath");
			
			dto = dao.select(Integer.parseInt(fileId));
			
			uploadPath = Env.get("uploadPath");
			uploadPath += (File.separator + boardType); 
			
			filename = dto.getFilename();
			savedFilename = dto.getSavedFilename();
			
			ext = filename.substring(filename.lastIndexOf(".")+1);
			
			fileByte = fileUtil.getByteArray(uploadPath, dto.getSavedFilename());

			System.out.printf("filename %s, savedfilename %s, filesize %d\n", 
							filename, savedFilename, fileByte.length); 
			
		    res.setContentType(MimeType.getMediaType(ext));
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
