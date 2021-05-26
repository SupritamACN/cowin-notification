import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PlaceEntity, UserEntity } from '../model/UserEntity';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  loading:boolean = true;

  constructor(private _http: HttpClient) { }

  doSubscribeUser(user:UserEntity):Observable<any>{
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

  getUserByMail(email:string):Observable<any>{
    let p1:PlaceEntity = new PlaceEntity(1, 'Labpur');
    let p2:PlaceEntity = new PlaceEntity(1, 'Bolpur');
    let p3:PlaceEntity = new PlaceEntity(1, 'Suri');
    let districtList:PlaceEntity[] = [];
    districtList.push(p1)
    districtList.push(p2)
    districtList.push(p3)
    let u:UserEntity = new UserEntity(email, districtList, 18);
    return new BehaviorSubject(u);
  }

  doUpdate(user:UserEntity):Observable<any>{
    return new BehaviorSubject('N/A');
  }
  doTelegramSubscribe(email:string): Observable<any>{
    return this._http.get(
      environment.R_BASE_URL + environment.TELEGRAM_SUBSCRIBE + email
    );
  }

}
