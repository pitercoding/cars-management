import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Owner } from '../models/owner';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OwnerService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/owners';

  getAllOwners(): Observable<Owner[]> {
      return this.http.get<Owner[]>(`${this.apiUrl}`);
    }

    getOwnerById(id: number): Observable<Owner> {
      return this.http.get<Owner>(`${this.apiUrl}/${id}`);
    }

    postOwner(owner: Owner): Observable<Owner> {
      return this.http.post<Owner>(`${this.apiUrl}`, owner);
    }

    updateOwner(id: number, owner: Owner): Observable<Owner> {
      return this.http.put<Owner>(`${this.apiUrl}/${id}`, owner);
    }

    deleteOwner(id: number): Observable<void> {
      return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

}
