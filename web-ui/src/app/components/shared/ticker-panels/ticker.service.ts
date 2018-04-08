import { Injectable } from '@angular/core';

import { ITicker } from "./iticker";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';

@Injectable()
export class TickerService {

	//TODO: once CoinMarketCap is available, replace URL to CMC API
	private _cmcLastPriceUrl = 'https://api.livecoin.net/exchange/ticker?currencyPair=XSN/BTC';
	// private _cmcLastPriceUrl = './api/tickers/satoshi.json'; // This was being used for local dev

	constructor(private _http: HttpClient) { }

	getLastPrice(): Observable<ITicker> {
		return this._http.get<ITicker>(this._cmcLastPriceUrl)
	}
}
