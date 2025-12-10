import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Menu } from '../menu/menu';

@Component({
  selector: 'app-main-content',
  imports: [Menu, RouterOutlet],
  templateUrl: './main-content.html',
  styleUrl: './main-content.scss',
})
export class MainContent {

}
