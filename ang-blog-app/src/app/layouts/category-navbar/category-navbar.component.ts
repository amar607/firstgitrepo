import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-category-navbar',
  templateUrl: './category-navbar.component.html',
  styleUrls: ['./category-navbar.component.css']
})
export class CategoryNavbarComponent {
  constructor(private location: Location, private categoryService: CategoryService) {

  }

  filterPosts(category: string) {
    this.categoryService.changeCategory(category);
  }

  goBack(): void {
    this.location.back();
  }
}
