import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable, tap } from "rxjs";
import { Beer, Brewery, Order, Style } from "./models";

const URL_STYLES = 'http://localhost:8080/api/styles';
const URL_BREWERY = 'http://localhost:8080/api/brewery';
const URL_BEERS = 'http://localhost:8080/api/beers';
const URL_ORDER = 'http://localhost:8080/api/order';

@Injectable()
export class BeerService {

    http = inject(HttpClient)

    getStyles(): Observable<Style[]> {
        
        return this.http.get<Style[]>(URL_STYLES).pipe(
            tap(styles => console.info(styles))           
        );

    }

    getBreweryByStyle(id: number): Observable<Beer[]> {
        
        const params = new HttpParams()
            .set("id", id);

        return this.http.get<Beer[]>(URL_BREWERY, { params }).pipe(
            tap(b => console.info(b))
        );
    }

    getBeersByBrewery(id: number): Observable<Brewery> {

        const params = new HttpParams()
            .set("brewery_id", id);

        return this.http.get<Brewery>(URL_BEERS, { params }).pipe(
            tap(result => console.info(result))
        );
    }

    postOrderByJson(brewery_id: number, quantities: Array<{ beer_id: number, 
        quantity: number }>): Observable<string> {

        const order: Order = {
            brewery_id: brewery_id,
            quantities: quantities
        }

        console.info('order: ', order)

        return this.http.post<string>(URL_ORDER, order).pipe(
            tap(result => console.info(result))
        );
    }
    
}