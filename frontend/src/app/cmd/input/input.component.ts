import {Component, Input, OnInit} from '@angular/core';
import {Cmd} from '../../models/cmd';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss']
})
export class InputComponent implements OnInit {

  @Input() item: Cmd

  constructor() { }

  ngOnInit(): void {
  }

}
