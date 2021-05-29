import { Component } from '@angular/core';
import { MatIconRegistry } from "@angular/material/icon";
import { DomSanitizer } from "@angular/platform-browser";
import { CowinapiService } from './service/cowinapi.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'cowin-mail-notifier';

  constructor(private matIconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer,
    private _cowinApiService: CowinapiService){
    this.matIconRegistry.addSvgIcon(
      `telegramIcon`,
      this.domSanitizer.bypassSecurityTrustResourceUrl("../assets/telegram.svg")
    );
  }
}
