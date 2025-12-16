import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';

@Component({
  selector: 'app-menu',
  imports: [MdbCollapseModule, RouterModule],
  templateUrl: './menu.html',
  styleUrl: './menu.scss',
})
export class Menu {

}
