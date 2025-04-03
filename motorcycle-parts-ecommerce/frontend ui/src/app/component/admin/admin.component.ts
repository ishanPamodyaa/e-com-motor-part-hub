import { Component } from '@angular/core';
import { AdminHeaderComponent } from '../admin-header/admin-header.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [AdminHeaderComponent, CommonModule],
  template: `
    <app-admin-header></app-admin-header>
    <div class="container mt-4">
      <h2>Admin Dashboard</h2>
      <!-- Add your admin dashboard content here -->
    </div>
  `,
  styles: []
})
export class AdminComponent {
  // Add your component logic here
}
