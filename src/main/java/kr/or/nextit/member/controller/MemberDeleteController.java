/*package kr.or.nextit.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.member.service.MemberService;
import kr.or.nextit.member.service.impl.MemberServiceImpl;
import kr.or.nextit.web.servlet.Controller;

public class MemberDeleteController implements Controller {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		
		String mem_id = request.getParameter("mem_id");
		
		boolean isError = false;
		String viewPage = "common/message";
		String message = "정상삭제됨";
		
		
		try {
		MemberService memberService = MemberServiceImpl.getInstance();
		int updCnt = memberService.deleteMember(mem_id);
		if(updCnt == 0) {
			message = "회원삭제 실패";
			isError = true;
		}
		}catch(Exception e) {
			e.printStackTrace();
			message = "회원삭제 실패";
			isError = true;
		}
		
		request.setAttribute("isError", isError);
		request.setAttribute("message", message);
		request.setAttribute("locationURL", "/member/memberList.do");
		
		
		
		return viewPage;
	}
	
}
*/