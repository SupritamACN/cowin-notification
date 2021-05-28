import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { UserService } from 'src/app/service/user.service';
import { PlaceEntity, UserEntity } from 'src/app/model/UserEntity';
import { CowinapiService } from 'src/app/service/cowinapi.service';
import { DistrictEntity } from 'src/app/model/DistrictEntity';
import { StateEntity } from 'src/app/model/StateEntity';
import { environment } from 'src/environments/environment';


export enum FormMode {
  SUB_MODE, UN_SUB_MODE, EDIT_MODE, TG_MODE
}

@Component({
  selector: 'app-subpage',
  templateUrl: './subpage.component.html',
  styleUrls: ['./subpage.component.css']
})
export class SubpageComponent implements OnInit, AfterViewInit {

  @ViewChild('formDirective') private formDirective: NgForm | any;

  theme:boolean = true;

  appload: boolean = true;
  subscribersForm: any;
  unSubscribersForm: any;
  updateForm: FormGroup = this._fb.group({});
  emailForm: FormGroup = this._fb.group({});
  telegramForm: FormGroup = this._fb.group({});
  toggleBtnText: boolean = true;
  formMode: string = FormMode.SUB_MODE.toString();
  editMode: boolean = false;
  loading: boolean = false;
  email_message: string = '';
  district_message: any = '';
  telegramMessage: string = '';
  telegramId: string = '';
  telegramIdShowName: string = '';
  subscriptionMessage: boolean = false;
  districtList: DistrictEntity[] = [];
  selectedSate: Number = 0;
  selectedDistricts: PlaceEntity[] = [];
  stateList: StateEntity[] = [];
  selectedAge: Number = 0;
  savedDistricts: PlaceEntity[] = [];
  minAgeLimit: Number = 99;
  u_email: string = '';
  telegramDirectVerificationMsg1: string = '';
  telegramDirectVerificationMsg2: string = '';
  telegramDirectVerificationMsg3: string = '';
  telegramURL: string = '';

  ageList: {
    id: Number;
    value: string
  }[] = [
      { id: 0, value: 'Both' },
      { id: 18, value: '18' },
      { id: 45, value: '45' }
    ];

  doseList: {
    id: Number;
    value: string
  }[] = [
      { id: 0, value: 'Both' },
      { id: 1, value: '1' },
      { id: 2, value: '2' }
    ]

  constructor(private _fb: FormBuilder,
    private _userService: UserService,
    private _cowinapiService: CowinapiService) {
      this._cowinapiService.theme.subscribe((res: boolean) => this.theme = res);
  }
  ngAfterViewInit(): void {
  }

  ngOnInit(): void {
    this._cowinapiService.getAllStates().subscribe(
      (res: any) => {
        res.states.forEach((s: any) => {
          this.stateList.push(new StateEntity(s.state_id, s.state_name));
        });
      }
    )
    this._cowinapiService.getAllDistrict('' + this.selectedSate).subscribe(
      (res: any) => {
        res.districts.forEach((d: any) => {
          this.districtList.push(new DistrictEntity(d.district_id, d.district_name));
        })
      }
    )

    this.subscribersForm = this._fb.group({
      email: [null, [Validators.required, Validators.email]],
      district: [null, Validators.required],
      state: [null, Validators.required],
      age: [null, [Validators.required]],
      dose: [null, [Validators.required]]
    });

    this.unSubscribersForm = this._fb.group({
      u_email: [null, [Validators.required, Validators.email]]
    })
    this.emailForm = this._fb.group({
      email: [null, [Validators.required, Validators.email]]
    })
    this.updateForm = this._fb.group({
      district: [null, Validators.required],
      state: [null, Validators.required],
      age: [null, [Validators.required]]
    })
    this.telegramForm = this._fb.group({
      email: [null, [Validators.required, Validators.email]]
    })
  }

  onSelect(e: any) {
    this.districtList = [];
    this._cowinapiService.getAllDistrict('' + e.value).subscribe(
      (res: any) => {
        res.districts.forEach((d: any) => {
          this.districtList.push(new DistrictEntity(d.district_id, d.district_name));
        })
      }
    )
  }

  onSelectDistrict(e: any) {
    this.selectedDistricts = []
    let val: Number[] = e.value;
    val.forEach((d: Number) => {
      this.selectedDistricts.push(new PlaceEntity(d, this.getDistrictNameById(d + '')));
    }
    )
  }

