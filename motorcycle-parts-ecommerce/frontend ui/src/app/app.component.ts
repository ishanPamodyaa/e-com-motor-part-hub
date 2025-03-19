import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './component/header/header.component';
import { FooterComponent } from './component/footer/footer.component';
import { AdminHeaderComponent } from './component/admin-header/admin-header.component';
import {LoginComponent} from './component/login/login.component';
import {SaleComponent} from './component/page/sale/sale.component';
import {SignUpComponent} from './component/sign-up/sign-up.component';
import {ProductComponent} from './component/product/product.component';
import {SingleProductComponent} from './component/single-product/single-product.component';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    HeaderComponent,
    FooterComponent,
    AdminHeaderComponent,
    LoginComponent,
    SaleComponent,
    SignUpComponent,
    ProductComponent,
    SingleProductComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'moto-part-hub';
}
