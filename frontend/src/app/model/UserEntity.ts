export class UserEntity{
    email:string;
    district:PlaceEntity[];
    minAgeLimit:Number;
    dose:Number;

    constructor(email:string, district:PlaceEntity[], minAgeLimit:Number, dose:Number){
        this.email = email;
        this.district = district;
        this.minAgeLimit = minAgeLimit;
        this.dose = dose;
    }
}
export class PlaceEntity{
    placeId:Number;
    placeName:string;

    constructor(placeId:Number, placeName:string){
        this.placeId = placeId;
        this.placeName = placeName;
    }
}