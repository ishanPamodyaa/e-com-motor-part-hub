import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import  {CommonModule} from '@angular/common';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  imports: [
    FormsModule,
    CommonModule,
  ],
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent {
  product = {
    name: '',
    brand:'',
    model:'',
    year:'',
    capacity:'',
    category:'',
    discount:'',
    stockQty:'',
    description: '',
    price: 0
  };

  imagePreviews: string[] = [];

  onImageChange(event: any) {
    this.imagePreviews = [];
    const files = event.target.files;

    if (files && files.length > 0) {
      Array.from(files).forEach((file: any) => {
        if (file.type.startsWith('image/')) {
          const reader = new FileReader();
          reader.onload = (e: any) => {
            this.imagePreviews.push(e.target.result);
          };
          reader.readAsDataURL(file);
        }
      });
    }
  }
  onSubmit() {
    console.log('Product Data:', this.product);
    console.log('Images:', this.imagePreviews);
    alert('Product added successfully!');
  }
  getYears(): number[] {
    const startYear = 1980;
    const endYear = 2025;
    const years = [];
    for (let year = startYear; year <= endYear; year++) {
      years.push(year);
    }
    return years;
  }

  getCategory():String[]{
    return [
      "Bearing",
      "Drive Belts & Pulleys",
      "Chain Sprocket Kit",
      "Gaskets & Seals",
      "Engine Parts",
      "Foot Controls & Pegs",
      "Handlebars & Controls",
      "Suspension",
      "Cables & Hoses",
      "Lighting",
      "Ignition System Parts",
      "Electronic Parts",
      "Frame & Body",
      "Air Intake & Filters",
      "Brake Discs and Pads",
      "Tires and Wheels",
      "Service Kits"
    ];

  }
  getBrand():String[]{
    return [
      "Honda",
      "Yamaha",
      "Kawasaki",
      "Suzuki",
      "Harley-Davidson",
      "Ducati",
      "BMW",
      "KTM",
      "Triumph",
      "Royal Enfield",
      "Indian",
      "Aprilia",
      "MV Agusta",
      "Benelli",
      "Moto Guzzi",
      "Husqvarna",
      "Bajaj",
      "Hero",
      "TVS",
      "CFMoto",
      "Zero Motorcycles",
      "Energica",
      "Loncin",
      "QJ Motor"
    ];

  }

}
