import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  // Initial value can be null or ''
  private selectedCategoryName = new BehaviorSubject<string>('');
  
  // Observable for components to subscribe
  currentCategory$ = this.selectedCategoryName.asObservable();

  // Method to update the selected category
  changeCategory(category: string) {
    this.selectedCategoryName.next(category);
  }
}