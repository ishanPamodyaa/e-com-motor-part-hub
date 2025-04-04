import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../service/auth/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule,RouterModule],
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';
  emailError: string = ''; 
  authError: string = '';  // Add this for authentication errors

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const email = this.loginForm.value.email;
      const password = this.loginForm.value.password;

      this.authService.login(email, password).subscribe({
        next: (response: any) => {
          console.log('Login successful:', response);
          // Navigate based on user role
          if (response.role === 'ADMIN') {
            this.router.navigate(['/home']);
            console.log('admin page load');
          } else if (response.role === 'CUSTOMER') {
            this.router.navigate(['/home']);
            console.log('user page load');
          } else {
            this.router.navigate(['']);
            console.log('page load No Login');
          }
        },
        error: (error) => {
          console.log('Login failed:', error);
          // Clear previous errors
          this.emailError = '';
          this.errorMessage = '';
          this.authError = '';

          if (error.status === 403) {
            this.authError = 'Invalid email or password';  // Handle Forbidden error
          } else if (error.status === 401) {
            this.authError = 'Invalid credentials';
          } else if (error.status === 404) {
            this.emailError = 'Email not found';
          } else {
            this.errorMessage = 'Login failed. Please try again.';
          }
        },
      });
    } else {
      if (this.loginForm.get('email')?.errors) {
        this.emailError = 'Please enter a valid email address';
      }
    }
  }
}
