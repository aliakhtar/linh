import {OutputKind} from './config';

export class OutputItem{
  kind: OutputKind;
  text: string;

  constructor(kind: OutputKind, text?: string) {
    this.kind = kind;
    this.text = text;
  }
}
