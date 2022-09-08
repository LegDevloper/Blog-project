package site.metacoding.MyBlog.domain.boards;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.MyBlog.web.dto.response.boards.MainDto;
import site.metacoding.MyBlog.web.dto.response.boards.PagingDto;


public interface BoardsDao {
	public Boards findById(Integer id);
	public void insert(Boards boards); // DTO 생각해보기
	public List<MainDto> findAll(@Param("startNum") int startNum);
	public void update(Boards boards); // DTO 생각해보기
	public void delete(Integer id);
	public Integer count(); //next,previous
	public PagingDto paging(@Param("page") Integer page,@Param("keyword") String keyword);
	public List<MainDto> findSearch(@Param("startNum") int startNum, @Param("keyword") String keyword);
}
