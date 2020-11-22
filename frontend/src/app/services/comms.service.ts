import { Injectable } from '@angular/core';
import {CmdListService} from './cmd-list.service';
import {Cmd} from '../models/cmd';
import {ConnService} from './conn.service';
import {ConnEvent} from '../models/connEvent';

@Injectable({
  providedIn: 'root'
})
export class CommsService {

  constructor(private cmdList:CmdListService, private conn: ConnService) {

    conn.events.subscribe(e => this.handleEvent(e))
    cmdList.cmds.subscribe(c => this.handleCommand(c))
  }

  handleCommand(cmd:Cmd):void {
    switch (cmd.text) {
      case 'connect':
        this.conn.connect();
        break;

      default:
        this.conn.sendMsg(cmd.text);
    }
  }

  private handleEvent(e:ConnEvent):void {
    console.log("Received event", e);
    if (e.data)
      this.cmdList.newOutput(e.data);
  }
}
