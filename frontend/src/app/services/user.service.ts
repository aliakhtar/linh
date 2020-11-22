import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})


//Stores and recalcs current type delay
export class UserService {

  private readonly defaultTypeDelay: number = 80; //Used to track if currently we're sped up
  private readonly fastSpeed: number = this.defaultTypeDelay / 5;


  private currentTypeDelay = this.defaultTypeDelay;


  typeDelay():number {
    return this.currentTypeDelay;
  }

  recalcDelay():void {
    this.currentTypeDelay = (this.currentTypeDelay == this.defaultTypeDelay) ? this.fastSpeed : this.defaultTypeDelay;
  }


}
