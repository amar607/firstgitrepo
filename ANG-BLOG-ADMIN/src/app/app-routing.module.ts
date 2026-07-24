import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriesComponent } from './categories/categories.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './auth/login/login.component';
import { AllPostComponent } from './posts/all-post/all-post.component';
import { NewPostComponent } from './posts/new-post/new-post.component';
import { authGuard } from './services/auth.guard';

const routes: Routes = [
 { path: '', redirectTo: 'login', pathMatch: 'full' }, // homepage redirect
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'categories', component: CategoriesComponent, canActivate: [authGuard] },
  { path: 'posts', component: AllPostComponent, canActivate: [authGuard]},
  { path: 'posts/new', component: NewPostComponent, canActivate: [authGuard] },
  { path: '**', redirectTo: 'login' } // wildcard for unknown paths
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
