import { Routes } from '@angular/router';
import {SignUpComponent} from './component/sign-up/sign-up.component';
import {LoginComponent} from './component/login/login.component';
import { AdminComponent } from './component/admin/admin.component';

export const routes: Routes = [

  {path : '' , component : LoginComponent},
  {path : "signUp" , component : SignUpComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  // { path: 'home', component: HomeComponent },
  // { path: 'login', component: LoginComponent },
  // { path: 'sign-up', component: SignUpComponent },
  { path: 'admin', component: AdminComponent },  // Add this line
  { path: '**', redirectTo: '/home' } 
];

