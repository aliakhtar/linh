import {ContentKind, Content} from './config';

export class Cmd implements Content{
  readonly kind: ContentKind = ContentKind.IN;
  readonly text: string;

  constructor(text: string) {
    this.text = text;
  }
}
