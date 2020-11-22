import {Component, Input, OnInit} from '@angular/core';
import {CmdListService} from '../../services/cmd-list.service';
import {Suggestions} from '../../models/suggestions';
import {TypewriterService} from '../../services/typewriter.service';

@Component({
  selector: 'app-suggestions',
  templateUrl: './suggestions.component.html',
  styleUrls: ['./suggestions.component.scss'],
  providers: [TypewriterService],
})
export class SuggestionsComponent implements OnInit {

  constructor(private cmdList:CmdListService, public typist: TypewriterService) { }

  @Input() suggestions : Suggestions;

  ngOnInit(): void {
    this.typist.start(this.suggestions.label).subscribe({
      next: () => {
      }
    });
    //Re-instantiate the class because it won't have remove method if deserialized thru json
    this.suggestions = new Suggestions(this.suggestions.label, this.suggestions.items);
  }


  handleClick(item: string) {
    this.suggestions.remove(item);
    this.cmdList.newCommand(item);
  }



}
