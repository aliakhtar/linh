export enum ContentKind {
  IN = 'IN',
  OUT = 'OUT'
}

export enum OutputKind {
  PLAIN= 'PLAIN',
  LOG= 'LOG',
  ERROR= 'ERROR',
  SUCCESS= 'SUCCESS',

  SUGGESTIONS= 'SUGGESTIONS'
}

export interface Content {
  readonly kind: ContentKind;
}
