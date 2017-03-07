const {
  NativeModules,
  DeviceEventEmitter,
} = require('react-native');

const RNFcmBridge = NativeModules.RNFcm;
const RNFCM_EVENT_REFRESH_TOKEN = 'RNFcmEventRefreshToken';

var RNFcm = {
  addEventListener: function(type, handler) {
    if (type === 'register') {
      DeviceEventEmitter.addListener(
        RNFCM_EVENT_REFRESH_TOKEN,
        (token) => handler({token: token.deviceToken, os: 'android'}));
    }
  },
  requestPermissions: function() {
    RNFcmBridge.requestPermissions();
  }
};

module.exports = RNFcm;
