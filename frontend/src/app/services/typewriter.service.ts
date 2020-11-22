import { Injectable } from '@angular/core';
import {UserService} from './user.service';
import {EMPTY, from, Observable, of, timer} from 'rxjs';
import {concatMap, debounceTime, delay, delayWhen, scan} from 'rxjs/operators';
import {CmdListService} from './cmd-list.service';

@Injectable()
export class TypewriterService {

  private fullText:string;
  textNow: string = '';

  constructor(private user:UserService, private cmdList:CmdListService) {
  }

  start(text:string): Observable<string> {
    this.fullText = text.trim();
    if (this.fullText.length <= 0)
      return EMPTY;


    const chars: Observable<string> = from(this.parseText());

    const stream: Observable<string> = chars.pipe(
      scan((a, c) => a + c, ''),
      concatMap(x => of(x).pipe(delay(this.user.typeDelay())))
    )

    setTimeout(() => stream.subscribe({
      next: s => this.textNow = s,
      complete: () => setTimeout(() => this.cmdList.renderEvents.emit(), this.user.typeDelay() + 100)
    }), this.user.typeDelay());

    return stream;
  }


  private parseText():string[] {
    const orig = Array.from(this.fullText)

    const result = [];
    let cursor = 0;
    while (cursor < orig.length) {
      const nextChar = orig[cursor];
      /*
      Hack: If answer doesn't include html, show it char by char.
      If it does include html, try to parse out the position of the end of the html (the > tag)
      and show it all at once so shit doesnt look weird
      */
      if (nextChar !== '<') {
        result.push(nextChar)
        cursor += 1;
      } else {
        const substr = orig.slice(cursor)
        const tagEndIndex = substr.indexOf(">")
        const html = substr.slice(0, tagEndIndex + 1)
        result.push(html.join(''))
        cursor += html.length;
      }
    }

    //console.warn(result);
    return result;
  }

}
