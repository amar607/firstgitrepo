package com.amar.blog.controller;

import com.amar.blog.dto.PostDTO;
import com.amar.blog.dto.PostPaginationResponse;
import com.amar.blog.service.PostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.amar.blog.utils.AppConstants;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/posts")
@Tag(
        name = "CRUD Rest APIs for Post Resource"
)
public class PostResource {

    PostServiceImpl postServiceImpl;

    @Autowired
    public PostResource(PostServiceImpl postServiceImpl) {
        this.postServiceImpl = postServiceImpl;
    }

    @GetMapping(path = "/status")
    public String sayIamUp() {
        return "I am Up";
    }

/*    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        //throw new RuntimeException();
        return new ResponseEntity<>(postServiceImpl.getAllPosts(), HttpStatus.OK) ;
    }*/

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

    @Operation(
            summary = "Get POST Rest API",
            description = "Get POST api is used to get a single post from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(HttpServletRequest request ,@PathVariable Long id) {
        //throw new RuntimeException();
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null) // remove path, keep http://host:port
                .build()
                .toUriString(); // returns something like http://localhost:8080
        return new ResponseEntity<>(postServiceImpl.getPostById(baseUrl, id), HttpStatus.OK);
    }


    @Operation(
            summary = "Create POST Rest API",
            description = "Create POST api is used to save post into the database."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDTO> createPost(@Valid @ModelAttribute PostDTO request) {

        //@RequestBody is replaced with @ModelAttribute , because when using image or  MULTIPART_FORM_DATA_VALUE  then it is throwing HttpMediaTypeNotSupportedException
        //Use Mapper here and validate
        PostDTO postDto = postServiceImpl.createPost(request);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update POST Rest API",
            description = "Update POST api is used to update a single post in the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO request, @PathVariable Long id) {
        PostDTO postDto = postServiceImpl.updatePost(id, request);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete POST Rest API",
            description = "Delete POST api is used to delete a particular post in the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deletePostById (@PathVariable() Long id) {
        postServiceImpl.deletePostById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Post deleted successfully");
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDTO>> getPostsByCategoryId(@PathVariable(name = "id") Long categoryId) {
        List<PostDTO> postsByCategoryId = postServiceImpl.getPostsByCategoryId(categoryId);
        return new ResponseEntity<>(postsByCategoryId, HttpStatus.OK);
    }

}
