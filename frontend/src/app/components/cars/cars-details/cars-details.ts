import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { Car } from '../../../models/car';
import { ActivatedRoute, Router } from '@angular/router';
import { inject } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cars-details',
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './cars-details.html',
  styleUrl: './cars-details.scss',
})
export class CarsDetails {
  @Input("car") car: Car = new Car(0, "");
  @Output("return") return = new EventEmitter<any>();
  router = inject(ActivatedRoute);
  router2 = inject(Router);

  constructor() {
    let id = this.router.snapshot.params['id'];
    if (id > 0) {
      this.findById(id);
    }
  }

  findById(id: number) {
    //busca backend
    let carReturned: Car = new Car(id, 'Jeep');
    this.car = carReturned;
  }

  saveChanges() {
    if (this.car.id > 0) {
      Swal.fire({
        title: 'Successfully edited!',
        icon: 'success',
        confirmButtonText: 'Ok'
      });
      this.router2.navigate(['admin/cars'], { state: { carEdited: this.car } });
    } else {
      Swal.fire({
        title: 'Saved successfully!',
        icon: 'success',
        confirmButtonText: 'Ok'
      });
      this.router2.navigate(['admin/cars'], { state: { carNew: this.car } });
    }
    this.return.emit(this.car);
  }
}
