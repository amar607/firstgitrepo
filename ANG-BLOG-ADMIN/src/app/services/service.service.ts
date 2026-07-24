import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { BlogPost } from '../models/post';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  private baseUrl = environment.apiUrl;

  constructor(private httpClient : HttpClient) { 

  }

  saveCategory (categoryData : any) {
    return this.httpClient.post<any>(`${this.baseUrl}/api/admin/categories`, categoryData);
  }

  getCategory () {
    return this.httpClient.get<any>(`${this.baseUrl}/api/admin/categories`).pipe(catchError(this.httpErrorHandler));
  }


  updateCategory (id : string, categoryData : any) {
    return this.httpClient.put<any>(`${this.baseUrl}/api/admin/categories/${id}`, categoryData);
  }

  deleteCategory (id : string) {
    return this.httpClient.delete(`${this.baseUrl}/api/admin/categories/${id}`);
  }

  savePost (postForm : any) {
    return this.httpClient.post<any>(`${this.baseUrl}/api/admin/posts`, postForm);
  }


  getPosts<T>() {
    return this.httpClient.get<any>(`${this.baseUrl}/api/admin/posts`).pipe(catchError(this.httpErrorHandler));
  }

  updatePost<T>(updatedPost: BlogPost) {
    return this.httpClient.put(`${this.baseUrl}/api/admin/posts/${updatedPost.id}`, updatedPost);
  }

  deletePost(id: number) {
    return this.httpClient.delete(`${this.baseUrl}/api/admin/posts/${id}`);
  }

  private httpErrorHandler (error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
       console.error("A client side error occurs. The error message is " + error.message);
       } else {
          console.error(
             "An error happened in server. The HTTP status code is "  + error.status + " and the error returned is " + error.message);
       }
 
    return throwError("Error occurred. Pleas try again");
 }
  
}
