import { Injectable } from '@angular/core';

import { ITicker } from "./iticker";
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';

@Injectable()
export class TickerService {

  //TODO: once CoinMarketCap is available, replace URL to CMC API
	private _cmcLastPriceUrl = 'https://api.livecoin.net/exchange/ticker?currencyPair=XSN/BTC';
	// private _cmcLastPriceUrl = './api/tickers/satoshi.json'; # This was being used for local dev

	constructor(private _http: HttpClient) { }

	getLastPrice(): Observable<ITicker[]> {
		return this._http.get<ITicker[]>(this._cmcLastPriceUrl)
			.do(data => console.log('ALL: ' + JSON.stringify(data)))
			.catch(this.handleError);
	}

	private handleError(err: HttpErrorResponse) {
		// in a real world app, we may send the server to some remote logging infrastructure
		// instead of just logging it to the console
		let errorMessage = '';
		if (err.error instanceof Error) {
			// A client-side or network error occurred. Handle it accordingly.
			errorMessage = `An error occurred: ${err.error.message}`;
		} else {
			// The backend returned an unsuccessful response code.
			// The response body may contain clues as to what went wrong,
			errorMessage = `Server returned code: ${err.status}, error message is: ${err.message}`;
		}
		console.error(errorMessage);
		return Observable.throw(errorMessage);
	}

}
