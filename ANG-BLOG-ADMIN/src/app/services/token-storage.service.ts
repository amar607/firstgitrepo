import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})

export class TokenStorageService {



  isLoggedInUser: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  isLoggedInGuard : boolean = false;

  constructor() { 

  }

  signOut() {
    this.setLoggedInStatus(false);
    this.isLoggedInGuard = false;
    window.sessionStorage.clear();
    
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): String {
    return window.sessionStorage.getItem(TOKEN_KEY) || '{}';
  }

  public saveUser(user : any) {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser() {
    const userString = window.sessionStorage.getItem(USER_KEY);
    return userString ? JSON.parse(userString) : null;
  }

  public isLoggedIn() : Observable<boolean> {
    return this.isLoggedInUser.asObservable();
  }

  getLoggedInStatus(): Observable<boolean> {
    return this.isLoggedInUser.asObservable();
  }

  public setLoggedInStatus(value: boolean) {
    this.isLoggedInUser.next(value);
  }
}
