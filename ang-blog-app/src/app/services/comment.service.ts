import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private baseUrl = environment.apiUrl;
 constructor(private http: HttpClient) {}

  saveCommentByPostId(id: string, comment: Comment) {
    return this.http.post<Comment[]>(this.baseUrl + `/api/client/post/${id}/comment`, comment);
  }

  getCommentByPostId(id: string) {
    return this.http.get<Comment[]>(this.baseUrl + `/api/client/post/${id}/comments`);
  }


}
