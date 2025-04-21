import { Routes } from '@angular/router';
import { SignUpComponent } from './component/sign-up/sign-up.component';
import { LoginComponent } from './component/login/login.component';
import { AdminComponent } from './component/admin/admin.component';
import { HomeComponent } from './component/home/home.component';
import { PostCategoryComponent } from './component/dashboard-admin/post-catogery/post-category.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'login/signUp', component: SignUpComponent },
  { path: 'signUp', component: SignUpComponent },
  { path: 'category', component: PostCategoryComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'dashBoard', component:DashboardComponent},

  { path: 'admin', component: AdminComponent }, // Add this line
  { path: '**', redirectTo: '/home' },
];
