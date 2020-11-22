import {Component, Input, OnInit} from '@angular/core';
import {Output} from '../../models/output';
import {UserService} from '../../services/user.service';
import {Content} from '../../models/config';
import {OutputItem} from '../../models/outputItem';
import {CmdListService} from '../../services/cmd-list.service';

@Component({
  selector: 'app-output',
  templateUrl: './output.component.html',
  styleUrls: ['./output.component.scss']
})
export class OutputComponent implements OnInit {

  @Input() item:Output

  readonly readyContent: OutputItem[] = [];

  constructor(private user:UserService, private cmdList:CmdListService) { }

  private renderFinished:boolean = false;

  ngOnInit(): void {
    if (this.item.content && this.item.content.length > 0)
      this.readyContent.push(this.item.content[0])
    else {
      this.renderFinished = true;
      this.finishRendering();
    }
  }

  showSuggestions():boolean {
    return this.renderFinished &&
      this.item.suggestions &&
      this.item.suggestions.items &&
      this.item.suggestions.items.length > 0
  }

  handleClick(e: MouseEvent) {
    //ignore the click if it was to a button or link
    const target = e.target as HTMLElement;
    const tag = target.tagName.toLowerCase();
    if (tag === 'a' || tag === 'button')
      return;

    this.user.recalcDelay();
  }

  onRendered(o: OutputItem) {
    const i = this.item.content.indexOf(o);
    const next = this.item.content[i + 1];

    if (next)
      this.readyContent.push( next );
    else
      this.finishRendering();
  }

  private finishRendering() {
    this.renderFinished = true;
    this.cmdList.onLastRendered();
  }
}
