// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  R_BASE_URL: 'https://cowin-slot-313213.el.r.appspot.com',
  R_SUBSCRIBE_USER: '/v1/public/subscribe/',
  R_UNSUBSCRIBE_USER: '/v1/public/unsubscribe/',

  COWIN_BASE_URL: 'https://cdn-api.co-vin.in/api',
  COWIN_DISTRICT: '/v2/admin/location/districts/',
  COWIN_STATE: '/v2/admin/location/states'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
