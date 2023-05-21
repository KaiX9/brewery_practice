import { Component, OnInit, inject } from '@angular/core';
import { BeerService } from './beer.service';
import { Beer, Beerlist, Brewery, Style } from './models';
import { Observable, map, tap } from 'rxjs';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  beerSvc = inject(BeerService)
  styles$!: Observable<Style[]>
  brewery$!: Observable<Beer[]>
  brewery_details$!: Observable<Brewery>
  beerList!: Beerlist[]
  quantities: Array<{ beer_id: number; quantity: number; }> = []
  brewery_id: number = 0
  order_id: string = ''

  ngOnInit(): void {
    this.styles$ = this.beerSvc.getStyles();
  }

  getBreweryByStyle(id: number) {
    console.info('style_id: ', id);
    this.brewery$ = this.beerSvc.getBreweryByStyle(id);
  }

  getBeersByBrewery(id: number) {
    this.brewery_id = id;
    console.info('brewery_id: ', this.brewery_id);
    this.brewery_details$ = this.beerSvc.getBeersByBrewery(id);
    this.brewery_details$.pipe(
      map(result => result.beerList)
    ).subscribe(beerList => {
      this.beerList = beerList;
      console.info('beerList: ', this.beerList);
    })
  }

  updateQuantity(index: number, value: number) {
    this.quantities[index] = { beer_id: this.beerList[index].beer_id, 
      quantity: value};
    console.info('quantity: ', this.quantities);
  }

  reload() {
    location.reload();
  }

  placeOrder() {
    console.info('Passing in id: ', this.brewery_id)
    console.info('Passing in quantities: ', this.quantities)
    this.beerSvc.postOrderByJson(this.brewery_id, this.quantities).subscribe(
      (resp: any) => {
        this.order_id = resp.order_id;
        alert(`Your order has been placed. Your order number is ${this.order_id}`);
        location.reload();
      }
    );
  }

}