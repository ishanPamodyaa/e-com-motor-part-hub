import { Component } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  ReactiveFormsModule,
  Validators,
  FormsModule,
} from '@angular/forms';
import { NgForOf } from '@angular/common';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, NgForOf],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css',
})
export class SignUpComponent {
  signupForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.signupForm = this.fb.group({
      fName: ['', Validators.required],
      lName: ['', Validators.required],
      province: ['', Validators.required], // Add this
      district: ['', Validators.required], // Add this
    });
  }
  submitForm() {
    if (this.signupForm.valid) {
      console.log('Form Submitted..! ', this.signupForm.value);
    } else {
      console.log('Form is not valid!', this.signupForm.value);
      // this.signupForm.reset();
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
