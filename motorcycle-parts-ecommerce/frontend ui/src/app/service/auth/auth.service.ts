import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { map, Observable } from 'rxjs';
import {UserStorageService} from '../storage/user-storage.service';
// import {observableToBeFn} from 'rxjs/internal/testing/TestScheduler';

const BASE_URL = 'http://localhost:8080';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient, private userStorageService: UserStorageService ) {}

  register(signupRequest: any): Observable<any> {
    return this.http.post(`${BASE_URL}/sign-up`, signupRequest);
  }

  login(userName: string, password: string) {
    const headers = new HttpHeaders().set( 'Content-Type', 'application/json');
    const body= {userName:userName, password:password};

    return this.http.post(`${BASE_URL}/authenticate`, body, {headers,observe:'response'}).pipe(
      map((res)=>{
        const authHeader = res.headers.get('authorization');
        if (!authHeader) {
          throw new Error('Authorization header not found');
        }
        const  token = authHeader.substring(7);
        const  user = res.body;
        if(token && user){
          this.userStorageService.saveToken(token);
          this.userStorageService.saveUser(user);
            return  true;
        }
        return false;
    })
  );
  }
}
