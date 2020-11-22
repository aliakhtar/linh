import {Output} from './output';

export enum ConnEventKind {
  CONNECTING = 'CONNECTING',
  CONNECTED = 'CONNECTED',
  DATA = 'DATA',
  ERROR = 'ERROR', //auto reconnects in case of connection error - error also means disconnected
  WARNING = 'WARNING', //e.g already connected etc
  DISCONNECTED = 'DISCONNECTED',
  RECONNECTING = 'RECONNECTING',
}


export class ConnEvent {
  readonly kind: ConnEventKind;
  readonly data : Output;


  constructor(kind: ConnEventKind, data: Output) {
    this.kind = kind;
    this.data = data;
  }


  static connecting : ConnEvent = new ConnEvent(ConnEventKind.CONNECTING, Output.logg('Connecting'))
  static connected : ConnEvent = new ConnEvent(ConnEventKind.CONNECTED, Output.success('Connected'))
  static data = (o:Output) => new ConnEvent(ConnEventKind.DATA, o)
  static error : ConnEvent = new ConnEvent(ConnEventKind.ERROR, Output.logg("Couldn't communicate with server."))
  static warning = (msg) => new ConnEvent(ConnEventKind.WARNING, Output.logg(msg))
  static disconnected : ConnEvent = new ConnEvent(ConnEventKind.DISCONNECTED, Output.error("Disconnected"))
  static reconnecting = (delay: number) => new ConnEvent(ConnEventKind.RECONNECTING, Output.logg(`Reconnecting in ${delay}s`))
}
