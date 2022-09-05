package site.metacoding.MyBlog.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import site.metacoding.MyBlog.domain.users.Users;

@RequiredArgsConstructor
@Controller
public class BoardsController {
	
	private final HttpSession session;
	// @PostMapping("/boards/{id}/delete")
		// @PostMapping("/boards/{id}/update")
	@GetMapping({"/", "/boards"})
	public String getBoardList() {
		return "boards/main";
	}
	
	@GetMapping("/boards/{id}")
	public String getBoardList(@PathVariable Integer id) {
		return "boards/detail";
	}
	
	@GetMapping("/boards/writeForm")
	public String writeForm() {
		Users principal = (Users)session.getAttribute("principal");//들어갈때 object지만 꺼낼때는 다운캐스팅ㄱ
		if(principal == null) {
			return "redirect:/loginForm";
		}
		else {
			return "boards/writeForm";
		}
	}
}
