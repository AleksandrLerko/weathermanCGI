import { Component } from '@angular/core';
import { AppServise } from './app.servise';

export interface Card {
  title: string
  text: string
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  toggle = true;

  cards: Card[] = [
    {title: 'Card 1', text: 'This is card number 1'}
  ]

  toggleCards(){
    this.toggle = !this.toggle;
  }
}
