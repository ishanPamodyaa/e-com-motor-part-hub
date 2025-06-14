import { Injectable } from '@angular/core';

const TOKEN = 'ecom-token';
const USER = 'ecom-user';

@Injectable({
  providedIn: 'root'
})
export class UserStorageService {

  constructor() { }
  public saveToken(token : string):void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN, token);
  }
  public saveUser(user: any):void{
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER, JSON.stringify(user));
  }
  static getToken():string{
    return localStorage.getItem(TOKEN) as string;
  }
  static getUser():any{
    return JSON.parse(localStorage.getItem(USER) as string);
  }
  static getUserId() : string{

    const user = this.getUser();
    if(user == null){
      return '';
    }
    return user.id;
  }

  static getUserRole():string{
    const user = this.getUser();
    if(user == null){
      return '';
    }
    return user.role;
  }

  static isAdminInLogedIn():boolean{

    if (this.getToken() === null) {
      return false;
    }

    return this.getUserRole() === 'ADMIN';
  }
  static isCustomerInLogedIn():boolean{

    if (this.getToken() === null) {
      return false;
    }

    return this.getUserRole() === 'CUSTOMER';
  }

  static signOut():void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }



}
