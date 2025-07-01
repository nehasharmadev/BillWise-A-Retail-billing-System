import { Component, inject } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CategoryServiceComponent } from '../../Services/category-service/category-service.component';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-category',
  imports: [NgFor, ReactiveFormsModule, NgIf],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css',
})
export class CategoryComponent {
  constructor(private categoryService: CategoryServiceComponent, private toast: ToastrService) {}
  categoryList: any = [];
  selectedFile: File | null = null;
  categoryForm = new FormGroup({
    name: new FormControl('', Validators.required),
    description: new FormControl('', [
      Validators.required,
      Validators.maxLength(100),
    ]),
    image: new FormControl(null, Validators.required),
  });

  ngOnInit(){
    this.categoryService.getAllCategory().subscribe({
      next:(response)=>{
        console.log(response);
        this.categoryList = response;
        this.toast.success("fetched all the categories successfully");
      },
      error:(err)=>{
        console.log(err);
        this.toast.error("error while fetching the categories");

      }
    });
    console.log(this.categoryList)
  }

  addCategory() {
    if (this.categoryForm.valid) {
      const formData = new FormData();

      const name = this.categoryForm.get('name')?.value || '';
      const description = this.categoryForm.get('description')?.value || '';
      const imageFile = this.selectedFile;

      formData.append('name', name);
      formData.append('description', description);
      if(imageFile)
      formData.append('file', imageFile);

      this.categoryService.addCategory(formData).subscribe({
        next: (response) => {
          console.log(response);
          this.categoryList.unshift(response);
          this.categoryForm.reset(); 
          this.selectedFile = null;
          console.log(this.categoryList);
          this.toast.success("Added the category successfully");

        },
        error: (err) => {
          console.log(err);
          this.toast.error(err.error.message);
        },
      });
    }
  }
  onFileSelected(event: any) {
    console.log(event);
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
    console.log('Upladed image' + file);
  }
  deleteCategory(Id: any, index:any) {
    console.log(Id);
    console.log(index);
    this.categoryService.deleteCategory(Id).subscribe({
      next:(response)=>{
        console.log(response);
        this.categoryList.splice(index, 1);
        this.toast.success("deleted the category successfully");
      },
      error:(err)=>{
        console.log(err);
        this.toast.error(err.error.message);

      }
    });;
    
  }
  editCategory(Id: any, index:any) {
    console.log(Id);
    console.log(index);
  }
}
