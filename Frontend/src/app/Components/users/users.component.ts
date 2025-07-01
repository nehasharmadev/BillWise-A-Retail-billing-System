import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../Services/user.service';
import { ToastrService } from 'ngx-toastr';
// interface User {
//   id?: string;
//   name: string;
//   email: string;
//   password: string;
//   role: 'USER' | 'ADMIN';
// }

@Component({
 selector: 'app-users',
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit {
  registerForm: FormGroup;
  users: any = [];
  filteredUsers: any = [];
  searchTerm: string = '';
  // isEditing = false;
  // currentUserId: string | null = null;

  constructor(private fb: FormBuilder, private userService:UserService, private toast:ToastrService) {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['USER', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next:(response)=>{
        this.users= response;
           this. filteredUsers = response;
           console.log("filtered users", this.filterUsers);
           console.log("fetched all users", response);
           this.toast.success("fetched all users successfully");
      },
      error:(err)=>{
         this.toast.error(err.error.message);
      }
    })
  }

  onSubmit(): void {
    if (this.registerForm.invalid) return;

    const user: any = this.registerForm.value;
    
    // if (this.isEditing && this.currentUserId) {
    //   // Update existing user
    //   const index = this.users.findIndex(u => u.id === this.currentUserId);
    //   if (index !== -1) {
    //     this.users[index] = { ...user, id: this.currentUserId };
    //   }
    // } else {
    //   // Add new user
    //   user.id = Date.now().toString();
    //   this.users.push(user);
    // }

    this.userService.registeUser(user).subscribe({
      next:(response) =>{
        this.toast.success("registered the user successfully");
        this.users.push(response);
        this.resetForm();
        this.filterUsers();
        console.log(response);
      },
      error:(err)=>{
        this.toast.error(err.error.message);
      }
    })
    
  }

  // editUser(user: User): void {
  //   this.isEditing = true;
  //   this.currentUserId = user.id || null;
  //   this.registerForm.patchValue({
  //     name: user.name,
  //     email: user.email,
  //     role: user.role
  //   });
  //   // Don't fill password for security reasons
  //   this.registerForm.get('password')?.reset();
  // }

  deleteUser(id: string, index:number): void {
    // this.users = this.users.filter((user:any) => user.id !== id);
  
    this.userService.deleteUser(id).subscribe({
      next:(response)=>{
         this.toast.success("Deleted the user successfully");
         this.users.splice(index,1);
         this.filterUsers();
      },
      error:(e)=>{
        this.toast.error(e.error.message);
      }
    })
  }

  filterUsers(): void {
    if (!this.searchTerm) {
      this.filteredUsers = [...this.users];
      return;
    }
    
    const term = this.searchTerm.toLowerCase();
    this.filteredUsers = this.users.filter((user:any) => 
      user.name.toLowerCase().includes(term) || 
      user.email.toLowerCase().includes(term) ||
      user.role.toLowerCase().includes(term)
    );
  }

  resetForm(): void {
    this.registerForm.reset({
      role: 'USER'
    });
    // this.isEditing = false;
    // this.currentUserId = null;
  }
}
