import { OwnerService } from './../../../services/owner.service';
import { AccessoryService } from './../../../services/accessory.service';
import {
  Component,
  EventEmitter,
  Input,
  Output,
  OnInit,
  OnChanges,
  SimpleChanges,
  inject,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import Swal from 'sweetalert2';

import { Car } from '../../../models/car';
import { Brand } from '../../../models/brand';
import { CarService } from '../../../services/car.service';
import { BrandService } from '../../../services/brand.service';
import { CommonModule } from '@angular/common';
import { Accessory } from '../../../models/accessory';
import { Owner } from '../../../models/owner';

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
  private AccessoryService = inject(AccessoryService);
  private ownerService = inject(OwnerService);

  brands: Brand[] = [];
  accessories: Accessory[] = [];
  owners: Owner[] = [];

  readonly minYear = 1886;
  readonly currentYear = new Date().getFullYear();

  ngOnInit(): void {
    if (!this.car.accessories) {
      this.car.accessories = [];
    }
    this.loadBrands();
    this.loadAccessories();
    this.loadOwners();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['car'] && this.car) {
      this.loadBrands();
      this.loadAccessories();
      this.loadOwners();
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

  loadAccessories(): void {
    this.AccessoryService.getAllAccessories().subscribe({
      next: (list) => {
        this.accessories = list.sort((a, b) => a.id! - b.id!);

        if (this.car.accessories && this.car.accessories.length) {
          this.car.accessories = this.car.accessories.map(
            (acc) => this.accessories.find((a) => a.id === acc.id)!
          );
        }
      },
      error: () => Swal.fire('Failed to load accessories', '', 'error'),
    });
  }

  loadOwners(): void {
    this.ownerService.getAvailableOwners().subscribe({
      next: (list) => {
        this.owners = list;

        if (this.car.owner) {
          const exists = this.owners.some((o) => o.id === this.car.owner!.id);
          if (!exists) {
            this.owners.unshift(this.car.owner);
          }
        }
      },
      error: () => Swal.fire('Failed to load owners', '', 'error'),
    });
  }

  onAccessoryChange(accessory: Accessory, event: Event) {
    const checkbox = event.target as HTMLInputElement;

    if (checkbox.checked) {
      if (!this.isSelected(accessory)) {
        this.car.accessories.push(accessory);
      }
    } else {
      const index = this.car.accessories.findIndex((a) => a.id === accessory.id);
      if (index > -1) {
        this.car.accessories.splice(index, 1);
      }
    }
  }

  isSelected(accessory: Accessory): boolean {
    return this.car.accessories.some((a) => a.id === accessory.id);
  }

  compareBrand = (b1: Brand, b2: Brand) => (b1 && b2 ? b1.id === b2.id : b1 === b2);
  compareAccessory = (a1: Accessory, a2: Accessory) => (a1 && a2 ? a1.id === a2.id : a1 === a2);
  compareOwner = (o1: Owner, o2: Owner) => o1 && o2 ? o1.id === o2.id : o1 === o2;

  saveCar(): void {
    const year = this.car.manufactureYear;
    const currentYear = this.currentYear;

    if (year < this.minYear || year > currentYear) {
      Swal.fire(
        `Manufacture Year must be between ${this.minYear} and ${currentYear}`,
        '',
        'warning'
      );
      return;
    }

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
