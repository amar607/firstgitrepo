import { Location } from '@angular/common';
import { Component, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  userEmail: String = "";
  isUserLoggedIn$ : Observable<boolean> = of(false);

  constructor(private tokenStorageService : TokenStorageService, private router : Router, private location: Location) {

  }

  ngOnInit(): void {

    // Track login status
    this.isUserLoggedIn$ = this.tokenStorageService.getLoggedInStatus();

    // Whenever login status changes, update the username
    this.tokenStorageService.getLoggedInStatus().subscribe((loggedIn) => {
      if (loggedIn) {
        this.userEmail  = this.tokenStorageService.getUser();
      } else {
        this.userEmail = "";
      }
    });
  }


  goBack(): void {
    this.location.back();
  }
  
  logout() {
    this.tokenStorageService.signOut();
    this.userEmail="";
    this.router.navigate(['/login']);
  }
}
