import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Post } from './model/post';
import { Observable } from 'rxjs';
import { PaginatedPostsResponse} from './model/paginatedpost';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PostServiceService {

  private baseUrl = environment.apiUrl;
 constructor(private http: HttpClient) {}

  getPostById(id: string) {
    return this.http.get<Post>(this.baseUrl + `/api/client/posts/${id}`);
  }

  

  

  getPosts(): Observable<PaginatedPostsResponse<Post>> {
    return this.http.get<PaginatedPostsResponse<Post>>(this.baseUrl + '/api/client/posts');
  }

}
