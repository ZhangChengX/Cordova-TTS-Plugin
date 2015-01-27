
/**
 * window.plugins.tts.read("text to speech");
 */
 
function Tts() {}

Tts.prototype.start = function(text, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "Tts", "start", [text]);
};

Tts.prototype.stop = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "Tts", "stop", []);
}

Tts.prototype.pause = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "Tts", "pause", []);
}

Tts.prototype.resume = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "Tts", "resume", []);
}

Tts.install = function () {
    if (!window.plugins) {
        window.plugins = {};
    }

    window.plugins.tts = new Tts();
    return window.plugins.tts;
};

cordova.addConstructor(Tts.install);
