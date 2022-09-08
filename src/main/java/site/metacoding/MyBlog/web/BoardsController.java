package site.metacoding.MyBlog.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.MyBlog.domain.boards.Boards;
import site.metacoding.MyBlog.domain.boards.BoardsDao;
import site.metacoding.MyBlog.domain.users.Users;
import site.metacoding.MyBlog.web.dto.request.boards.UpdateDto;
import site.metacoding.MyBlog.web.dto.request.boards.WriteDto;
import site.metacoding.MyBlog.web.dto.response.boards.MainDto;
import site.metacoding.MyBlog.web.dto.response.boards.PagingDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {
	private final BoardsDao boardsDao;
	private final HttpSession session;

	@PostMapping("boards/{id}/update")
	public String update(@PathVariable Integer id, UpdateDto updateDto) {
		// 1.영속화
		Boards boardsPS = boardsDao.findById(id);
		Users principal = (Users) session.getAttribute("principal");

		if (boardsPS == null) {
			return "errors/badPage";
		}
		// 인증체크
		if (principal == null) {
			return "redirect:/loginForm";
		}
		// 권한체크
		if (principal.getId() != boardsPS.getUsersId()) {
			return "errors/badPage";
		}
		// 2.변경
		boardsPS.updateBoards(updateDto);
		// 수행
		boardsDao.update(boardsPS);
		return "redirect:/boards/" + id;
	}

	@GetMapping("/boards/{id}/updateForm")
	public String updateForm(@PathVariable Integer id, Model model) {
		Boards boardsPS = boardsDao.findById(id);
		Users principal = (Users) session.getAttribute("principal");

		// 비정상요청체크
		if (boardsPS == null) {
			return "errors/badPage";
		}
		// 인증체크
		if (principal == null) {
			return "redirect:/loginForm";
		}
		// 권한체크
		if (principal.getId() != boardsPS.getUsersId()) {
			return "errors/badPage";
		}
		model.addAttribute("boards", boardsPS);
		return "boards/updateForm";
	}

	@PostMapping("/boards/{id}/delete")
	public String deleteBoards(@PathVariable Integer id) {
		// 트랜잭션때문에 먼저 select를 한다(READ)
		Users principal = (Users) session.getAttribute("principal");
		Boards boardsPS = boardsDao.findById(id);
		
		// 비정상요청체크
		if (boardsPS == null) {// If는 비정상 로직을 타게 해서 걸러내는 필터 역할을 하는게 좋다.
			return "redirect:/loginForm";
		}
		// 인증체크
		if (principal == null) {
			return "redirect:/loginForm";
		}
		// 권한체크
		if (principal.getId() != boardsPS.getUsersId()) {
			return "errors/badPage";
		}
		boardsDao.delete(id); // 핵심로직

		return "redirect:/";
	}

	@PostMapping("/boards")
	public String writeBoards(WriteDto writeDto) {
		// 세션에 접근해서 세션값을 확인한다. 그떄 users로 다운캐스팅하고 키값은 principal로 한다.
		// principal null 인지 확인하고 null이면 loginForm리다이렉션
		// BoardsDao에 접근해서 insert 메서드를 호출한다.
		// dto를 entity로 변환해서 인수로 넣는다
		// entity에는 셋녀의 principal에 getid가 필요하다.
		Users principal = (Users) session.getAttribute("principal");
		Integer usersId = principal.getId();

		if (principal == null) {
			return "redirect:/loginForm";
		}

		boardsDao.insert(writeDto.toEntity(usersId));
		return "redirect:/";
	}

	// 1. ?page=0&keyword=스프링 -> 2개의 값을 받는다
	@GetMapping({ "/", "/boards" }) // page는 QueryString으로 받자(PK값이 아니기때문에)
	public String getBoardList(Model model, Integer page, String keyword) {
		
		if (page == null) {
			page = 0;
		}
		int startNum = (page) * 3;
		
		if (keyword == null || keyword.isEmpty()) {// 검색이 아닌
			List<MainDto> boardsList = boardsDao.findAll(startNum);
			PagingDto paging = boardsDao.paging(page, null);
			paging.makeBlockInfo(keyword);

			model.addAttribute("boardsList", boardsList);
			model.addAttribute("paging", paging);

		} else { // 검색이 된
			List<MainDto> boardsList = boardsDao.findSearch(startNum, keyword);
			PagingDto paging = boardsDao.paging(page, keyword);

			paging.makeBlockInfo(keyword);

			model.addAttribute("boardsList", boardsList);
			model.addAttribute("paging", paging);
		}

		return "boards/main";
	}

	@GetMapping("/boards/{id}")
	public String getBoardDetail(@PathVariable Integer id, Model model) {
		model.addAttribute("boards", boardsDao.findById(id));
		return "boards/detail";
	}

	@GetMapping("/boards/writeForm")
	public String writeForm() {
		Users principal = (Users) session.getAttribute("principal");// 들어갈때 object지만 꺼낼때는 다운캐스팅ㄱ
		if (principal == null) {
			return "redirect:/loginForm";
		}
		return "boards/writeForm";
	}
}
