import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {CmdListService} from '../../services/cmd-list.service';
import {ConnService} from '../../services/conn.service';

@Component({
  selector: 'app-input-box',
  templateUrl: './input-box.component.html',
  styleUrls: ['./input-box.component.scss']
})
export class InputBoxComponent implements OnInit {

  constructor(private cmdList: CmdListService, private conn: ConnService) {

  }

  @ViewChild('box') box: ElementRef;
  cmd: String = "";

  ngOnInit(): void {
  }


  submit():void {
    if (this.isDisabled())
      return;

    if (this.cmd.trim().length > 0)
      this.cmdList.newCommand(this.cmd.trim());

    this.cmd = "";
    this.box.nativeElement.blur(); //hide keyboard on mobile otherwise it may hide suggestions that come back
  }

  isDisabled():boolean {
    return ! this.conn.connected || this.cmdList.isRendering;
  }

  onClick(e:MouseEvent) {
    //Try to determine if click happened towards the rightmost 20% of box and around halfway below
    //the top
    const horizontalEnd = (e.clientX >= (this.box.nativeElement.offsetWidth * 0.85))
    const verticalEnd = (e.clientY >= (this.box.nativeElement.offsetHeight * 0.5))
    console.warn("In click handler", horizontalEnd, verticalEnd );

    if (horizontalEnd && verticalEnd)
      this.submit();
  }

}
