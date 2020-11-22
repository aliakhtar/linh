import { Component } from '@angular/core';
import {CmdListService} from './services/cmd-list.service';
import {CommsService} from './services/comms.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  constructor(comms: CommsService) {
  }
}
