import { Component } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  ReactiveFormsModule,
  Validators, FormsModule,
} from '@angular/forms';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css',
})
export class SignUpComponent {
  signupForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.signupForm = this.fb.group({
      fName: ['', Validators.required],
      lName: ['', Validators.required],
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

  provinces:string[]=[
    'Western', 'Central', 'Southern', 'Northern', 'Eastern',
    'North Western', 'North Central', 'Uva', 'Sabaragamuwa'
  ];
  selectedProvince:string = '';
}
