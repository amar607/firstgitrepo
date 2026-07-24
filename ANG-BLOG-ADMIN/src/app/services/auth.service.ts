import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private httpClient : HttpClient) { }

   login (loginData: any) {
    return this.httpClient.post<any>(`${environment.apiUrl}/api/auth/login`, loginData);
}
}
