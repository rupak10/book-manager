package com.app.service;

import com.app.dto.AppResponse;
import com.app.dto.BookDTO;
import com.app.model.Book;
import com.app.model.User;
import com.app.repository.BookRepository;
import com.app.util.CommonUtil;
import com.app.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> getBookList() {
        try {
            List<Book> books = bookRepository.findAll();
            if(books.isEmpty()) {
                return new ArrayList<>();
            }
            return processList(books);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public AppResponse addBook(BookDTO bookDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isBookAddRequestValid(bookDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }

            Book book = new Book();
            book.setTitle(bookDTO.getTitle());
            book.setDescription(bookDTO.getDescription());
            book.setAuthors(bookDTO.getAuthors());
            book.setCategories(bookDTO.getCategories());

            if(CommonUtil.isValueNotNullAndEmpty(bookDTO.getPublishedDate())) {
                book.setPublishedDate(CommonUtil.getDateFromString(bookDTO.getPublishedDate()));
            }

            book.setImage(bookDTO.getImage());
            book.setPreviewLink(bookDTO.getPreviewLink());
            book.setInfoLink(bookDTO.getInfoLink());
            book.setRatingsCount(bookDTO.getRatingsCount());

            book.setCreatedBy(loggedInUser.getUserId());
            book.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            bookRepository.save(book);

            return new AppResponse(true, "Book Added Successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to add book !");
        }
    }

    public AppResponse updateBook(BookDTO bookDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isBookAddRequestValid(bookDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }
            if(!CommonUtil.isValueNotNullAndEmpty(bookDTO.getId())) {
                return new AppResponse(false, "Invalid value provided !");
            }

            Optional<Book> bookOptional = bookRepository.findById(bookDTO.getId());
            if(bookOptional.isEmpty()) {
                return new AppResponse(false, "Invalid value provided !");
            }

            Book book = bookOptional.get();

            book.setTitle(bookDTO.getTitle());
            book.setDescription(bookDTO.getDescription());
            book.setAuthors(bookDTO.getAuthors());
            book.setCategories(bookDTO.getCategories());

            if(CommonUtil.isValueNotNullAndEmpty(bookDTO.getPublishedDate())) {
                book.setPublishedDate(CommonUtil.getDateFromString(bookDTO.getPublishedDate()));
            }

            book.setImage(bookDTO.getImage());
            book.setPreviewLink(bookDTO.getPreviewLink());
            book.setInfoLink(bookDTO.getInfoLink());
            book.setRatingsCount(bookDTO.getRatingsCount());

            book.setUpdatedBy(loggedInUser.getUserId());
            book.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            bookRepository.save(book);

            return new AppResponse(true, "Book updated successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to update book !");
        }
    }

    public BookDTO fetchBookById(Long id) {
        try {
            Optional<Book> bookOptional = bookRepository.findById(id);
            if(bookOptional.isPresent()){
                return processBook(bookOptional.get());
            }
            return new BookDTO();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new BookDTO();
        }
    }

    public AppResponse deleteBook(Long id) {
        bookRepository.deleteById(id);
        return new AppResponse(true, "Book deleted");
    }

    private BookDTO processBook(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setAuthors(book.getAuthors());
        bookDTO.setImage(book.getImage());

        bookDTO.setCategories(book.getCategories());
        bookDTO.setRatingsCount(book.getRatingsCount());
        bookDTO.setPreviewLink(book.getPreviewLink());
        bookDTO.setInfoLink(book.getInfoLink());
        bookDTO.setPublishedDate(String.valueOf(book.getPublishedDate()));

        return bookDTO;
    }

    private List<BookDTO> processList(List<Book> books) {
        List<BookDTO> list = new ArrayList<>();
        int counter = 1;
        for(Book book : books) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(book.getId());
            bookDTO.setSl(String.valueOf(counter++));
            bookDTO.setTitle(book.getTitle());
            bookDTO.setAuthors(book.getAuthors());
            bookDTO.setDescription(book.getDescription());
            bookDTO.setCategories(book.getCategories());
            bookDTO.setRatingsCount(book.getRatingsCount());
            bookDTO.setPreviewLink(book.getPreviewLink());
            bookDTO.setInfoLink(book.getInfoLink());
            bookDTO.setPublishedDate(String.valueOf(book.getPublishedDate()));
            list.add(bookDTO);
        }
        return list;
    }
}
