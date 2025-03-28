import { Routes } from '@angular/router';
import {SignUpComponent} from './component/sign-up/sign-up.component';
import {LoginComponent} from './component/login/login.component';

export const routes: Routes = [

  {path : '' , component : LoginComponent},
  {path : "signUp" , component : SignUpComponent},
];

