package com.amar.blog.service;

import com.amar.blog.dto.PostDTO;
import com.amar.blog.dto.PostPaginationResponse;
import com.amar.blog.entity.Category;
import com.amar.blog.entity.Post;
import com.amar.blog.exceptions.ResourceNotFoundException;
import com.amar.blog.repository.CategoryRepository;
import com.amar.blog.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PostServiceImpl implements PostService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    PostRepository postRepository;
    private CategoryRepository categoryRepository;

    private ModelMapper mapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDtos = posts.stream().map(post -> mapper.map(post, PostDTO.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostPaginationResponse getAllPosts(String base_url, int pageNo, int pageSize, String sortBy, boolean desc) {
        //create Pageable instance
        Sort sort = desc ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePosts = postRepository.findAll(pageable);

        //get content from page object
        List<Post> posts = pagePosts.getContent();
        List<PostDTO> contents = posts.stream().map(post -> mapper.map(post, PostDTO.class)).collect(Collectors.toList());


        // Create a map of postId to image for quick lookup
        Map<Long, String> postImageMap = posts.stream()
                .collect(Collectors.toMap(Post::getId, post -> {
                    if (post.getImage() == null)
                        return "";
                    else
                        return post.getImage();
                }));

        // Set image in contents where id matches
        contents.forEach(content -> {
            String image = postImageMap.get(content.getId());
            if (image != null) {
                content.setImage(base_url + "/api/client/uploads/" + image);
                //convertToMultipartFile(image, "image.jpg", "image/jpeg")
            }
        });

        PostPaginationResponse postPaginationResponse = new PostPaginationResponse();
        postPaginationResponse.setContent(contents);
        postPaginationResponse.setPageNo(pagePosts.getNumber());
        postPaginationResponse.setPageSize(pagePosts.getSize());
        postPaginationResponse.setTotalElements(pagePosts.getTotalElements());
        postPaginationResponse.setTotalPages(pagePosts.getTotalPages());
        postPaginationResponse.setLast(pagePosts.isLast());
        return postPaginationResponse;
    }

    @Override
    public PostDTO createPost(PostDTO postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("category", "id", postDto.getCategoryId()));
        Post post = mapper.map(postDto, Post.class);

        try {
            // Check if file is empty
            if (postDto.getMultipartFile().isEmpty()) {
                System.out.println("Image is empty");
            }

            // Get the original filename and extension
            String originalFilename = postDto.getMultipartFile().getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));

            // Create temp file
            File targetDirectory = new File(uploadDir);
            if (!targetDirectory.exists()) {
                targetDirectory.mkdirs(); // Create directory if it doesn't exist
            }

            // ✅ Build destination file path
            File destFile = new File(targetDirectory, postDto.getMultipartFile().getOriginalFilename());

            // ✅ Save file
            postDto.getMultipartFile().transferTo(destFile);
            // Log or return the file path
            //System.out.println("Saved to temp: " + destFile.getAbsolutePath());
            post.setImage(destFile.getName());


        } catch (IOException e) {
            e.printStackTrace();
        }
//        if(postDto.getImage() != null) {
//            try {
//                post.setImage(postDto.getImage().getBytes());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
        post.setCategory(category);
        post.setObsolete(0);
        Post savedPost = postRepository.save(post);
        return mapper.map(savedPost, PostDTO.class);
    }

    public PostDTO updatePost(Long id, PostDTO postRequestDto) {
        Category category = categoryRepository.findById(postRequestDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("category", "id", postRequestDto.getCategoryId()));
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        post.setTitle(postRequestDto.getTitle());
        post.setAuthor(postRequestDto.getAuthor());
        post.setContent(postRequestDto.getContent());
        post.setCategory(category);
        Post updatedPost = postRepository.save(post);
        return mapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public PostDTO getPostById(String baseUrl, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        PostDTO postDTO = mapper.map(post, PostDTO.class);
        postDTO.setImage(baseUrl + "/api/client/uploads/" + post.getImage());
        return postDTO;
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDTO> getPostsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        List<PostDTO> postDTOS = posts.stream().map((post) -> mapper.map(post, PostDTO.class)).collect(Collectors.toList());

        return postDTOS;
    }


 /*  comment to use modelmapper
  private Post mapToEntity(PostDTO postDto){
        //Convert DTO to entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setAuthor(postDto.getAuthor());
        post.setContent(postDto.getContent());
        post.setObsolete(postDto.getObsolete());
        return post;

    }
    private PostDTO mapToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setAuthor(post.getAuthor());
        postDTO.setContent(post.getContent());
        postDTO.setObsolete(post.getObsolete());
        return postDTO;
    }*/


}
