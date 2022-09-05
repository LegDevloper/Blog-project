package site.metacoding.MyBlog.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.MyBlog.domain.boards.BoardsDao;
import site.metacoding.MyBlog.domain.boards.mapper.MainView;
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
		// 세션에 접근해서 세션값을 확인한다. 그떄 users로 다운캐스팅하고 키값은 principal로 한다.
		// principal null 인지 확인하고 null이면 loginForm리다이렉션
		// BoardsDao에 접근해서 insert 메서드를 호출한다.
		// dto를 entity로 변환해서 인수로 넣는다
		// entity에는 셋녀의 principal에 getid가 필요하다.
		Users principal = (Users)session.getAttribute("principal");
		Integer usersId = principal.getId();
		
		if(principal==null) {
			return "redirect:/loginForm";
		}
		
		boardsDao.insert(writeDto.toEntity(usersId));
		return "redirect:/";
	}
	
	@GetMapping({"/", "/boards"})
	public String getBoardList(Model model) {
		List<MainView> boardsList = boardsDao.findAll();
		model.addAttribute("boardsList",boardsList);
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
