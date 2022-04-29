import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IForecast } from "../domain/IForecast";

@Injectable()
export class ForecastService {

  constructor(private http: HttpClient) {
  }

  public findAll(query: string): Observable<string> {
    return this.http.get<string>(query);
  }
}
