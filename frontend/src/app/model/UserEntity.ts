export class UserEntity{
    email:string;
    district:PlaceEntity[];
    minAgeLimit:Number;

    constructor(email:string, district:PlaceEntity[], minAgeLimit:Number){
        this.email = email;
        this.district = district;
        this.minAgeLimit = minAgeLimit;
    }
}
export class PlaceEntity{
    placeId:string;
    placeName:string;

    constructor(placeId:string, placeName:string){
        this.placeId = placeId;
        this.placeName = placeName;
    }
}