  getDistrictNameById(district_id: string): string {
    let name: string = '';
    this.districtList.forEach((d: DistrictEntity) => {
      if (d.district_id + '' == district_id) {
        name = d.district_name
      }
    })
    return name;
  }
  /* form toggle code */
  toggleForm(formMode: string): void {
    this.email_message = this.subscribersForm.value.email;
    this.district_message = this.subscribersForm.value.district;
    this.subscriptionMessage = false;
    switch (formMode) {
      case '0':
        this.formMode = FormMode.SUB_MODE.toString();
        break;
      case '1':
        this.formMode = FormMode.UN_SUB_MODE.toString();
        break;
      case '2':
        this.formMode = FormMode.EDIT_MODE.toString();
        break;
      case '3':
        this.formMode = FormMode.TG_MODE.toString();
        break;
      default:
        break;
    }
  }
  doSubscribe(): void {
    this.district_message = ''
    this.email_message = ''
    this.loading = true;
    /*  
        let districtName = '';
        this.districtList.forEach(d => {
          if (d.district_id == this.subscribersForm.value.district)
            districtName = d.district_name;
        }) 
    */
    let dList: PlaceEntity[] = [];
    this.selectedDistricts.forEach(d => {
      dList.push(new PlaceEntity(d.placeId, d.placeName))
    });

    let userEntity: UserEntity = new UserEntity(
      this.subscribersForm.value.email,
      dList,
      this.selectedAge,
      this.subscribersForm.value.dose
    );
    this._userService.doSubscribeUser(userEntity).subscribe(
      res => {
        this.email_message = environment.subcription_message.msg01 + this.subscribersForm.value.email + environment.subcription_message.msg02;
        this.selectedDistricts.forEach(sd => {
          this.district_message = this.district_message + sd.placeName + ', '
        })
        this.district_message = this.district_message.substring(0, this.district_message.length - 2) + '.';
        this.telegramMessage = environment.subcription_message.telegramMessage;
        this.telegramId = environment.subcription_message.telegramId;
        this.telegramIdShowName = environment.subcription_message.telegramIdShowName;

        this.telegramDirectVerificationMsg1 = environment.subcription_message.telegramDirectVerificationMsg1;
        this.telegramDirectVerificationMsg2 = environment.subcription_message.telegramDirectVerificationMsg2;
        this.telegramDirectVerificationMsg3 = environment.subcription_message.telegramDirectVerificationMsg3;

        this.telegramURL = environment.subcription_message.telegramDirectVerification + res.message;

        this.subscriptionMessage = true;
        this.formDirective.resetForm();
        this.loading = false;
        return 'success';
      },
      err => {
        this.subscriptionMessage = true;
        this.email_message = err.error.text;
        this.district_message = '';
        this.formDirective.resetForm();
        this.loading = false;
        return 'Issue while saving the user';
      }
    );
  }

  doUnSubscribe(): string {
    this.loading = true;
    let val: any = this.unSubscribersForm.value;
    this._userService.doUnSubscribeUser(this.unSubscribersForm.value.u_email).subscribe(
      res => {
        this.email_message = '' + val.u_email + ', unsubscribed!';
        this.subscriptionMessage = true;
        this.formDirective.resetForm();
        this.loading = false;
      },
      err => {
        if (err.status == 404) {
          this.email_message = '' + err.error;
        } else {
          this.email_message = '' + err.error.text;
        }
        this.subscriptionMessage = true;
        this.formDirective.resetForm();
        this.loading = false;
      }
    )
    return val;
  }


  doTelegramSub(): void {
    this.loading = true;
    //let val: any = this.telegramForm.value;
    this._userService.doTelegramSubscribe(this.telegramForm.value.email).subscribe(
      res => {

        this.telegramDirectVerificationMsg1 = environment.subcription_message.telegramDirectVerificationMsg1;
        this.telegramDirectVerificationMsg2 = environment.subcription_message.telegramDirectVerificationMsg2;
        this.telegramDirectVerificationMsg3 = environment.subcription_message.telegramDirectVerificationMsg3;

        this.telegramURL = environment.subcription_message.telegramDirectVerification + res.message;

        this.subscriptionMessage = true;
        this.formDirective.resetForm();
        this.loading = false;
      },
      err => {
        if (err.status == 404) {
          this.email_message = '' + err.error;
        } else {
          this.email_message = '' + err.error.text;
        }
        this.subscriptionMessage = true;
        this.formDirective.resetForm();
        this.loading = false;
      }
    )
  }

  /* preference code */

  showPreference(): void {
    this.formMode = FormMode.EDIT_MODE.toString();
  }
  getUserByMail() {
    this.u_email = this.unSubscribersForm.value.u_email;
    this._userService.getUserByMail(this.u_email).subscribe(
      res => {
        this.savedDistricts = res.district
        this.editMode = true;
        this.emailForm.controls['email'].disable()
      }
    );
  }

  doUpdate() {
    let user: UserEntity = new UserEntity(
      this.updateForm.value.email,
      this.selectedDistricts,
      this.subscribersForm.value.age,
      this.subscribersForm.value.dose
    );
    this._userService.doUpdate(user).subscribe(
      res => {
      },
      err => {
      }
    );
  }

}
