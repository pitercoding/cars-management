import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Accessory } from '../models/accessory';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AccessoryService {
  private http = inject(HttpClient);
  private apiUrl = environment.SERVER + "/api/accessories";

  getAllAccessories(): Observable<Accessory[]> {
    return this.http.get<Accessory[]>(`${this.apiUrl}`);
  }

  getAccessoryById(id: number): Observable<Accessory> {
    return this.http.get<Accessory>(`${this.apiUrl}/${id}`);
  }

  postAccessory(accessory: Accessory): Observable<Accessory> {
    return this.http.post<Accessory>(`${this.apiUrl}`, accessory);
  }

  updateAccessory(id: number, accessory: Accessory): Observable<Accessory> {
    return this.http.put<Accessory>(`${this.apiUrl}/${id}`, accessory);
  }

  deleteAccessory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
