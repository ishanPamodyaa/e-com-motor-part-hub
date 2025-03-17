import { Component, OnInit, OnDestroy } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.css"],
  standalone: true,
  imports: [CommonModule, RouterModule],
})
export class HomeComponent implements OnInit, OnDestroy {
  // Banner carousel
  currentSlide = 0;
  private slideInterval: any;

  // Categories
  categories = [
    { id: 1, name: "Engine Parts", icon: "fas fa-cogs", color: "#e74c3c" },
    { id: 2, name: "Brakes", icon: "fas fa-brake-warning", color: "#3498db" },
    { id: 3, name: "Suspension", icon: "fas fa-car-crash", color: "#2ecc71" },
    { id: 4, name: "Exhaust", icon: "fas fa-wind", color: "#f39c12" },
    { id: 5, name: "Tires", icon: "fas fa-circle-notch", color: "#9b59b6" },
    { id: 6, name: "Lighting", icon: "fas fa-lightbulb", color: "#1abc9c" },
    { id: 7, name: "Accessories", icon: "fas fa-motorcycle", color: "#34495e" },
    { id: 8, name: "Helmets", icon: "fas fa-hard-hat", color: "#e67e22" },
    { id: 9, name: "Apparel", icon: "fas fa-tshirt", color: "#27ae60" },
    { id: 10, name: "Tools", icon: "fas fa-tools", color: "#c0392b" },
    { id: 11, name: "Electronics", icon: "fas fa-microchip", color: "#16a085" },
    { id: 12, name: "Oils & Fluids", icon: "fas fa-oil-can", color: "#d35400" },
  ];

  // Featured Products
  featuredProducts = [
    {
      id: 1,
      name: "Premium Motorcycle Engine Oil Filter",
      category: "Engine Parts",
      currentPrice: 12.99,
      originalPrice: 19.99,
      discount: 35,
      image: "assets/images/products/oil-filter.jpg",
      rating: 4.5,
      reviewCount: 128,
      isNew: false,
    },
    {
      id: 2,
      name: "High Performance Brake Pads Set",
      category: "Brakes",
      currentPrice: 34.99,
      originalPrice: 49.99,
      discount: 30,
      image: "assets/images/products/brake-pads.jpg",
      rating: 4.8,
      reviewCount: 95,
      isNew: false,
    },
    {
      id: 3,
      name: "Adjustable Front Fork Suspension Kit",
      category: "Suspension",
      currentPrice: 189.99,
      originalPrice: 249.99,
      discount: 24,
      image: "assets/images/products/suspension.jpg",
      rating: 4.7,
      reviewCount: 42,
      isNew: false,
    },
    {
      id: 4,
      name: "Carbon Fiber Exhaust Pipe",
      category: "Exhaust",
      currentPrice: 129.99,
      originalPrice: 159.99,
      discount: 19,
      image: "assets/images/products/exhaust.jpg",
      rating: 4.6,
      reviewCount: 67,
      isNew: false,
    },
    {
      id: 5,
      name: "All-Weather Sport Motorcycle Tires",
      category: "Tires",
      currentPrice: 79.99,
      originalPrice: 99.99,
      discount: 20,
      image: "assets/images/products/tires.jpg",
      rating: 4.4,
      reviewCount: 156,
      isNew: false,
    },
    {
      id: 6,
      name: "LED Headlight Conversion Kit",
      category: "Lighting",
      currentPrice: 59.99,
      originalPrice: 89.99,
      discount: 33,
      image: "assets/images/products/led-headlight.jpg",
      rating: 4.9,
      reviewCount: 78,
      isNew: false,
    },
    {
      id: 7,
      name: "Motorcycle Phone Mount Holder",
      category: "Accessories",
      currentPrice: 24.99,
      originalPrice: 34.99,
      discount: 29,
      image: "assets/images/products/phone-mount.jpg",
      rating: 4.3,
      reviewCount: 214,
      isNew: false,
    },
    {
      id: 8,
      name: "Full Face Racing Helmet DOT Certified",
      category: "Helmets",
      currentPrice: 149.99,
      originalPrice: 199.99,
      discount: 25,
      image: "assets/images/products/helmet.jpg",
      rating: 4.8,
      reviewCount: 183,
      isNew: false,
    },
  ];

