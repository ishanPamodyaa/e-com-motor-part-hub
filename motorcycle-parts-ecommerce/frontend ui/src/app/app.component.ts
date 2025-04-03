import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Route, Router, RouterOutlet } from '@angular/router';
import { HeaderComponent } from './component/header/header.component';
import { FooterComponent } from './component/footer/footer.component';
import { AdminHeaderComponent } from './component/admin-header/admin-header.component';
import { LoginComponent } from './component/login/login.component';
import { SaleComponent } from './component/page/sale/sale.component';
import { SignUpComponent } from './component/sign-up/sign-up.component';
import { ProductComponent } from './component/product/product.component';
import { SingleProductComponent } from './component/single-product/single-product.component';
import { ShoppingCartComponent } from './component/shopping-cart/shopping-cart.component';
import { AddProductComponent } from './component/add-product/add-product.component';
import { UserStorageService } from './service/storage/user-storage.service';
import { HomeComponent } from "./component/home/home.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    HeaderComponent,
    FooterComponent,
    AdminHeaderComponent,
    LoginComponent,
    SaleComponent,
    SignUpComponent,
    ProductComponent,
    SingleProductComponent,
    ShoppingCartComponent,
    AddProductComponent,
    HomeComponent
],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'moto-part-hub';

  isCustomerInLogedIn: boolean = UserStorageService.isCustomerInLogedIn();
  isAdminInLogedIn: boolean = UserStorageService.isAdminInLogedIn();

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      this.isAdminInLogedIn = UserStorageService.isCustomerInLogedIn();
      this.isCustomerInLogedIn = UserStorageService.isCustomerInLogedIn();
    });
  }
  logout() {
    UserStorageService.signOut();
    this.router.navigate(['/login']);
  }
}
