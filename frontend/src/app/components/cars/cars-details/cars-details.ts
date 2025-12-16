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

  brands: Brand[] = [];
  accessories: Accessory[] = [];

  ngOnInit(): void {
    if (!this.car.accessories) {
      this.car.accessories = [];
    }
    this.loadBrands();
    this.loadAccessories();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['car'] && this.car) {
      this.loadBrands();
      this.loadAccessories();
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

  /**
   * Adiciona ou remove um acessório da lista do carro.
   * @param accessory O acessório que foi clicado.
   * @param event O evento de mudança do checkbox.
   */
  onAccessoryChange(accessory: Accessory, event: Event) {
    const checkbox = event.target as HTMLInputElement;

    if (checkbox.checked) {
      // Adiciona o acessório se não estiver na lista
      if (!this.isSelected(accessory)) {
        this.car.accessories.push(accessory);
      }
    } else {
      // Remove o acessório da lista
      const index = this.car.accessories.findIndex(a => a.id === accessory.id);
      if (index > -1) {
        this.car.accessories.splice(index, 1);
      }
    }
  }

  /**
   * Verifica se um acessório já está selecionado para o carro.
   * Essencial para marcar os checkboxes corretamente ao editar um carro.
   * @param accessory O acessório a ser verificado.
   * @returns true se o acessório estiver selecionado, false caso contrário.
   */
  isSelected(accessory: Accessory): boolean {
    // Usamos 'some' para verificar se o acessório está no array.
    // A comparação deve ser feita pelo 'id' para ser precisa.
    return this.car.accessories.some(a => a.id === accessory.id);
  }

  compareBrand = (b1: Brand, b2: Brand) => (b1 && b2 ? b1.id === b2.id : b1 === b2);
  compareAccessory = (a1: Accessory, a2: Accessory) => (a1 && a2 ? a1.id === a2.id : a1 === a2);

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
