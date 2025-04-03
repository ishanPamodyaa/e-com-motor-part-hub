import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';

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
    // Only proceed if form is valid
    if (this.loginForm.valid) {
      // Get form values
      const email = this.loginForm.value.email;
      const password = this.loginForm.value.password;

      // Call login service
      this.authService.login(email, password).subscribe({
        // When login is successful
        next: (response: any) => {
          console.log('Login successful:', response);
          // Navigate based on user role
          if (response.role === 'ADMIN') {
            // this.router.navigate(['/admin']);
            console.log('admin page load');
          } else {
            // this.router.navigate(['']);
            console.log('user page load');
          }
        },
        // When login fails
        error: (error) => {
          console.log('Login failed:', error);
          this.errorMessage =
            'Login failed. Please check your email and password.';
        },
      });
    }
  }
}
