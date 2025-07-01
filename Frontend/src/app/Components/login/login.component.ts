import { Component } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../Services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
 loginForm: FormGroup;
  isLoading = false;
  errorMessage = '';  
  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private toast: ToastrService
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }
  onSubmit() {
    // console.log(this.loginForm)
    if (this.loginForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';
      
      const { email, password } = this.loginForm.value;
      
      this.authService.login(email, password).subscribe({
        next: () => {
          this.toast.success('Login successfull!')
          this.router.navigate(['/dashboard']);
        },
        error: (err:any) => {
          this.errorMessage = err.error?.message || 'Login failed. Please try again.';
          this.isLoading = false;
          if(err.error?.message)
           this.toast.error(err.error.message);
          else{
            this.toast.error('Login failed. Please try again.');
          }
        },
        complete: () => {
          this.isLoading = false;
        }
      });
    }
  }
}
