import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserEntity } from '../model/UserEntity';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  loading:boolean = true;

  constructor(private _http: HttpClient) { }

  doSubsribeUser(user:UserEntity):Observable<any>{
    return this._http.post(
      environment.R_BASE_URL + environment.R_SUBSCRIBE_USER,
      user
    );
  }
  doUnSubscribeUser(email:string):Observable<any>{
    return this._http.get(
      environment.R_BASE_URL + environment.R_UNSUBSCRIBE_USER + email
    );
  }


}
