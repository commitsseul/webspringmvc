/*package kr.or.nextit.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.nextit.board.dao.BoardDao;
import kr.or.nextit.board.model.Board;
import kr.or.nextit.board.service.BoardService;
import kr.or.nextit.board.service.impl.BoardServiceImpl;
import kr.or.nextit.member.model.Member;
import kr.or.nextit.web.servlet.Controller;

public class BoardDeleteController implements Controller {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		
		// 로그인 여부 확인
		HttpSession session = request.getSession();
						
		Member member = (Member)session.getAttribute("LOGIN_USER");
//						
//		if(member == null) {
//			return "redirect:/login/loginForm.do";
//		}
		
		
		Board board = new Board();
		
		BeanUtils.populate(board, request.getParameterMap());
		
		String boSeqNo = request.getParameter("bo_seq_no");
		
		int bo_seq_no = Integer.parseInt(boSeqNo);
		
		Map<String, Object> paramMap = new HashMap<>();
		
		paramMap.put("bo_seq_no", bo_seq_no);
		paramMap.put("upd_user", member.getMem_id());
		
		
//		board.setBo_del_yn("san3");
		
		boolean isError = false;
		String viewPage = "common/message";
		String message = "정상 삭제 되었음";
//		String locationURL = "/board/boardView.do";
		
		try {
		BoardService boardService = BoardServiceImpl.getInstance();	
		
		int updCnt = boardService.deleteBoard(paramMap);
		
		if(updCnt == 0) {
			isError = true;
			message = "삭제 실패함";
		}
		
		}catch (Exception e) {
			e.printStackTrace();
			isError = true;
			message ="삭제 실패함";
		}
		
		request.setAttribute("isError", isError);
		request.setAttribute("message", message);
		request.setAttribute("locationURL", "/board/boardList.do");
		
		
		return viewPage;
	}

}
*/