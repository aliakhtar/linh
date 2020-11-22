import { Component, OnInit } from '@angular/core';
import {CmdListService} from '../../services/cmd-list.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(private cmdList:CmdListService) { }

  ngOnInit(): void {
  }

  contact() {
    this.cmdList.newCommand('contact');
  }

}
