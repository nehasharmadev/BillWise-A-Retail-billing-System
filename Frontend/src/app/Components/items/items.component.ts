import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ItemRequest } from '../../io/ItemRequest';
// import { ItemResponse } from '../../io/ItemResponse';
import { ItemService } from '../../Services/item.service';
import { CommonModule } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { CategoryServiceComponent } from '../../Services/category-service/category-service.component';
import { ItemResponse } from '../../io/ItemResponse';
@Component({
  selector: 'app-items',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './items.component.html',
  styleUrl: './items.component.css'
})
export class ItemsComponent {
 itemForm: FormGroup;
  categories: any = []; 
  selectedFile: File | null = null;
  itemList:any=[];
  constructor(
    private fb: FormBuilder,
    private itemService: ItemService,
    private categoryService: CategoryServiceComponent,
    private toast : ToastrService
  ) {
    this.itemForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
      categoryId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.fetchCategories(); 
     this.itemService.getItems().subscribe({
      next:(response)=>{
        console.log(response);
        this.itemList = response;
        this.toast.success("fetched all the items successfully");
      },
      error:(err)=>{
        console.log(err);
        this.toast.error("error while fetching the items");

      }
    });
    console.log(this.itemList);
  }
  fetchCategories(): void {
    this.categoryService.getAllCategory().subscribe({
      next: (categories) => {
        this.categories = categories; 
        console.log(this.categories)
        this.toast.success("fetched all the categories successfully");
      },
      error: (error) => {
        console.error('Error fetching categories:', error);
        this.toast.error("error while fetching the categories");
      }
    });
  }
  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0]; // Get the selected file
  }

  addItem(): void {
    console.log(this.itemForm.value);
    if (this.itemForm.valid && this.selectedFile) {
      const itemRequest: ItemRequest = {
        ...this.itemForm.value
      };
      this.itemService.addItem(itemRequest, this.selectedFile).subscribe({
        next: (response) => {
          console.log('Item added successfully:', response);
          this.toast.success("added item successfully");
          this.itemList.unshift(response);
          this.itemForm.reset(); 
          this.selectedFile = null;
          // Handle success (e.g., show a success message, reset form, etc.)
        },
        error: (error) => {
          console.error('Error adding item:', error);
          this.toast.error("error while adding item");
          // Handle error (e.g., show an error message)
        }
      });
    } else {
      console.error('Form is invalid or no file selected');
      this.toast.error("Form is invalid or no file selected")
      // Handle case where form is invalid
    }
  }
   deleteItem(Id: any, index:any) {
    console.log(Id);
    console.log(index);
    this.itemService.deleteItem(Id).subscribe({
      next:(response)=>{
        console.log(response);
        this.itemList.splice(index, 1);
        this.toast.success("deleted the item successfully");
      },
      error:(err)=>{
        console.log(err);
        this.toast.error(err.error.message);

      }
    });;
    
  }
}
