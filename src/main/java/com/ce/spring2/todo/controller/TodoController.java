package com.ce.spring2.todo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ce.spring2.todo.model.dto.Todo;
import com.ce.spring2.todo.model.service.TodoService;
import com.ce.spring2.todo.model.service.TodoServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/todo")
@Slf4j
public class TodoController {
	@Autowired
	private TodoService todoService;
	
	@GetMapping("/todoList.do")
	public ModelAndView todoList(ModelAndView mav) {
		try {
			// new TodoServiceImpl()
//			log.debug("todoService = {}", new TodoServiceImpl().getClass());
//			log.debug("todoService = {}", todoService.getClass());
			
			List<Todo> list = todoService.selectAllTodo();
			mav.addObject("list", list);
			
			return mav;
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping("/insertTodo.do")
	public String insertTodo(Todo todo, RedirectAttributes redirectAttr) {
		try {
			int result = todoService.insertTodo(todo);
			redirectAttr.addFlashAttribute("msg", "할일을 추가하였습니다.");
			
			return "redirect:/todo/todoList.do";
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping("/updateTodo.do")
	public String updateTodo(@RequestParam int no, @RequestParam boolean isCompleted) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("no", no);
			param.put("isCompleted", isCompleted);
			
			int result = todoService.updateTodo(param);
			
			return "redirect:/todo/todoList.do";
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
}
