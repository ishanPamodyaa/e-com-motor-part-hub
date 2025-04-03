import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { UserStorageService } from '../../service/storage/user-storage.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  isCustomerInLogedIn: boolean = UserStorageService.isCustomerInLogedIn();
  isAdminInLogedIn: boolean = UserStorageService.isAdminInLogedIn();

  constructor(private router: Router) {
    this.updateLoginStatus();
  }

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      this.updateLoginStatus();
    });
  }

  private updateLoginStatus(): void {
    const token = UserStorageService.getToken();
    const userRole = UserStorageService.getUserRole();
    
    console.log('Current token:', token);
    console.log('User role:', userRole);
    
    this.isAdminInLogedIn = UserStorageService.isAdminInLogedIn();
    console.log('isAdminInLogedIn:', this.isAdminInLogedIn);
    
    this.isCustomerInLogedIn = UserStorageService.isCustomerInLogedIn();
    console.log('isCustomerInLogedIn:', this.isCustomerInLogedIn);
}

  logout() {
    console.log('logout function called');
    UserStorageService.signOut();
    this.updateLoginStatus(); 
    this.router.navigate(['']);
    // this.updateLoginStatus();
  }
}
