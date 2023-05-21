export interface Style {
    styles_name: string
    styles_id: number
}

export interface Beer {
    beer_id: number
    beer_name: string
    brewery_id: number
    brewery_name: string
}

export interface Brewery {
    brewery_id: number
    brewery_name: string
    brewery_descript: string
    address1: string
    address2: string
    city: string
    phone: string
    website: string
    beerList: Beerlist[]
}

export interface Beerlist {
    beer_id: number
    beer_name: string
    beer_descript: string
}

export interface Order {
    brewery_id: number
    quantities: Orders[]
}

export interface Orders {
    beer_id: number
    quantity: number
}