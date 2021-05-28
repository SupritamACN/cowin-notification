import { Component, OnInit } from '@angular/core';
import { CowinapiService } from 'src/app/service/cowinapi.service';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {

  today: number = Date.now();

  constructor(private _cowinApiService: CowinapiService) {
    setInterval(() => { this.today = Date.now() }, 1);
  }

  ngOnInit(): void {
  }

    
  theme:boolean = false;
  changeTheme(){
    this.theme = !this.theme;
    this._cowinApiService.theme.next(this.theme);
  }

}
