const {
  NativeModules,
  DeviceEventEmitter,
} = require('react-native');

const RNFcmBridge = NativeModules.RNFcm;
const RNFCM_EVENT_REFRESH_TOKEN = 'RNFcmEventRefreshToken';

var RNFcm = {
  addEventListener: function(type: String, handler: Function) {
    if (type === 'register') {
      DeviceEventEmitter.addListener(
        RNFCM_EVENT_REFRESH_TOKEN,
        (token) => {
          if (token) {
            handler({token: token.deviceToken, os: 'android'});
          }
          else {
            handler({token: null, os: 'android'});
          }
        }
      );
      RNFcmBridge.requestPermissions();

    }
  },
  requestPermissions: function() {
    RNFcmBridge.requestPermissions();
  }
};

module.exports = RNFcm;
