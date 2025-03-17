import { Component } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { UserService } from "../../services/user.service";

@Component({
  selector: "app-forgot-password",
  templateUrl: "./forgot-password.component.html",
  styleUrls: ["./forgot-password.component.css"],
})
export class ForgotPasswordComponent {
  forgotPasswordForm: FormGroup;
  isSubmitted = false;
  message = "";
  isError = false;
  isSuccess = false;

  constructor(private userService: UserService, private fb: FormBuilder) {
    this.forgotPasswordForm = this.fb.group({
      email: ["", [Validators.required, Validators.email]],
    });
  }

  onSubmit(): void {
    if (this.forgotPasswordForm.invalid) {
      return;
    }

    this.isSubmitted = true;
    this.resetMessages();

    this.userService
      .forgotPassword(this.forgotPasswordForm.value.email)
      .subscribe(
        (response) => {
          this.message =
            response.message ||
            "Password reset email sent. Please check your inbox.";
          this.isSuccess = true;
        },
        (err) => {
          this.message =
            err.error.message || "Error sending password reset email";
          this.isError = true;
          this.isSubmitted = false;
        }
      );
  }

  resetMessages(): void {
    this.message = "";
    this.isError = false;
    this.isSuccess = false;
  }
}