  // New Arrivals
  newArrivals = [
    {
      id: 9,
      name: "Bluetooth Motorcycle Communication System",
      category: "Electronics",
      currentPrice: 119.99,
      originalPrice: null,
      image: "assets/images/products/bluetooth.jpg",
      rating: 4.7,
      reviewCount: 32,
      isNew: true,
    },
    {
      id: 10,
      name: "Waterproof Motorcycle Riding Gloves",
      category: "Apparel",
      currentPrice: 39.99,
      originalPrice: null,
      image: "assets/images/products/gloves.jpg",
      rating: 4.5,
      reviewCount: 18,
      isNew: true,
    },
    {
      id: 11,
      name: "Digital Tire Pressure Gauge",
      category: "Tools",
      currentPrice: 15.99,
      originalPrice: null,
      image: "assets/images/products/tire-gauge.jpg",
      rating: 4.6,
      reviewCount: 24,
      isNew: true,
    },
    {
      id: 12,
      name: "Motorcycle Chain Cleaning & Lubrication Kit",
      category: "Maintenance",
      currentPrice: 29.99,
      originalPrice: null,
      image: "assets/images/products/chain-kit.jpg",
      rating: 4.8,
      reviewCount: 15,
      isNew: true,
    },
    {
      id: 13,
      name: "Motorcycle Cover - All Weather Protection",
      category: "Accessories",
      currentPrice: 49.99,
      originalPrice: null,
      image: "assets/images/products/cover.jpg",
      rating: 4.4,
      reviewCount: 27,
      isNew: true,
    },
    {
      id: 14,
      name: "Motorcycle Security Alarm System",
      category: "Security",
      currentPrice: 69.99,
      originalPrice: null,
      image: "assets/images/products/alarm.jpg",
      rating: 4.3,
      reviewCount: 12,
      isNew: true,
    },
    {
      id: 15,
      name: "Motorcycle Battery Tender & Charger",
      category: "Electronics",
      currentPrice: 45.99,
      originalPrice: null,
      image: "assets/images/products/battery-charger.jpg",
      rating: 4.9,
      reviewCount: 21,
      isNew: true,
    },
    {
      id: 16,
      name: "Motorcycle GPS Navigation System",
      category: "Electronics",
      currentPrice: 159.99,
      originalPrice: null,
      image: "assets/images/products/gps.jpg",
      rating: 4.7,
      reviewCount: 9,
      isNew: true,
    },
  ];

  // Brands
  brands = [
    { id: 1, name: "Honda", logo: "assets/images/brands/honda.png" },
    { id: 2, name: "Yamaha", logo: "assets/images/brands/yamaha.png" },
    { id: 3, name: "Kawasaki", logo: "assets/images/brands/kawasaki.png" },
    { id: 4, name: "Suzuki", logo: "assets/images/brands/suzuki.png" },
    { id: 5, name: "Harley-Davidson", logo: "assets/images/brands/harley.png" },
    { id: 6, name: "Ducati", logo: "assets/images/brands/ducati.png" },
    { id: 7, name: "BMW", logo: "assets/images/brands/bmw.png" },
    { id: 8, name: "KTM", logo: "assets/images/brands/ktm.png" },
  ];

  constructor() {}

  ngOnInit(): void {
    this.startSlideShow();
  }

  ngOnDestroy(): void {
    this.stopSlideShow();
  }

  // Banner carousel methods
  startSlideShow(): void {
    this.slideInterval = setInterval(() => {
      this.nextSlide();
    }, 5000);
  }

  stopSlideShow(): void {
    if (this.slideInterval) {
      clearInterval(this.slideInterval);
    }
  }

  nextSlide(): void {
    this.currentSlide = (this.currentSlide + 1) % 3;
  }

  prevSlide(): void {
    this.currentSlide = (this.currentSlide - 1 + 3) % 3;
  }

  goToSlide(index: number): void {
    this.currentSlide = index;
    this.stopSlideShow();
    this.startSlideShow();
  }
}
