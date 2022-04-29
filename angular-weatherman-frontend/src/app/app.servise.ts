import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export class AppServise {
    public heroesUrl = 'http://localhost:8080/weatherapi/1';

    constructor(private http: HttpClient) {
        this.getHeroes();
    }

    getHeroes(): Observable<string> {
        console.log(this.http.get<string>(this.heroesUrl))
        return this.http.get<string>(this.heroesUrl)
      }
}
