export class User {
  id?: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  phone?: string;
  roles?: string[];

  constructor(
    username: string = '',
    email: string = '',
    firstName: string = '',
    lastName: string = '',
    phone: string = '',
    roles: string[] = []
  ) {
    this.username = username;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.roles = roles;
  }
} 