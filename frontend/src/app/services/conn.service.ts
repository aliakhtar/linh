import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {webSocket, WebSocketSubject, WebSocketSubjectConfig} from 'rxjs/webSocket';
import {Output} from '../models/output';

import {WebSocketMessage} from 'rxjs/internal/observable/dom/WebSocketSubject';
import {BehaviorSubject, Observable, Subject, timer} from 'rxjs';
import {ConnEvent} from '../models/connEvent';
import {delayWhen, retryWhen, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ConnService{

  private wsUrl: string = environment.wsUrl;
  private ws: WebSocketSubject<any>;
  private reconnectDelaySecs:number = 1;

  connected:boolean = false;

  events = new Subject<ConnEvent>();

  private readonly config: WebSocketSubjectConfig<any> = {
    url: this.wsUrl,

    deserializer: (e: MessageEvent) => {
      if (e.data === 'pong')
        return 'pong';

      return JSON.parse(e.data);
    },
    serializer: (value: any) => value,
    openObserver: {
      next: _ => {
        this.connected = true
        this.events.next(ConnEvent.connected)
        setTimeout(() => this.sendPing(), 5000);
      }
    },

  }

  constructor() { }


  connect():void {
    if (this.connected) {
      this.events.next(ConnEvent.warning('Already connected'));
      return;
    }

    this.events.next(ConnEvent.connecting);
    console.log("sent connecting");
    this.ws = webSocket(this.config);
    this.ws.subscribe(
      msg => this.onMsg(msg),
    err => this.onError(err),
    () =>this.onFinished()
    );
  }

  sendMsg(msg:string):void {
    if (! this.connected) {
      console.warn(`"not connected, can't send msg: ${msg}`);
      this.events.next(ConnEvent.warning('Not connected.'));
    }
    else
      this.ws.next(msg)
  }


  private onMsg(msg: any):void {
    if (msg === 'pong') {
      console.debug("pong received");
      setTimeout(() => this.sendPing(), 5000);
      return;
    }

    const output = Output.fromJson(msg)
    this.events.next(ConnEvent.data(output))
  }

  private onError(err:any):void {
    console.error("ws error", err);
    this.events.next(ConnEvent.error);
    this.onFinished();
  }

  private onFinished():void {
    console.log("ws stream completed");
    this.connected = false;
    this.events.next(ConnEvent.disconnected);
    this.reconnect();
  }

  private reconnect():void {
    this.reconnectDelaySecs *= 2;
    this.events.next(ConnEvent.reconnecting(this.reconnectDelaySecs))
    setTimeout(() => this.connect(), this.reconnectDelaySecs * 1000);
  }

  private sendPing():void {
    if (this.connected) {
      console.debug("sending ping");
      this.sendMsg('ping');
    }
  }
}
