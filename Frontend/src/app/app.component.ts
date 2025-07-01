import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CategoryComponent } from './Components/category/category.component';
import { NavBarComponent } from './Components/nav-bar/nav-bar.component';
import { Router, NavigationEnd } from '@angular/router';
// import { CommonModule } from '@angular/common';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-root',
  imports: [RouterOutlet,NavBarComponent,CommonModule ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Frontend';
  showNavbar = true;
 constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // Hide navbar on login page
        this.showNavbar = !event.url.includes('/login');
      }
    });
  }

}
