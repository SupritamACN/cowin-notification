import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject, Subscriber } from 'rxjs';
import { environment } from 'src/environments/environment';
import { DistrictEntity } from '../model/DistrictEntity';
import { StateEntity } from '../model/StateEntity';

@Injectable({
  providedIn: 'root'
})
export class CowinapiService {

  constructor(private _http: HttpClient) { 
  }

  theme:Subject<boolean> = new BehaviorSubject<boolean>(false);

  getAllDistrict(state_id:string):Observable<DistrictEntity[]>{
    return this._http.get<DistrictEntity[]>(
      environment.COWIN_BASE_URL + environment.COWIN_DISTRICT + state_id
    );
  }

  getAllStates():Observable<StateEntity[]>{
    return this._http.get<StateEntity[]>(
      environment.COWIN_BASE_URL + environment.COWIN_STATE
    );
  }

}
