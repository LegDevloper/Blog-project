package site.metacoding.MyBlog.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.MyBlog.domain.boards.BoardsDao;
import site.metacoding.MyBlog.domain.users.Users;
import site.metacoding.MyBlog.web.dto.request.boards.WriteDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {
	private final BoardsDao boardsDao;
	private final HttpSession session;
	// @PostMapping("/boards/{id}/delete")
	// @PostMapping("/boards/{id}/update")
	
	@PostMapping("/boards")
	public String writeBoards(WriteDto writeDto) {
		Users principal = (Users)session.getAttribute("principal");
		Integer usersId = principal.getId();
		
		if(principal==null) {
			return "redirect:/loginForm";
		}
		
		boardsDao.insert(writeDto.toEntity(usersId));
		return "redirect:/";
	}
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
		return "boards/writeForm";
	}
}
