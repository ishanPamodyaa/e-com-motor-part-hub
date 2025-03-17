import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { UserService } from "../../services/user.service";

@Component({
  selector: "app-reset-password",
  templateUrl: "./reset-password.component.html",
  styleUrls: ["./reset-password.component.css"],
})
export class ResetPasswordComponent implements OnInit {
  resetPasswordForm: FormGroup;
  token: string = "";
  isSubmitted = false;
  message = "";
  isError = false;
  isSuccess = false;

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.resetPasswordForm = this.fb.group({
      newPassword: ["", [Validators.required, Validators.minLength(6)]],
      confirmPassword: ["", [Validators.required]],
    });
  }

  ngOnInit(): void {
    this.token = this.route.snapshot.queryParams["token"] || "";
    if (!this.token) {
      this.message = "Invalid or missing reset token";
      this.isError = true;
    }
  }

  onSubmit(): void {
    if (this.resetPasswordForm.invalid) {
      return;
    }

    if (
      this.resetPasswordForm.value.newPassword !==
      this.resetPasswordForm.value.confirmPassword
    ) {
      this.message = "Passwords do not match";
      this.isError = true;
      return;
    }

    this.isSubmitted = true;
    this.resetMessages();

    this.userService
      .resetPassword(this.token, this.resetPasswordForm.value.newPassword)
      .subscribe(
        (response) => {
          this.message =
            response.message || "Password has been reset successfully";
          this.isSuccess = true;

          // Redirect to login after 3 seconds
          setTimeout(() => {
            this.router.navigate(["/login"]);
          }, 3000);
        },
        (err) => {
          this.message = err.error.message || "Error resetting password";
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
