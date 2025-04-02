import { Component } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  ReactiveFormsModule,
  Validators,
  FormsModule,
} from '@angular/forms';
import { NgForOf, NgIf } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth/auth.service';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule,
    NgForOf,
    NgIf,
    // Add this import
  ],
  providers: [AuthService],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css',
})
export class SignUpComponent {
  signupForm: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {
    this.signupForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      nic: [
        '',
        [
          Validators.required,
          Validators.pattern(/^([0-9]{9}[Vv]$)|^([0-9]{12}$)/),
        ],
      ],
      contactNumber: [
        '',
        [Validators.required, Validators.pattern(/^(0\d{9}|\+94\d{9})$/)],
      ],
      province: ['', Validators.required], // Add this
      district: ['', Validators.required], // Add this
      city: ['', [Validators.required]],
      postalCode: ['', [Validators.required, Validators.pattern(/^\d{5}$/)]],
      address: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
    });
  }
  submitForm() {
    console.log('press Reg button');
    const password = this.signupForm.get('password')?.value;
    const confirmPassword = this.signupForm.get('confirmPassword')?.value;

    if (password != confirmPassword) {
      this.signupForm
        .get('confirmPassword')
        ?.setErrors({ passwordMismatch: true });
      return;
    }

    if (this.signupForm.valid) {
      this.authService.register(this.signupForm.value).subscribe(
        (response) => {
          this.successMessage = 'Sign up successfully';
          setTimeout(() => {
            this.router.navigateByUrl('/login');
          }, 2000);
        },
        (error) => {
          this.errorMessage = 'Registration failed. Please try again.';
          console.error('Registration error:', error);
        }
      );
    }
  }

  provinces: string[] = [
    'Western',
    'Central',
    'Southern',
    'Northern',
    'Eastern',
    'North Western',
    'North Central',
    'Uva',
    'Sabaragamuwa',
  ];

  districts: string[] = [];

  provinceDistrict: { [key: string]: string[] } = {
    Western: ['Colombo', 'Gampaha', 'Kalutara'],
    Central: ['Kandy', 'Matale', 'Nuwara Eliya'],
    Southern: ['Galle', 'Matara', 'Hambantota'],
    Northern: ['Jaffna', 'Kilinochchi', 'Mannar', 'Mullaitivu', 'Vavuniya'],
    Eastern: ['Trincomalee', 'Batticaloa', 'Ampara'],
    'North Western': ['Kurunegala', 'Puttalam'],
    'North Central': ['Anuradhapura', 'Polonnaruwa'],
    Uva: ['Badulla', 'Monaragala'],
    Sabaragamuwa: ['Ratnapura', 'Kegalle'],
  };

  onProvinceChange() {
    const selectedProvince = this.signupForm.get('province')?.value;
    this.districts = this.provinceDistrict[selectedProvince] || [];
  }
}
