import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { UserStorageService } from './storage/user-storage.service';

const BASE_URL = 'http://localhost:8080';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  addCategory(categoryDTO: any): Observable<any> {
    console.log("adding Category" + JSON.stringify(categoryDTO));

    // Check if admin is logged in
    if (!UserStorageService.isAdminInLogedIn()) {
      console.log("need admin loged in");
      return throwError(() => new Error('Admin privileges required'));
    }

    // Get and verify token
    const token = UserStorageService.getToken();
    if (!token) {
      console.log("no token");
      return throwError(() => new Error('No authentication token found'));
    }

    // Set up headers with token
    const headers = new HttpHeaders()
      .set('Authorization', `Bearer ${token}`)
      .set('Content-Type', 'application/json');


    // Make the request
    return this.http.post(`${BASE_URL}/api/category/create`, categoryDTO, {
      headers: this.createAuthorizationHeader(),
      withCredentials: true
    }).pipe(
      catchError(error => {
        console.log('Error response:', error);
        if (error.status === 403) {
          return throwError(() => new Error('Not authorized to perform this action. Please check your admin privileges.'));
        }
        return throwError(() => new Error('Failed to create category. Please try again.'));
      })
    );
  }
  private createAuthorizationHeader(): HttpHeaders {

    return new HttpHeaders().set(
      'Authorization', 'Bearer ' + UserStorageService.getToken()
    )
  }
}
