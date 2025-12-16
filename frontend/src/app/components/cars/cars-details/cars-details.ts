import { Component, EventEmitter, Input, Output, OnInit, OnChanges, SimpleChanges, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import Swal from 'sweetalert2';

import { Car } from '../../../models/car';
import { Brand } from '../../../models/brand';
import { CarService } from '../../../services/car.service';
import { BrandService } from '../../../services/brand.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cars-details',
  standalone: true,
  imports: [CommonModule, MdbFormsModule, FormsModule],
  templateUrl: './cars-details.html',
  styleUrls: ['./cars-details.scss'],
})
export class CarsDetails implements OnInit, OnChanges {
  @Input() car: Car = new Car();
  @Output() return = new EventEmitter<Car>();

  private carService = inject(CarService);
  private brandService = inject(BrandService);

  brands: Brand[] = [];

  ngOnInit(): void {
    this.loadBrands();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['car'] && this.car) {
      this.loadBrands();
    }
  }

  loadBrands(): void {
    this.brandService.getAllBrands().subscribe({
      next: (list) => {
        this.brands = list.sort((a, b) => a.name.localeCompare(b.name));

        if (this.car.brand) {
          const found = this.brands.find((b) => b.id === this.car.brand!.id);
          if (found) {
            this.car.brand = found;
          }
        } else {
          this.car.brand = undefined;
        }
      },
      error: () => Swal.fire('Failed to load brands', '', 'error'),
    });
  }

  compareBrand = (b1: Brand, b2: Brand) => (b1 && b2 ? b1.id === b2.id : b1 === b2);

  saveCar(): void {
    if (!this.car.brand?.id) {
      Swal.fire('Please select a Brand', '', 'warning');
      return;
    }

    const isEdit = !!this.car.id;
    const request$ = isEdit
      ? this.carService.updateCar(this.car.id!, this.car)
      : this.carService.postCar(this.car);

    request$.subscribe({
      next: () => {
        Swal.fire(isEdit ? 'Edited successfully!' : 'Saved successfully!', '', 'success');
        this.return.emit(this.car);
      },
      error: () => {
        Swal.fire(isEdit ? 'Failed to update this car' : 'Failed to save this car', '', 'error');
      },
    });
  }
}
