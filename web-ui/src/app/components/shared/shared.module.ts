import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TickerPanelsComponent } from "./ticker-panels/ticker-panels.component";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    TickerPanelsComponent
  ],
  exports: [
    CommonModule,
    TickerPanelsComponent
  ]
})
export class SharedModule { }
