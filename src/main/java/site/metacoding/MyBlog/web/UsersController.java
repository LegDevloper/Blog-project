package site.metacoding.MyBlog.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.MyBlog.domain.boards.Boards;
import site.metacoding.MyBlog.domain.boards.BoardsDao;
import site.metacoding.MyBlog.domain.users.Users;
import site.metacoding.MyBlog.domain.users.UsersDao;
import site.metacoding.MyBlog.web.dto.request.users.JoinDto;
import site.metacoding.MyBlog.web.dto.request.users.LoginDto;
import site.metacoding.MyBlog.web.dto.request.users.UpdateDto;

@RequiredArgsConstructor
@Controller
public class UsersController {

	private final HttpSession session; // 스프링이 서버시작시에 IoC 컨테이너에 보관함.
	private final UsersDao usersDao;
	private final BoardsDao boardsDao;
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}

	@PostMapping("/login") // 로그인만 예외로 select인데 post로 함.
	public String login(LoginDto loginDto) {
		Users usersPS = usersDao.login(loginDto);
		if (usersPS != null) { // 인증됨.
			session.setAttribute("principal", usersPS);
			return "redirect:/";
		} else { // 인증안됨.

			return "redirect:/loginForm";
		}

	}

	@PostMapping("/join")
	public String join(JoinDto joinDto) {
		usersDao.insert(joinDto);
		return "redirect:/loginForm";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "users/loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "users/joinForm";
	}

	@PostMapping("/update")
	public String update(UpdateDto updateDto) {
		Users principal = (Users) session.getAttribute("principal"); // 세션가져오기
		Integer id = principal.getId();
		Users usersPS = usersDao.findById(id);
		usersPS.updateUsers(updateDto);
		usersDao.update(usersPS);

		session.setAttribute("principal", usersPS); // 세션업데이트

		return "redirect:/";

	}

	@GetMapping("/updateForm")
	public String updateForm() {
		return "users/updateForm";
	}

	@PostMapping("/users/{id}/delete")
	public String delete(@PathVariable Integer id) {
		Users principal = (Users) session.getAttribute("principal");
		Integer usersId = principal.getId();
		Users usersPS = usersDao.findById(usersId);

		// 비정상요청체크
		if (usersPS == null) {
			return "errors/badPage";
		}
		// 인증체크
		if (principal == null) {
			return "redirect:/loginForm";
		}
		// 권한체크
		if (principal.getId() != usersPS.getId()) {
			return "errors/badPage";
		}
		System.out.println("====================");
		System.out.println("권한,인증 조사까지 통과");
		System.out.println("====================");
		Boards boars = new Boards();
		boardsDao.delete(id);
		usersDao.delete(id);
		session.invalidate();
		return "redirect:/";
	}
	@GetMapping("/deleteForm")
	public String deleteForm() {
		return "users/deleteForm";
	}
}
