import {EventEmitter, Injectable} from '@angular/core';
import {Content, ContentKind} from '../models/config';
import {Cmd} from '../models/cmd';
import {Output} from '../models/output';
import {Observable, ReplaySubject} from 'rxjs';
import {filter, map, scan} from 'rxjs/operators';
import {Suggestions} from '../models/suggestions';


@Injectable({
  providedIn: 'root'
})
export class CmdListService {

  isRendering = false;

  content = new ReplaySubject<Content>( 10)
  private queue: Output[] = [];

  renderEvents = new EventEmitter<void>();

  cmds: Observable<Cmd> = this.content.pipe(
    filter(c => c.kind == ContentKind.IN),
    map(c => c as Cmd))

  constructor() {
    this.newCommand('connect')
  }

  stack: Observable<Content[]> = this.content.pipe(
      scan((a,c) => {
        a.push(c);
        return a;
      }, [] as Content[] )
  )

  newCommand(text: string): void {
    this.content.next( new Cmd(text) );
  }

  newOutput(item: Output):void {
    if (! this.isRendering) {
      this.isRendering = true;
      this.content.next(item)
    }
    else
      this.queue.push(item);
  }

  onLastRendered():void {
    //this.renderEvents.emit();
    this.isRendering = false;
    if (this.queue.length > 0)
      this.newOutput( this.queue.shift() )
  }

}
