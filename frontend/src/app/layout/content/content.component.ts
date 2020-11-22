import { Component, OnInit } from '@angular/core';
import {CmdListService} from '../../services/cmd-list.service';
import {Content, ContentKind, OutputKind} from '../../models/config';
import {Observable, of} from 'rxjs';
import {Output} from '../../models/output';
import {OutputItem} from '../../models/outputItem';
import {Cmd} from '../../models/cmd';
import {Suggestions} from '../../models/suggestions';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.scss']
})
export class ContentComponent implements OnInit {

  kind = ContentKind;
  stack: Observable<Content[]>;

  constructor(public cmd:CmdListService) {
    this.stack = cmd.stack;
  }

  ngOnInit(): void {
  }

  toCmd = (item:Content) => item as Cmd
  toOutput = (item:Content) => item as Output

}
