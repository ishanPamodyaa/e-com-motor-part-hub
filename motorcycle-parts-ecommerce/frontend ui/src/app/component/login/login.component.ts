import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../service/auth/auth.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [
    RouterLink,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm!: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private formBuilder : FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {
  }
  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email : ['', [Validators.required]],
      password: ['', [Validators.required]]
    })
  }

  onSubmit() {
    console.log("btn press login")
    const  userName = this.loginForm.get('email')?.value;
    const password = this.loginForm.get('password')?.value;

    this.authService.login(userName, password).subscribe(
      (res)=>{
        this.successMessage = 'Login successful!';
      },
      (err)=>{
        this.errorMessage= 'Login error!';
      }

    )


  }


}
