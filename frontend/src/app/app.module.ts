import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

import {LayoutModule} from './layout/layout.module';
import {CmdModule} from './cmd/cmd.module';
import {TypewriterService} from './services/typewriter.service';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    LayoutModule,
    CmdModule
  ],

  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
