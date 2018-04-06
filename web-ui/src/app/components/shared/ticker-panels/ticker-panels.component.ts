import { Component, OnInit } from '@angular/core';
import { ITicker } from "./iticker";
import { TickerService } from "./ticker.service";

@Component({
	selector: 'app-ticker-panels',
	templateUrl: './ticker-panels.component.html',
	styleUrls: [ './ticker-panels.component.css' ]
})
export class TickerPanelsComponent implements OnInit {

	errorMessage: string;

	ticker: ITicker[] = [];

	constructor(private _tickerService: TickerService) {

	}

	ngOnInit(): void {
		this._tickerService.getLastPrice()
			.subscribe(tickers => {
				this.ticker = Array.of(tickers);
				console.log(this.ticker)
			},
				error => this.errorMessage = <any>error);
	}

}
