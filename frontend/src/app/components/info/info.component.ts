import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {

  today: number = Date.now();

  constructor() {
    setInterval(() => { this.today = Date.now() }, 1);
  }

  ngOnInit(): void {
  }

}
