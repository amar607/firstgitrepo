package com.amar.blog.controller;

import com.amar.blog.dto.PostDTO;
import com.amar.blog.dto.PostPaginationResponse;
import com.amar.blog.service.PostServiceImpl;
import com.amar.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/client/posts")
@Tag(
        name = "CRUD Rest APIs for Post Resource"
)
public class PostClientResource {


    PostServiceImpl postServiceImpl;

    public PostClientResource(PostServiceImpl postServiceImpl) {
        this.postServiceImpl = postServiceImpl;
    }


    @Operation(
            summary = "Get POST Rest API For Client",
            description = "Get POST api is used to get a single post from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(HttpServletRequest request, @PathVariable Long id) {
        //throw new RuntimeException();
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null) // remove path, keep http://host:port
                .build()
                .toUriString(); // returns something like http://localhost:8080
        return new ResponseEntity<>(postServiceImpl.getPostById(baseUrl, id), HttpStatus.OK);
    }


    @Operation(
            summary = "Get all POST Rest API",
            description = "Get all POSTS from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping()
    public ResponseEntity<PostPaginationResponse> getAllPostsUsingPagination(
            HttpServletRequest request,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_COLUMN, required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = AppConstants.DEFAULT_SORT_DESC, required = false) boolean desc

    ) {
        // Dynamically construct base URL
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null) // remove path, keep http://host:port
                .build()
                .toUriString(); // returns something like http://localhost:8080
        //throw new RuntimeException();
        return new ResponseEntity<>(postServiceImpl.getAllPosts(baseUrl, pageNo, pageSize, sortBy, desc), HttpStatus.OK) ;
    }


}
