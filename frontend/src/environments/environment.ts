// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  R_BASE_URL: 'https://cowin-slot-313213.el.r.appspot.com',
  R_SUBSCRIBE_USER: '/v1/public/subscribe/',
  R_UNSUBSCRIBE_USER: '/v1/public/unsubscribe/',
  TELEGRAM_SUBSCRIBE: '/v1/public/telegram/subscribe/',

  COWIN_BASE_URL: 'https://cdn-api.co-vin.in/api',
  COWIN_DISTRICT: '/v2/admin/location/districts/',
  COWIN_STATE: '/v2/admin/location/states',

  subcription_message: {
    msg01: 'Please verify ',
    msg02: ', to complete subcription for ',
    spamMsg: ' Please check your SPAM box as well in case you do not receive the email.',
    telegramMessage: 'To register for Telegram message, please ping the same email id to ',
    telegramId: 'https://t.me/CowinNotBot',
    telegramIdShowName: 'Cowin-slot-bot Telegram bot',
    telegramDirectVerification: 'https://t.me/CowinNotBot?start=',
    telegramDirectVerificationMsg1: 'Click',
    telegramDirectVerificationMsg2: 'here',
    telegramDirectVerificationMsg3: 'to instant verify.',
    successfullMsg: 'Email verified, subscription activated. You can subscribe for Telegram updates. You can use the Unsubscribe option anytime to stop receiving updates.',
    failureMsg: 'Email verification failed. Please resubscribe.',
    alreadyValidatedMsg: 'Email already verified.'
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
