package com.app.controller;

import com.app.dto.AppResponse;
import com.app.dto.BookDTO;
import com.app.service.BookService;
import com.app.util.CommonUtil;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookController {
	private final Logger log = LoggerFactory.getLogger(BookController.class);
	private final String ACTIVE_MENU = "home";
	private final String BOOK_LIST_PAGE = "books/list";
	private final String BOOK_ADD_PAGE = "books/add";
	private final String BOOK_EDIT_PAGE = "books/edit";
	private final String  BOOK_VIEW_PAGE = "books/view";
	private final String REDIRECT_LIST_PAGE = "redirect:/books";

	@Autowired
	private BookService bookService;
	
	@RequestMapping(value = "/books", method = RequestMethod.GET)
    public String loadBookListPage(Model model, HttpSession httpSession) {
		log.info("Entering loadBookListPage() method");

		model.addAttribute("pageTitle", "Books");

		model.addAttribute("books", bookService.getBookList());
		model.addAttribute("am", ACTIVE_MENU);

		log.info("Exiting loadBookListPage() method");
		return BOOK_LIST_PAGE;
    }

	@RequestMapping(value = "/books/add", method = RequestMethod.GET)
	public String loadBookAddPage(Model model, HttpSession httpSession) {
		log.info("Entering loadBookAddPage() method");

		model.addAttribute("pageTitle", "Add Book");
		model.addAttribute("book", new BookDTO());

		model.addAttribute("am", ACTIVE_MENU);
		log.info("Exiting loadBookAddPage() method");
		return BOOK_ADD_PAGE;
	}

	@RequestMapping(value = "/books/add", method = RequestMethod.POST)
	public String addBook(Model model, HttpSession httpSession, @ModelAttribute BookDTO bookDTO,
							 final RedirectAttributes redirectAttributes) {
		log.info("Entering addBook() method");
		log.info("bookDTO request:"+bookDTO);

		AppResponse appResponse = bookService.addBook(bookDTO, CommonUtil.getUserFromSession(httpSession));
		if(appResponse.getStatus()){
			redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
			log.info("Exiting addBook() method");
			return REDIRECT_LIST_PAGE;
		}
		else{
			model.addAttribute("msg", appResponse.getMessage());
			model.addAttribute("book", bookDTO);
			log.info("Exiting addBook() method");
			return BOOK_ADD_PAGE;
		}
	}

	@RequestMapping(value = "/books/edit/{id}", method = RequestMethod.GET)
	public String loadBookEditPage(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
		log.info("Entering loadBookEditPage() method");
		log.info("Book ID : "+id);

		BookDTO bookDTO = bookService.fetchBookById(id);

		model.addAttribute("pageTitle", "Edit Book");
		model.addAttribute("book", bookDTO);
		model.addAttribute("am", ACTIVE_MENU);

		log.info("Exiting loadBookEditPage() method");
		return BOOK_EDIT_PAGE;
	}

	@RequestMapping(value = "/books/edit", method = RequestMethod.POST)
	public String editBook(Model model, @ModelAttribute BookDTO bookDTO, HttpSession httpSession,
							  final RedirectAttributes redirectAttributes) {
		log.info("Entering editBook() method");

		AppResponse appResponse = bookService.updateBook(bookDTO, CommonUtil.getUserFromSession(httpSession));
		if(appResponse.getStatus()){
			redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
			log.info("Exiting editBook() method");
			return REDIRECT_LIST_PAGE;
		}
		else{
			model.addAttribute("msg", appResponse.getMessage());
			model.addAttribute("book", bookDTO);
			log.info("Exiting editBook() method");
			return BOOK_EDIT_PAGE;
		}
	}

	@RequestMapping(value = "/books/delete/{id}", method = RequestMethod.GET)
	public String deleteBook(@PathVariable("id") Long id, HttpSession httpSession, final RedirectAttributes redirectAttributes) {
		log.info("Entering deleteBook() method");
		log.info("Book ID : "+id);

		AppResponse appResponse = bookService.deleteBook(id);
		redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());

		log.info("Exiting deleteBook() method");
		return REDIRECT_LIST_PAGE;
	}

	@RequestMapping(value = "/books/view/{id}", method = RequestMethod.GET)
	public String loadBookViewPage(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
		log.info("Entering loadBookViewPage() method");
		log.info("Book ID : "+id);

		BookDTO bookDTO = bookService.fetchBookById(id);

		model.addAttribute("pageTitle", "View Book");
		model.addAttribute("book", bookDTO);
		model.addAttribute("am", ACTIVE_MENU);

		log.info("Exiting loadBookViewPage() method");
		return BOOK_VIEW_PAGE;
	}


}
