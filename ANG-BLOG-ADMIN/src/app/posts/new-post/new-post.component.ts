import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Category } from 'src/app/models/category';
import { BlogPost } from 'src/app/models/post';
import { ServiceService } from 'src/app/services/service.service';

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.css']
})
export class NewPostComponent implements OnInit{
  
  isEditMode: boolean = false;
  categoriesArray : Array<Category>= [];
  imageFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;

  blogPosts: BlogPost[] = [];
  blogPost: BlogPost = {
    title: '',
    author: '',
    content: '',
    categoryId: 0,
    createdDate: '',
    codeSnippet: '',
    image: ''
  };
  constructor(private httpService : ServiceService){}

  ngOnInit(): void {
    this.loadCategory();
    this.getAllPosts();
  }

  loadCategory () {
    this.httpService.getCategory().subscribe(categories => {
      this.categoriesArray = categories;
    }, (error : any) => {
      console.log(error);
    });
  }

  onImageSelected(event: Event): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.imageFile = file;

      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  savePost(postForm: NgForm): void {

    if (!postForm.valid) {
      console.log('Form is invalid');
      // Mark all fields as touched to trigger validation messages
      Object.keys(postForm.controls).forEach(field => {
        const control = postForm.controls[field];
        control?.markAsTouched({ onlySelf: true });
      });
      return;
    }

     if (this.isEditMode) {
      this.updatePost();
    } else {
    
      const formData = new FormData();
      formData.append('title', this.blogPost.title);
      formData.append('author', this.blogPost.author);
      formData.append('content', this.blogPost.content);
      formData.append('categoryId', this.blogPost.categoryId.toString());
      formData.append('codeSnippet', this.blogPost.codeSnippet);

      if (this.imageFile) {
        formData.append('multipartFile', this.imageFile);
      }

      this.httpService.savePost(formData).subscribe({
        next: (res) => {
          this.blogPosts.push(res);
          this.resetForm();
        },
        error: (err) => console.error('Error saving post', err)
      });
  }
    
  }

  getAllPosts(): void {
    this.httpService.getPosts<BlogPost[]>().subscribe({
      next: (res) => {
        this.blogPosts = [];
        (this.blogPosts = res.content)},
      error: (err) => console.error('Error fetching posts', err)
    });
  }



  editPost(post: BlogPost): void {
    this.blogPost = { ...post };
    this.isEditMode = true;
  }

  updatePost(): void {
    
    this.httpService.updatePost<BlogPost>(this.blogPost).subscribe({
      next: () => {
        this.getAllPosts();
        this.resetForm();
      },
      error: (err) => console.error('Error updating post', err)
    });
  }

  deletePost(id: number | undefined): void {
    if (!id) {
      return
    };
    
    this.httpService.deletePost(id).subscribe({
      next: (res) => { 
        console.log('Delete response:', res);
        this.blogPosts = this.blogPosts.filter(post => post.id !== id);
        this.resetForm();
      },
      error: (err) => { console.error('Error deleting post', err) }
      ,
    complete: () => { console.log('Delete request completed'); }
    });
    
  }

  resetForm(): void {
    this.blogPost = { title: '', author: '', content: '', categoryId: 1 , createdDate: '',
      codeSnippet: '',
      image: ''
    };
    this.imageFile = null;
    this.imagePreview = null;
    this.isEditMode = false;
  }
}



