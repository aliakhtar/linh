import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { BallComponent } from './header/ball/ball.component';
import { ContentComponent } from './content/content.component';
import { InputBoxComponent } from './input-box/input-box.component';
import {CmdModule} from '../cmd/cmd.module';
import {FormsModule} from '@angular/forms';
import { AutoScrollComponent } from './auto-scroll/auto-scroll.component';


@NgModule({
  declarations: [HeaderComponent, BallComponent, ContentComponent, InputBoxComponent, AutoScrollComponent],
  exports: [
    HeaderComponent,
    ContentComponent,
    InputBoxComponent,
    AutoScrollComponent
  ],
  imports: [
    CommonModule,
    CmdModule,
    FormsModule
  ]
})
export class LayoutModule { }
