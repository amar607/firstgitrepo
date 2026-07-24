import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private httpClient : HttpClient) {

   }

   saveCategories(data : any) {
    return this.httpClient.post(`${environment.apiUrl}/api/categories`, data);
   }
}
