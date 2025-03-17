import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { RouterModule, Routes } from "@angular/router";

import { AppComponent } from "./app.component";
import { ProfileComponent } from "./components/profile/profile.component";
import { ForgotPasswordComponent } from "./components/forgot-password/forgot-password.component";
import { ResetPasswordComponent } from "./components/reset-password/reset-password.component";
import { UserManagementComponent } from "./components/admin/user-management/user-management.component";

// Import services
import { UserService } from "./services/user.service";
import { TokenStorageService } from "./services/token-storage.service";
import { authInterceptorProviders } from "./helpers/auth.interceptor";

// Define routes
const routes: Routes = [
  { path: "profile", component: ProfileComponent },
  { path: "forgot-password", component: ForgotPasswordComponent },
  { path: "reset-password", component: ResetPasswordComponent },
  { path: "admin/users", component: UserManagementComponent },
  { path: "", redirectTo: "/home", pathMatch: "full" },
];

@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    UserManagementComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
  ],
  providers: [UserService, TokenStorageService, authInterceptorProviders],
  bootstrap: [AppComponent],
})
export class AppModule {}
