/*package kr.or.nextit.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.nextit.board.model.Board;
import kr.or.nextit.board.service.BoardService;
import kr.or.nextit.board.service.impl.BoardServiceImpl;
import kr.or.nextit.common.file.model.FileItem;
import kr.or.nextit.common.file.servise.FileItemService;
import kr.or.nextit.common.file.servise.impl.FileItemServiceImpl;
import kr.or.nextit.web.servlet.Controller;

public class BoardInsertController implements Controller {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		request.setCharacterEncoding("UTF-8");
		
		Board board = new Board();
		
		BeanUtils.populate(board, request.getParameterMap());
		
		String viewPage = "common/message";
		
		boolean isError = false;
		String message = "정상등록 되었습니다";
		String locationURL = "/board/boardList.do";
		
		try {
			BoardService boardService = BoardServiceImpl.getInstance();
			FileItemService fileItemService = FileItemServiceImpl.getInstance();
			
			//파일목록 생성
			List<FileItem> fileItemList = fileItemService.uploadFiles(request, board.getBo_type());
			
			board.setFileItemList(fileItemList);
			
			int updCnt = boardService.insertBoard(board);
			
			if(updCnt == 0) {
				isError = true;
				message ="등록실패함" ;
						
			}
		}catch (Exception e) {
			isError = true;
			message ="등록실패함" ;
			
		}
		
		request.setAttribute("isError", isError);
		request.setAttribute("message", message);
		request.setAttribute("locationURL", locationURL);
		
		return viewPage;
	}

}










*/