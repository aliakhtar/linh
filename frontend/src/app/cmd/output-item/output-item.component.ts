import {Component, EventEmitter, Input, OnInit, Output as NgOutput } from '@angular/core';
import {OutputItem} from '../../models/outputItem';
import {OutputKind} from '../../models/config';
import {TypewriterService} from '../../services/typewriter.service';
import {CmdListService} from '../../services/cmd-list.service';

@Component({
  selector: '[app-output-item]',
  templateUrl: './output-item.component.html',
  styleUrls: ['./output-item.component.scss'],
  providers: [TypewriterService],
})
export class OutputItemComponent implements OnInit {

  @Input() item:OutputItem

  @NgOutput() rendered = new EventEmitter<void>();

  kind = OutputKind


  constructor(public typist:TypewriterService, private cmdList:CmdListService) { }

  ngOnInit(): void {
    this.typist.start(this.item.text)
      .subscribe({
        //next: () => this.cmdList.renderEvents.emit(), //scroll

        complete: () => {
          this.rendered.emit();
        }
      })
  }


  class() {
    switch (this.item.kind) {
      case OutputKind.PLAIN: return "";
      case OutputKind.LOG: return "dull";
      case OutputKind.SUCCESS: return "success";
      case OutputKind.ERROR: return "error";
    }
  }
}
