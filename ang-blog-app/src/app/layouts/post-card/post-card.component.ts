import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { debounceTime, Subscription } from 'rxjs';
import { Post } from 'src/app/model/post';
import { PostServiceService } from 'src/app/post-service.service';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.css']
})
export class PostCardComponent implements OnInit, OnDestroy {
  posts: Post[] = [];
  allPosts: Post[] = [];
  
  private subscription: Subscription = new Subscription;

  constructor(private postService: PostServiceService,
    private categoryService: CategoryService,
    private router: Router) {}


  ngOnInit(): void {
    this.initializePosts();


    

    // Subscribe to category changes
    this.subscription = this.categoryService.currentCategory$
      .pipe(debounceTime(100))  // wait 100ms after the last event before emitting
      .subscribe(selectedCategoryName => {
        //this.initializePosts();
        // setTimeout(() => {
        //   // code to run after 10 milliseconds delay
        // }, 1000);
      if (selectedCategoryName=="Home") {
       //this.subscription .unsubscribe;
        //this.router.navigate(['/home',selectedCategoryName]);
        this.initializePosts();
        //this.router.navigate(['/home',selectedCategoryName]);
        
      } else if (selectedCategoryName){
        
        this.posts = this.allPosts;
        this.posts = this.posts.filter(post => post.category.name === selectedCategoryName);
      }

    });

  }

  initializePosts() {
    this.postService.getPosts().subscribe({
      next: (paginatedResult) => {
        if (Array.isArray(paginatedResult.content)) {
          this.allPosts = paginatedResult.content;
          this.posts = this.allPosts;
         this.posts.forEach((post) => {
            post.content = this.getPlainText(post.content).slice(0, 200);
          });
          
        }
        
      },
      error: (err) => { 
        console.error('Error fetching posts', err);
        this.posts = [];
      },
    });

  }
  
  getPlainText(html: string): string {
    const div = document.createElement('div');
    div.innerHTML = html;
    return div.textContent || div.innerText || '';
  }

  goToPost(id: number) {
   this.router.navigate(['/posts', id]);
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}


/* working category filter
ngOnInit(): void {
  this.subscription = this.categoryService.currentCategory$
    .pipe(
      debounceTime(100),
      switchMap((selectedCategoryName: any) =>
        this.initializePosts().pipe(
          map(posts => ({ posts, selectedCategoryName }))
        )
      )
    )
    .subscribe(({ posts, selectedCategoryName }) => {
      if (selectedCategoryName) {
        this.posts = posts.filter(post => post.category.name === selectedCategoryName);
      }
    });
}

 initializePosts(): Observable<Post[]> {
  return this.postService.getPosts().pipe(
    map(result => {
      const posts = result.content || [];
      posts.forEach(post => {
        post.content = this.getPlainText(post.content).slice(0, 200);
      });
      this.posts = posts;
      return posts;
    }),
    catchError(err => {
      console.error('Error fetching posts', err);
      this.posts = [];
      return of([]);
    })
  );
}

*/