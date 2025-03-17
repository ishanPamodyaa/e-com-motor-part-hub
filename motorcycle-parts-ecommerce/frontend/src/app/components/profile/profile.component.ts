import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { User } from "../../models/user.model";
import { UserService } from "../../services/user.service";
import { TokenStorageService } from "../../services/token-storage.service";

@Component({
  selector: "app-profile",
  templateUrl: "./profile.component.html",
  styleUrls: ["./profile.component.css"],
})
export class ProfileComponent implements OnInit {
  currentUser: User = new User();
  profileForm: FormGroup;
  passwordForm: FormGroup;
  isEditing = false;
  isChangingPassword = false;
  message = "";
  isError = false;
  isSuccess = false;

  constructor(
    private userService: UserService,
    private tokenStorage: TokenStorageService,
    private fb: FormBuilder
  ) {
    this.profileForm = this.fb.group({
      firstName: ["", [Validators.required]],
      lastName: ["", [Validators.required]],
      email: ["", [Validators.required, Validators.email]],
      phone: [""],
    });

    this.passwordForm = this.fb.group({
      currentPassword: ["", [Validators.required]],
      newPassword: ["", [Validators.required, Validators.minLength(6)]],
      confirmPassword: ["", [Validators.required]],
    });
  }

  ngOnInit(): void {
    this.loadUserProfile();
  }

  loadUserProfile(): void {
    const user = this.tokenStorage.getUser();
    if (user && user.id) {
      this.userService.getUserById(user.id).subscribe(
        (data) => {
          this.currentUser = data;
          this.profileForm.patchValue({
            firstName: this.currentUser.firstName,
            lastName: this.currentUser.lastName,
            email: this.currentUser.email,
            phone: this.currentUser.phone,
          });
        },
        (err) => {
          this.message = err.error.message || "Error loading profile";
          this.isError = true;
        }
      );
    }
  }

  toggleEdit(): void {
    this.isEditing = !this.isEditing;
    this.resetMessages();
  }

  toggleChangePassword(): void {
    this.isChangingPassword = !this.isChangingPassword;
    if (!this.isChangingPassword) {
      this.passwordForm.reset();
    }
    this.resetMessages();
  }

  updateProfile(): void {
    if (this.profileForm.invalid) {
      return;
    }

    const updatedUser = {
      ...this.currentUser,
      firstName: this.profileForm.value.firstName,
      lastName: this.profileForm.value.lastName,
      email: this.profileForm.value.email,
      phone: this.profileForm.value.phone,
    };

    this.userService.updateUser(this.currentUser.id!, updatedUser).subscribe(
      (data) => {
        this.currentUser = data;
        this.isEditing = false;
        this.message = "Profile updated successfully!";
        this.isSuccess = true;

        // Update stored user data
        const user = this.tokenStorage.getUser();
        if (user) {
          user.firstName = data.firstName;
          user.lastName = data.lastName;
          user.email = data.email;
          this.tokenStorage.saveUser(user);
        }
      },
      (err) => {
        this.message = err.error.message || "Error updating profile";
        this.isError = true;
      }
    );
  }

  changePassword(): void {
    if (this.passwordForm.invalid) {
      return;
    }

    if (
      this.passwordForm.value.newPassword !==
      this.passwordForm.value.confirmPassword
    ) {
      this.message = "New password and confirm password do not match";
      this.isError = true;
      return;
    }

    this.userService
      .changePassword(
        this.currentUser.id!,
        this.passwordForm.value.currentPassword,
        this.passwordForm.value.newPassword
      )
      .subscribe(
        (response) => {
          this.message = response.message || "Password changed successfully!";
          this.isSuccess = true;
          this.isChangingPassword = false;
          this.passwordForm.reset();
        },
        (err) => {
          this.message = err.error.message || "Error changing password";
          this.isError = true;
        }
      );
  }

  resetMessages(): void {
    this.message = "";
    this.isError = false;
    this.isSuccess = false;
  }
}
