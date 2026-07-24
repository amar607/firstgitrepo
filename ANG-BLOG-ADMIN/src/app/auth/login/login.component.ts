import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  
  //roles: string[] = [];

  constructor(private authService : AuthService, private toaster : ToastrService, private router : Router, private tokenStorageService : TokenStorageService) {}
  ngOnInit(): void {
    if (this.tokenStorageService.getToken()) {
      //this.isLoggedIn = true;
      //this.roles = this.tokenStorageService.getUser().roles;
    }
  }
  onLogin(login : any) {
    console.log(login);
    let creds:any = {usernameOrEmail:login.form.value.email,
      password:login.form.value.password
    }

   this.authService.login(creds).subscribe({
    next: (data) => {
      // Save token in sessionStorage (more secure than localStorage)
      this.tokenStorageService.saveToken(data.accessToken);
      this.tokenStorageService.saveUser(login.form.value.email );

      // Update your token service state
      this.tokenStorageService.setLoggedInStatus(true);
      this.tokenStorageService.isLoggedInGuard = true;

      // Toast message
      this.toaster.success("Login successful");

      // Redirect to dashboard
      this.router.navigate(['/dashboard']);

    },
    error: () => {
      this.toaster.warning("Login failed");
    }
  });
  }

}
