import { Component, OnInit } from '@angular/core';
import {CmdListService} from '../../services/cmd-list.service';

@Component({
  selector: 'app-auto-scroll',
  templateUrl: './auto-scroll.component.html',
  styleUrls: ['./auto-scroll.component.scss']
})
export class AutoScrollComponent implements OnInit {


  private manualScrollOverride = false;

  constructor(private cmdList:CmdListService) {
    cmdList.renderEvents.subscribe(_ => this.scrollNow());
  }

  ngOnInit(): void {
    window.addEventListener("scroll", () => this.onUserScroll(), {passive: true} );

    this.cmdList.content.subscribe(_ => {
      if (this.manualScrollOverride) {
        console.warn("Manual override in effect, not scrolling");
        return;
      }

      this.scrollNow();

    })
  }

  private scrollNow(){
    const cmdBox = document.getElementById("cmd");

    cmdBox.scrollIntoView({behavior: 'smooth'});
    setTimeout(() => cmdBox.focus(), 100)
  }


  private onUserScroll() {

    this.manualScrollOverride = true;
    //NOTE: Keep delay at 1 sec cuz sometimes clicks are treated as scroll events, so if delay is too long,
    //you may not see the box after typewriter finishes typing. 1 sec seems to work best
    setTimeout(() => this.manualScrollOverride = false, 1000) //turn it off in 1 secs cuz you
    //hopefully done by then
  }

}
