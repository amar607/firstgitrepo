import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../services/service.service';
import { Category } from '../models/category';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit{

  categoriesArray : Array<Category>= []
  categoryName : string = ''
  description: string = ''
  formStatus : string = 'Add'
  categoryId : string = ''

  constructor( private httpService : ServiceService, private toastr : ToastrService){}
  ngOnInit(): void {
    this.loadCategoryData();
  }

  loadCategoryData(){
    this.httpService.getCategory().subscribe(categories => {
      this.categoriesArray = categories;
    });
  }

  onSubmit(formData : any) {

    let category: Category = {
      name : formData.form.value.categoryName,
      description : formData.form.value.description
    }

    if (this.formStatus == 'Add') {
      this.httpService.saveCategory(category).subscribe(response => {
        this.toastr.success("Category saved successfully");
        this.loadCategoryData();
        formData.reset();
      },  error => {
        this.toastr.warning("Category not saved");
      });
    } else if(this.formStatus == 'Edit') {
      this.updateCategory(this.categoryId ,category);
      this.loadCategoryData();
      formData.reset();
      this.formStatus = 'Add';
    }
  }

  onEdit(id : any, name:string, description : string) {
    this.categoryId = id;
    this.categoryName = name;
    this.description = description;
    this.formStatus = 'Edit'

  }

  updateCategory(id : any , updatedData : any) {
    this.httpService.updateCategory(id, updatedData).subscribe(response => {
      this.toastr.success("Successfully updated");
      this.loadCategoryData();
    }, error => {
      this.toastr.warning("Updation failed. Try again");
    });
  }

  onDelete(id: any) {
    this.httpService.deleteCategory(id).subscribe(
      (response) => {
        this.toastr.success('Deleted successfully');
        this.loadCategoryData();
      },
      (error) => {
        console.error('Delete request failed:', error);
        this.toastr.error('Deletion failed. Server error');
        // Optionally, retry the operation or handle the error differently
        this.loadCategoryData(); // Reload data regardless of error
      }
    );
  }
}
