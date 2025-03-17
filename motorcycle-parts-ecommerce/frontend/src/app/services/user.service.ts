import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "../models/user.model";

const API_URL = "http://localhost:8080/api/users";

@Injectable({
  providedIn: "root",
})
export class UserService {
  constructor(private http: HttpClient) {}

  getHttpOptions() {
    const token = window.sessionStorage.getItem("auth-token");
    return {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      }),
    };
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(API_URL, this.getHttpOptions());
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${API_URL}/${id}`, this.getHttpOptions());
  }

  getUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(
      `${API_URL}/username/${username}`,
      this.getHttpOptions()
    );
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${API_URL}/${id}`, user, this.getHttpOptions());
  }

  changePassword(
    id: number,
    currentPassword: string,
    newPassword: string
  ): Observable<any> {
    return this.http.post<any>(
      `${API_URL}/${id}/change-password`,
      {
        currentPassword,
        newPassword,
      },
      this.getHttpOptions()
    );
  }

  forgotPassword(email: string): Observable<any> {
    return this.http.post<any>(
      `${API_URL}/forgot-password`,
      { email },
      { headers: new HttpHeaders({ "Content-Type": "application/json" }) }
    );
  }

  resetPassword(token: string, newPassword: string): Observable<any> {
    return this.http.post<any>(
      `${API_URL}/reset-password`,
      {
        token,
        newPassword,
      },
      { headers: new HttpHeaders({ "Content-Type": "application/json" }) }
    );
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete<any>(`${API_URL}/${id}`, this.getHttpOptions());
  }
}
