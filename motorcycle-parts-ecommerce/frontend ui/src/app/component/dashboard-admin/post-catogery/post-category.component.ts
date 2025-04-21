import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormGroup, ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { AdminService } from '../../../service/admin.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-category',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './post-category.component.html',
  styleUrl: './post-category.component.css'
})
export class PostCategoryComponent {
  categoryForm: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private adminService: AdminService
  ) {
    this.categoryForm = this.fb.group({
      categoryName: ['', [Validators.required, Validators.minLength(2)]]
    });
  }

  addCategory(): void {
    console.log("add cat"+JSON.stringify(this.categoryForm.value));
    // Reset messages
    this.errorMessage = '';
    this.successMessage = '';

    if (this.categoryForm.valid) {
      console.log("valid"+ this.categoryForm.value);
      this.adminService.addCategory(this.categoryForm.value).subscribe({
        next: (res) => {
          if (res.id != null) {
            this.successMessage = 'Category added successfully!';
            this.categoryForm.reset(); // Reset form after successful addition
            // Navigate to category list after 2 seconds
            setTimeout(() => {
              this.router.navigate(['/admin/categories']);
            }, 2000);
          }
        },
        error: (error) => {
          console.error('Error adding category:', error);
          if (error.status === 409) {
            this.errorMessage = 'Category already exists';
          } else {
            this.errorMessage = 'Failed to add category. Please try again.';
          }
        }
      });
    } else {
      // Form validation errors
      if (this.categoryForm.get('categoryName')?.hasError('required')) {
        this.errorMessage = 'Category name is required';
      } else if (this.categoryForm.get('categoryName')?.hasError('minlength')) {
        this.errorMessage = 'Category name must be at least 2 characters';
      }
    }
  }
}
