package com.example.joblisting.controller;

import com.example.joblisting.repository.PostRepository;
import com.example.joblisting.model.Post;
import com.example.joblisting.repository.SearchRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostRepository repo;

    @Autowired
    private SearchRepositoryImpl srepo;

    @GetMapping("/posts1")
    public List<Post> getAllPosts() {
        return repo.findAll();
    }

    @GetMapping("/posts/{text}")
    public List<Post> searchPost(@PathVariable String text) {
        return srepo.findByText(text);
    }

    @PostMapping("/post")
    public Post setPost(@RequestBody Post post) {
        return repo.save(post);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {
        try {
            System.out.println("Attempting to delete post with ID: " + id); // Debug log
            if (id == null || id.trim().isEmpty()) {
                return new ResponseEntity<>("Invalid ID provided", HttpStatus.BAD_REQUEST);
            }
            if (repo.existsById(id)) {
                repo.deleteById(id);
                System.out.println("Post deleted successfully: " + id); // Debug log
                return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
            } else {
                System.out.println("Post not found: " + id); // Debug log
                return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("Error deleting post: " + e.getMessage()); // Error log
            return new ResponseEntity<>("Error deleting job: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}