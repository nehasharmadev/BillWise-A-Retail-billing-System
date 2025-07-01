import { Component } from '@angular/core';
import { RouterLink , RouterLinkActive} from '@angular/router';
import { AuthService } from '../../Services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { NgIf } from '@angular/common';
@Component({
  selector: 'app-nav-bar',
  imports: [RouterLink ,RouterLinkActive, NgIf],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {
    userRole: string | null = null;  

 ngOnInit(): void {
    this.userRole = localStorage.getItem('user_role'); // Get role from localStorage
  }
  constructor(private authService: AuthService, private toast: ToastrService){}
logout(){
  this.authService.logout();
  this.toast.success("logged out");
}
}
