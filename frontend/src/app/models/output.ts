import {Content, ContentKind, OutputKind} from './config';
import {OutputItem} from './outputItem';
import {Suggestions} from './suggestions';

export class Output implements Content{
  kind: ContentKind = ContentKind.OUT;
  content: OutputItem[];
  suggestions ?: Suggestions;

  constructor(content: OutputItem[], suggestions?: Suggestions) {
    this.content = content;
    this.suggestions = suggestions;
  }

  static plain(msg: string):Output {
    const item = new OutputItem(OutputKind.PLAIN, msg)
    return new Output([item])
  }

  static logg(msg: string):Output {
    const item = new OutputItem(OutputKind.LOG, msg)
    return new Output([item])
  }

  static success(msg: string):Output {
    const item = new OutputItem(OutputKind.SUCCESS, msg)
    return new Output([item])
  }

  static error(msg: string):Output {
    const item = new OutputItem(OutputKind.ERROR, msg)
    return new Output([item])
  }

  static fromJson(parsedJson: any):Output {
    const o = Object.assign(Object.create(Output.prototype), parsedJson);
    o.kind = ContentKind.OUT; //todo backend doesn't assign this atm
    return o;
  }
}
