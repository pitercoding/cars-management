import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Car } from '../models/car';

@Injectable({
  providedIn: 'root',
})
export class CarService {
  http = inject(HttpClient);

  API = 'http://localhost:8080/api/cars';

  constructor() {}

  getAllCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.API + '/getAllCars');
  }

  deleteCar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/deleteCar/${id}`);
  }
}
