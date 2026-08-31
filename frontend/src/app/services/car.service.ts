import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Car } from '../models/car';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CarService {
  private http = inject(HttpClient);
  private apiUrl = environment.SERVER + "/api/cars";

  getAllCars(): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.apiUrl}`);
  }

  getCarById(id: number): Observable<Car> {
    return this.http.get<Car>(`${this.apiUrl}/${id}`);
  }

  postCar(car: Car): Observable<Car> {
    return this.http.post<Car>(`${this.apiUrl}`, this.toRequestPayload(car));
  }

  updateCar(id: number, car: Car): Observable<Car> {
    return this.http.put<Car>(`${this.apiUrl}/${id}`, this.toRequestPayload(car));
  }

  deleteCar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // The backend's CarRequestDTO expects ids for relations (brandId, ownerId,
  // accessoryIds), not the full nested objects the forms bind to for display.
  private toRequestPayload(car: Car) {
    return {
      name: car.name,
      model: car.model,
      manufactureYear: car.manufactureYear,
      brandId: car.brand?.id ?? null,
      ownerId: car.owner?.id ?? null,
      accessoryIds: (car.accessories ?? []).map((a) => a.id),
    };
  }
}
