import { Component, OnInit } from "@angular/core";
import { User } from "../../../models/user.model";
import { UserService } from "../../../services/user.service";

@Component({
  selector: "app-user-management",
  templateUrl: "./user-management.component.html",
  styleUrls: ["./user-management.component.css"],
})
export class UserManagementComponent implements OnInit {
  users: User[] = [];
  filteredUsers: User[] = [];
  searchTerm: string = "";
  isLoading = true;
  message = "";
  isError = false;
  isSuccess = false;
  selectedUser: User | null = null;
  confirmDelete = false;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.isLoading = true;
    this.userService.getAllUsers().subscribe(
      (data) => {
        this.users = data;
        this.filteredUsers = [...this.users];
        this.isLoading = false;
      },
      (err) => {
        this.message = err.error.message || "Error loading users";
        this.isError = true;
        this.isLoading = false;
      }
    );
  }

  searchUsers(): void {
    if (!this.searchTerm.trim()) {
      this.filteredUsers = [...this.users];
      return;
    }

    const term = this.searchTerm.toLowerCase().trim();
    this.filteredUsers = this.users.filter(
      (user) =>
        user.username.toLowerCase().includes(term) ||
        user.email.toLowerCase().includes(term) ||
        user.firstName.toLowerCase().includes(term) ||
        user.lastName.toLowerCase().includes(term)
    );
  }

  clearSearch(): void {
    this.searchTerm = "";
    this.filteredUsers = [...this.users];
  }

  viewUserDetails(user: User): void {
    this.selectedUser = user;
    this.confirmDelete = false;
  }

  closeUserDetails(): void {
    this.selectedUser = null;
  }

  confirmDeleteUser(): void {
    this.confirmDelete = true;
  }

  cancelDelete(): void {
    this.confirmDelete = false;
  }

  deleteUser(id: number): void {
    this.userService.deleteUser(id).subscribe(
      (response) => {
        this.message = response.message || "User deleted successfully";
        this.isSuccess = true;
        this.selectedUser = null;
        this.loadUsers();
      },
      (err) => {
        this.message = err.error.message || "Error deleting user";
        this.isError = true;
      }
    );
  }

  resetMessages(): void {
    this.message = "";
    this.isError = false;
    this.isSuccess = false;
  }

  getRoleBadgeClass(role: string): string {
    switch (role) {
      case "ROLE_ADMIN":
        return "bg-danger";
      case "ROLE_MODERATOR":
        return "bg-warning text-dark";
      default:
        return "bg-secondary";
    }
  }
}
