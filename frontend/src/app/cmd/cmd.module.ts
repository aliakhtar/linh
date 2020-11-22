import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InputComponent } from './input/input.component';
import { OutputComponent } from './output/output.component';
import { OutputItemComponent } from './output-item/output-item.component';
import { SuggestionsComponent } from './suggestions/suggestions.component';



@NgModule({
  declarations: [InputComponent, OutputComponent, OutputItemComponent, SuggestionsComponent],
  exports: [
    InputComponent,
    OutputComponent,
    SuggestionsComponent
  ],
  imports: [
    CommonModule
  ]
})
export class CmdModule { }
