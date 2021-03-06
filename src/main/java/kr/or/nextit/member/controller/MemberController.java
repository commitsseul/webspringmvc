package kr.or.nextit.member.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import kr.or.nextit.member.model.Member;
import kr.or.nextit.member.service.MemberService;

@Controller
@RequestMapping("/member")
//@SessionAttributes(value="member")
public class MemberController {

	@Resource(name = "memberService")
	private MemberService memberService;

	@ModelAttribute("searchTypeMap")
	public Map<String, String> getSearchType(){
		Map<String, String> searchTypeMap = new LinkedHashMap<>();
		searchTypeMap.put("id", "아이디");
		searchTypeMap.put("name", "이름");
		return searchTypeMap;
	}
	
	
	
	
	@RequestMapping(value = "/memberList")
	public String memberList(
			HttpServletRequest request,
			@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
			String searchWord, 
			@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			Model model
			)throws Exception {

		System.out.println("currentPage : " + currentPage);
		System.out.println("pageSize : " + pageSize);

		/*
		 * String searchType = request.getParameter("searchType"); String searchWord =
		 * request.getParameter("searchWord");
		 */

		Map<String, Object> paramMap = new HashMap<>();
		if (StringUtils.isNoneBlank(searchWord)) {
			paramMap.put("searchType", searchType);
			paramMap.put("searchWord", searchWord);

		}

		// 회원 목록 조회
		List<Member> memberList = memberService.getMemberList(paramMap);

		model.addAttribute("memberList", memberList);
		
		
//		request.setAttribute("memberList", memberList);

		return "/member/memberList";
	}

	@RequestMapping("/memberView")
	public String memberView(
			@RequestParam(value="mem_id",required=true) String mem_id,
			Model model
			) throws Exception {

		Member member = memberService.getMember(mem_id);
		
		model.addAttribute("member",member);
		
		return "member/memberView";
	}

	@RequestMapping(value="/memberForm")
	public String memberForm(
			@RequestParam(value="mem_id", required=false) String mem_id,
			HttpSession session,
			Model model
			) throws Exception {
		
		
		Member member = new Member();
		
		if(StringUtils.isNotBlank(mem_id)) {
		member = memberService.getMember(mem_id);
		}
		
		
		if(session.getAttribute("member") != null) {
			member = (Member)session.getAttribute("member");
			System.out.println("memberForm : " + member.getMem_id());
			
		}
		
		
		model.addAttribute("member", member);
		
		return "member/memberForm";
	}
	
	@RequestMapping(value="/memberInsert", method=RequestMethod.POST)
	public String memberInsert(
			@ModelAttribute("member")Member member,
			Model model,
			SessionStatus SessionStatus
			
			) throws Exception {
		
			String viewPage = "common/message";
	
			boolean isError = false;
			String message = "정상적으로 회원가입 되었습니다.";
			
			//유효성 검증
			if(member.getMem_id() == null ||member.getMem_id().isEmpty()) {
				isError = true;
				message = "아이디를 입력하세요";
			}
			if(StringUtils.isBlank(member.getMem_name())) {
				isError = true;
				message = "이름을 입력하세요";
				
				model.addAttribute("isError", isError);
				model.addAttribute("message", message);
				
				//model.addAttribute("member", member);
				return "member/memberForm";
//				return "redirect:/member/memberForm?type=I";
			}
			
			
			try{
				if(!isError) {

				int updCnt = memberService.insertMember(member);
				
				if(updCnt == 0){
					// 에러
					message = "회원등록 실패하였습니다.";
					isError = true;
				}
				
				//정상 완료시 세션 애트리뷰트 제거
				SessionStatus.setComplete();
				}
				
			}catch(Exception e){
				// 에러
//				e.printStackTrace();
				message = "회원등록 실패하였습니다.";
				isError = true;
				throw e;
			}
			
			//response.sendRedirect(request.getContextPath() + "/webmvc/member/memberList.do");
			model.addAttribute("isError", isError);
			model.addAttribute("message", message);
			model.addAttribute("locationURL", "/member/memberList");

			return viewPage;
		

	}
	
	@RequestMapping("/memberUpdate")
	public String memberUpdate(
			Member member,
			Model model
			) throws Exception{
		
		boolean isError = false;
		String message = "정상 수정 되었습니다";
		
		try {
			int updCnt = memberService.updateMember(member);
			if(updCnt == 0) {
				isError = true;
				message = "회원 정보 수정 실패하였습니다";
			}
		} catch (Exception e) {
			isError = true;
			message = "회원 정보 수정 실패하였습니다";
			throw e;
			
		}
		
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);
		model.addAttribute("locationURL", "/member/memberView?mem_id=" + member.getMem_id());
		
		return "common/message";
	}
	
	@RequestMapping("/memberDelete")
	public String memberDelete(
			Model model,
			@RequestParam(value="mem_id", required=true)String mem_id
			) throws Exception {
		
		boolean isError = false;
		String message = "정상 삭제 되었습니다";
		
		try {
			int updCnt = memberService.deleteMember(mem_id);
			if(updCnt == 0) {
				isError = true;
				message = "삭제 실패했습니다";
			}
			
		}catch (Exception e) {
			isError = true;
			message = "삭제 실패했습니다";
			throw e;
		}
		
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);
		model.addAttribute("locationURL", "/member/memberList");
		
		return "common/message";
	}
	
	@RequestMapping("/memberExists")
	@ResponseBody
	public Map<String, Object> memberExists(
			@RequestParam(value="mem_id",required=true) String mem_id
			) throws Exception {

		Member member = memberService.getMember(mem_id);
		
		Map<String, Object> resultMap = new HashMap<>();
		
//		resultMap.put("msg", "성공");
//		resultMap.put("member", member);

		if(member != null){
			resultMap.put("result", "true");
		}else {
			resultMap.put("result", "false");
		}
		
		return resultMap;
		

	}
}











