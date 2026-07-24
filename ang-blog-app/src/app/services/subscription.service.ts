import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  constructor(private httpClient : HttpClient) {

   }

   subscribe(data : any) {
    return this.httpClient.post(`${environment.apiUrl}/api/client/subscribe`, data);
   }
}
